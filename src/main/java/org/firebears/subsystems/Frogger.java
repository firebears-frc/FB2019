package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.firebears.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;

public class Frogger extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX jumpMotor;
    WPI_TalonSRX forwardMotor;
    private final double FROGGER_SPEED = 0.5;
    private final double DRIVE_SPEED = 0.5;

    public Frogger() {
        jumpMotor = new WPI_TalonSRX(config.getInt("frogger.jumpMotor.canID", 11));
        forwardMotor = new WPI_TalonSRX(config.getInt("frogger.forwardMotor.canID", 17));
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {

    }

    public void footDown() {
        jumpMotor.set(FROGGER_SPEED);
    }

    public void footup() {
        jumpMotor.set(-FROGGER_SPEED);
    }

    public void footStop() {
        jumpMotor.set(0.0);
    }

    public void driveForward() {
        forwardMotor.set(DRIVE_SPEED);
    }

    public void stopDrive() {
        forwardMotor.set(0.0);
    }

    public double getJumpMotor() {
        return jumpMotor.get();
    }

}
