package org.firebears.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import org.firebears.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Preferences;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Tilty extends Subsystem {
    private final double MOTOR_SPEED = 0.8;

    private final NetworkTableEntry extendedLimitSwitchWidget;
    private final NetworkTableEntry retractedLimitSwitchWidget;

    WPI_TalonSRX motor;
    private final Preferences config = Preferences.getInstance();

    public Tilty() {
        motor = new WPI_TalonSRX(config.getInt("tilty.motor.canID", 12));
        motor.configFactoryDefault();
        motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        addChild("motor", motor);

        extendedLimitSwitchWidget = Robot.programmerTab.add("Tilty extention", false).withPosition(0, 4).getEntry();
        retractedLimitSwitchWidget = Robot.programmerTab.add("Tilty retraction", false).withPosition(3, 4).getEntry();
    }

    public boolean isRetracted() {
        return motor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isExtended() {
        return motor.getSensorCollection().isRevLimitSwitchClosed();
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        extendedLimitSwitchWidget.setBoolean(isExtended());
        retractedLimitSwitchWidget.setBoolean(isRetracted());
    }

    /**
     * Raise the elevator for gameplay.
     */
    public void extend() {
        motor.set(-1 * MOTOR_SPEED);
    }

    /**
     * Lower the elevator to be within the frame perimeter.
     */
    public void retract() {
        motor.set(0.3);
    }

    public void freeze() {
        motor.set(0.0);
    }
}
