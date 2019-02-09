package org.firebears.subsystems;

import org.firebears.commands.DriveCommand;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Chassis extends Subsystem {

    private CANSparkMax rearRight;
    private CANSparkMax frontRight;
    private CANSparkMax frontLeft;
    private CANSparkMax rearLeft;
    private DifferentialDrive robotDrive;
    public DigitalInput rightSensor;
    public DigitalInput centerSensor;
    public DigitalInput leftSensor;
    private CANEncoder frontRightEncoder;
    private CANEncoder frontLeftEncoder;
    private AHRS navXBoard;
    boolean brakingMode = false;
    float initialRoll;

    public static final double ENCODER_TICKS_PER_INCH = 0.4449;

    public Chassis() {
        final Preferences config = Preferences.getInstance();
        double rampRate = config.getDouble("chassis.rampRate", 0.0);

        int chassisRearRightCanID = config.getInt("chassis.rearright.canID", 2);
        rearRight = new CANSparkMax(chassisRearRightCanID, MotorType.kBrushless);
        rearRight.setInverted(false);
        rearRight.setRampRate(rampRate);

        int chassisFrontRightCanID = config.getInt("chassis.frontright.canID", 3);
        frontRight = new CANSparkMax(chassisFrontRightCanID, MotorType.kBrushless);
        frontRight.setInverted(false);
        frontRightEncoder = frontRight.getEncoder();
        frontRight.setRampRate(rampRate);

        rearRight.follow(frontRight);

        int chassisFrontLeftCanID = config.getInt("chassis.frontleft.canID", 4);
        frontLeft = new CANSparkMax(chassisFrontLeftCanID, MotorType.kBrushless);
        frontLeft.setInverted(false);
        frontLeftEncoder = frontLeft.getEncoder();
        frontLeft.setRampRate(rampRate);

        int chassisRearLeftCanID = config.getInt("chassis.rearleft.canID", 5);
        rearLeft = new CANSparkMax(chassisRearLeftCanID, MotorType.kBrushless);
        rearLeft.setInverted(false);
        rearLeft.setRampRate(rampRate);

        rearLeft.follow(frontLeft);

        robotDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("RobotDrive", robotDrive);
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setMaxOutput(1.0);

        int chassisRightSensorDio = config.getInt("chassis.rightSensor.dio", 0);
        rightSensor = new DigitalInput(chassisRightSensorDio);

        int chassisCenterSensorDio = config.getInt("chassis.centerSensor.dio", 1);
        centerSensor = new DigitalInput(chassisCenterSensorDio);

        int chassisLeftSensorDio = config.getInt("chassis.leftSensor.dio", 2);
        leftSensor = new DigitalInput(chassisLeftSensorDio);

        setBrakingMode(config.getBoolean("chassis.defaultbraking", false));

        try {
            navXBoard = new AHRS(edu.wpi.first.wpilibj.SerialPort.Port.kUSB);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        initialRoll = navXBoard.getRoll();
    }

    public double getAngle() {
        return navXBoard.getAngle();
    }

    public void resetNavX() {
        navXBoard.reset();
    }

    public boolean isTipping() {
        float currentRoll = navXBoard.getRoll();
        return Math.abs(currentRoll - initialRoll) > 7.0;
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    public void setBrakingMode(boolean braking) {
        IdleMode idleMode = braking ? IdleMode.kBrake : IdleMode.kCoast;
        if (rearRight.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("Failed to set idleMode " + braking + " on rightRear");
        }
        if (rearLeft.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("Failed to set idleMode " + braking + " on rearLeft");
        }
        if (frontRight.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("Failed to set idleMode " + braking + " on frontRight");
        }
        if (frontLeft.setIdleMode(idleMode) != CANError.kOK) {
            System.out.println("Failed to set idleMode " + braking + " on frontLeft");
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

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("rightSensor", rightSensor.get());
        SmartDashboard.putBoolean("centerSensor", centerSensor.get());
        SmartDashboard.putBoolean("leftSensor", leftSensor.get());
        SmartDashboard.putNumber("distanceInInches", inchesTraveled());
        SmartDashboard.putNumber("navX.angle", navXBoard.getAngle());
        SmartDashboard.putBoolean("tipping over", isTipping());

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

}
