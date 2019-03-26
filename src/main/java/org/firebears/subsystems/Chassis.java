package org.firebears.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.firebears.Robot;
import org.firebears.commands.DriveCommand;
import org.firebears.util.PIDSparkMotor;
import org.firebears.util.SPI_Arduino;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {

    private double InchesAvg;
    private double InchesNew;
    private double changeRateOfLidarAvg = 0.6;

    private CANSparkMax rearRight;
    private CANSparkMax frontRight;
    private CANSparkMax frontLeft;
    private CANSparkMax rearLeft;
    public PIDSparkMotor pidFrontRight;
    public PIDSparkMotor pidFrontLeft;
    private DifferentialDrive robotDrive;
    public DigitalInput rightSensor;
    public DigitalInput centerSensor;
    public DigitalInput leftSensor;
    private CANEncoder frontRightEncoder;
    private CANEncoder frontLeftEncoder;
    private AHRS navXBoard;
    private boolean brakingMode = false;
    private float initialRoll;
    private NetworkTableEntry tippingwidget;
    private NetworkTableEntry getAnglewidget;
    private NetworkTableEntry getPitchwidget;
    private NetworkTableEntry inchesTravelledwidget;
    private NetworkTableEntry lidarDistancewidget;

    public SPI_Arduino lidarArduino = null;
    public Thread lidarThread = null;
    private boolean navXUsePitchAngle = true;
    private double navXPitchOffset = 0.0;

    public static final double ENCODER_TICKS_PER_INCH = 0.4449;

    public static final double WHEEL_BASE_INCHES = 22.0;

    final Preferences config = Preferences.getInstance();
    private final double rampRate;

    public Chassis() {
        double kP = config.getDouble("chassis.p", 0.00015);
        double kI = config.getDouble("chassis.i", 0.0);
        double kD = config.getDouble("chassis.d", 0.0);
        double kP2 = config.getDouble("chassis.secondary.p", 0.015);
        double kI2 = config.getDouble("chassis.secondary.i", 0.0);
        double kD2 = config.getDouble("chassis.secondary.d", 0.0);
        boolean closedLoop = config.getBoolean("chassis.closedLoop", false);
        navXUsePitchAngle = config.getBoolean("chassis.navXUsePitchAngle", true);
        navXPitchOffset = config.getDouble("chassis.navXPitchOffset", 5.0);

        int chassisRearRightCanID = config.getInt("chassis.rearright.canID", 2);
        rearRight = new CANSparkMax(chassisRearRightCanID, MotorType.kBrushless);
        rearRight.setInverted(false);

        int chassisFrontRightCanID = config.getInt("chassis.frontright.canID", 3);
        frontRight = new CANSparkMax(chassisFrontRightCanID, MotorType.kBrushless);
        frontRight.setInverted(false);
        frontRightEncoder = frontRight.getEncoder();
        pidFrontRight = new PIDSparkMotor(frontRight, kP, kI, kD);
        pidFrontRight.setClosedLoop(closedLoop);
        pidFrontRight.setInvertEncoder(true);
        pidFrontRight.setSecondaryPID(kP2, kI2, kD2);

        rearRight.follow(frontRight);

        int chassisFrontLeftCanID = config.getInt("chassis.frontleft.canID", 4);
        frontLeft = new CANSparkMax(chassisFrontLeftCanID, MotorType.kBrushless);
        frontLeft.setInverted(false);
        frontLeftEncoder = frontLeft.getEncoder();
        pidFrontLeft = new PIDSparkMotor(frontLeft, kP, kI, kD);
        pidFrontLeft.setClosedLoop(closedLoop);

        int chassisRearLeftCanID = config.getInt("chassis.rearleft.canID", 5);
        rearLeft = new CANSparkMax(chassisRearLeftCanID, MotorType.kBrushless);
        rearLeft.setInverted(false);

        rearLeft.follow(frontLeft);

        robotDrive = new DifferentialDrive(pidFrontLeft, pidFrontRight);
        addChild("RobotDrive", robotDrive);

        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setMaxOutput(1.0);
        setBrakingMode(config.getBoolean("chassis.defaultbraking", false));

        rampRate = config.getDouble("chassis.rampRate", 0.0);
        setRampRate(rampRate);

        try {
            navXBoard = new AHRS(edu.wpi.first.wpilibj.SerialPort.Port.kUSB);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        initialRoll = navXBoard.getRoll();

        tippingwidget = Robot.programmerTab.add("Tipping", false).withPosition(24, 6).getEntry();
        getAnglewidget = Robot.programmerTab.add("NavX Angle", 0.0).withSize(4, 2).withPosition(0, 0).getEntry();
        getPitchwidget = Robot.programmerTab.add("NavX Pitch", 0.0).withSize(4, 2).withPosition(0, 2).getEntry();
        inchesTravelledwidget = Robot.programmerTab.add("Inches Travelled", 0.0).withSize(4, 2).withPosition(20, 6).getEntry();
        lidarDistancewidget = Robot.programmerTab.add("Lidar Inches", 0.0).withSize(4, 2).withPosition(6, 0).getEntry();

        if (config.getBoolean("chassis.lidarEnable", true)) {
            lidarArduino = new SPI_Arduino();
            lidarThread = new Thread(new LidarRunner());
            lidarThread.start();
        }
    }

    public double getAngle() {
        return navXBoard.getAngle();
    }

    public double getPitchAngle() {
        // return navXBoard.getRoll();
        if (navXUsePitchAngle) {
            return navXBoard.getPitch() + navXPitchOffset;
        } else {
            return navXBoard.getRoll() + navXPitchOffset;
        }
    }

    public void resetNavX() {
        navXBoard.reset();
    }

    public boolean isTipping() {
        double currentRoll = getPitchAngle();
        return Math.abs(currentRoll - initialRoll) > 7.0;
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        robotDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void setBrakingMode(boolean braking) {
        IdleMode idleMode = braking ? IdleMode.kBrake : IdleMode.kCoast;
        if (rearRight.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("ERROR: Failed to set idleMode " + braking + " on rightRear");
        }
        if (rearLeft.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("ERROR: Failed to set idleMode " + braking + " on rearLeft");
        }
        if (frontRight.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("ERROR: Failed to set idleMode " + braking + " on frontRight");
        }
        if (frontLeft.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("ERROR: Failed to set idleMode " + braking + " on frontLeft");
        }
        brakingMode = braking;
    }

    public boolean isBrakingMode() {
        return brakingMode;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveCommand());
    }

    public double getRampRate() {
        // return frontLeft.getRampRate();
        return rampRate;
    }

    public void setRampRate(double rampRate) {
        // frontLeft.setRampRate(rampRate);
        // frontRight.setRampRate(rampRate);
    }

    @Override
    public void periodic() {
        tippingwidget.setBoolean(isTipping());
        getAnglewidget.setNumber(getAngle());
        getPitchwidget.setNumber(getPitchAngle());
        inchesTravelledwidget.setNumber(inchesTraveled());
        lidarDistancewidget.setNumber(getLidarDistanceInches());
        SmartDashboard.putNumber("left inches", pidFrontLeft.inchesTraveled());
        SmartDashboard.putNumber("right inches", pidFrontRight.inchesTraveled());
        SmartDashboard.putNumber("max encoder", pidFrontRight.getmaxEncoderVelocity());
        InchesNew = lidarArduino.getdistanceAA() * changeRateOfLidarAvg;
        InchesAvg = InchesAvg * (1 - changeRateOfLidarAvg) + InchesNew;
    }

    public double inchesTraveledLeft() {
        return frontLeftEncoder.getPosition() / ENCODER_TICKS_PER_INCH;
    }

    public double inchesTraveledRight() {
        return -1 * frontRightEncoder.getPosition() / ENCODER_TICKS_PER_INCH;
    }

    public double inchesTraveled() {
        return (inchesTraveledLeft() + inchesTraveledRight()) / 2;
    }

    public double getVelocity() {
        return frontLeftEncoder.getVelocity();
    }

    public double getLidarDistanceInches() {
        if (lidarArduino != null) {
            return InchesAvg;
        } else {
            return -1.0;
        }
    }

    /**
     * Thread runnable to monitor the Arduino connection to the Lidar.
     */
    public class LidarRunner implements Runnable {
        public void run() {
            while (true) {
                lidarArduino.getSlavebyte();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
