package org.firebears.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
import edu.wpi.first.wpilibj.Preferences;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Tilty extends SendableSubsystemBase {
    public final double MOTOR_SPEED = 0.8;

    private final NetworkTableEntry extendedLimitSwitchWidget;
    private final NetworkTableEntry retractedLimitSwitchWidget;

    WPI_TalonSRX motor;
    private final Preferences config = Preferences.getInstance();

    private final long dashDelay;
    private long dashTimeout;

    public Tilty(WPI_TalonSRX tiltyMotor, ShuffleboardTab programmerTab) {
        motor = tiltyMotor;
        motor.configFactoryDefault();

        extendedLimitSwitchWidget = programmerTab.add("Tilty extention", false).withPosition(0, 4).getEntry();
        retractedLimitSwitchWidget = programmerTab.add("Tilty retraction", false).withPosition(3, 4).getEntry();
 
        dashDelay = config.getLong("dashDelay", 250);
        dashTimeout = System.currentTimeMillis() + dashDelay;
    }

    public boolean isRetracted() {
        return motor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isExtended() {
        return motor.getSensorCollection().isRevLimitSwitchClosed();
    }

    @Override
    public void periodic() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > dashTimeout) {
            extendedLimitSwitchWidget.setBoolean(isExtended());
            retractedLimitSwitchWidget.setBoolean(isRetracted());
            dashTimeout = currentTime + dashDelay;
        }
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
