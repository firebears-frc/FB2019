package org.firebears.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Preferences;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Tilty extends Subsystem {
    private final double MOTOR_SPEED = 0.5;

    WPI_TalonSRX motor;
    private final Preferences config = Preferences.getInstance();

    public Tilty() {
        motor = new WPI_TalonSRX(config.getInt("tilty.motor.canID", 12));
        motor.configFactoryDefault();
        addChild("motor", motor);
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {

    }

    /**
     * Raise the elevator for gameplay.
     */
    public void extend() {
        motor.set(MOTOR_SPEED);
    }

    /**
     * Lower the elevator to be within the frame perimeter.
     */
    public void retract() {
        motor.set(-MOTOR_SPEED);
    }

    public void freeze() {
        motor.set(0.0);
    }
}
