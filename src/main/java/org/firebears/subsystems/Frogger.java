package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Add your docs here.
 */
public class Frogger extends PIDSubsystem {
    private final Preferences config = Preferences.getInstance();
    private final WPI_TalonSRX jumpMotor;
    private final WPI_TalonSRX forwardMotor;
    private final double DRIVE_SPEED;
    private final double TICKS_PER_INCH = 113.0;
    private final NetworkTableEntry froggerBottomWidget;
    private final NetworkTableEntry froggerEncoderWidget;
    private final Encoder encoder;
    private double startingDistance;
    public final static double MAX_FROGGER_DISTANCE = 20.0;

    public Frogger() {
        super("Frogger", 0.1, 0, 0);
        DRIVE_SPEED = config.getDouble("frogger.driveSpeed", 1.00);

        jumpMotor = new WPI_TalonSRX(config.getInt("frogger.jumpMotor.canID", 11));
        forwardMotor = new WPI_TalonSRX(config.getInt("frogger.forwardMotor.canID", 17));

        jumpMotor.configFactoryDefault();
        forwardMotor.configFactoryDefault();
        jumpMotor.setNeutralMode(NeutralMode.Brake);

        DigitalInput encoderInputA = new DigitalInput(config.getInt("frogger.encoder.dio.A", 0));
        DigitalInput encoderInputB = new DigitalInput(config.getInt("frogger.encoder.dio.B", 1));
        encoder = new Encoder(encoderInputA, encoderInputB, false, EncodingType.k4X);

        froggerBottomWidget = Robot.programmerTab.add("froggerBottom", false).withPosition(13, 7).getEntry();
        froggerEncoderWidget = Robot.programmerTab.add("Frogger Dist", 0.0).withSize(4, 2).withPosition(20, 8)
                .getEntry();

        resetEncoder();

        addChild("jumpMotor", jumpMotor);
        addChild("forwardMotor", forwardMotor);
        addChild("encoder", encoder);
    }

    @Override
    public void periodic() {
        froggerEncoderWidget.setDouble(encoderDistance());
        froggerBottomWidget.setBoolean(jumpMotor.getSensorCollection().isFwdLimitSwitchClosed());
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    protected double returnPIDInput() {
        return encoderDistance();
    }

    @Override
    protected void usePIDOutput(double output) {
        jumpMotor.set(output);
    }

    public boolean isDownwardsLimitHit() {
        return jumpMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isUpwardsLimitHit() {
        return jumpMotor.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void resetEncoder() {
        startingDistance = encoder.getDistance();
    }

    public void driveForward() {
        forwardMotor.set(DRIVE_SPEED);
    }

    public void stopDrive() {
        forwardMotor.set(0.0);
    }

    public double encoderDistance() {
        double currentDistance = encoder.getDistance();
        return Math.abs(currentDistance - startingDistance) / TICKS_PER_INCH;
    }

    public void footDown() {
        setSetpoint(MAX_FROGGER_DISTANCE);
    }

    public void footup() {
        setSetpoint(0.0);
    }

    public void footStop() {
        setSetpoint(encoderDistance());
    }

}
