package org.firebears.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Preferences;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 *
 */
public class Tilty extends Subsystem {

    private WPI_TalonSRX motor;
    private final Preferences config = Preferences.getInstance();

    public Tilty() {
        motor = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));
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
        // TODO
    }

    /**
     * Lower the elevator to be within the frame perimeter.
     */
    public void retract() {
        // TODO
    }

    public void freeze(){
        //TODO
    }
}
