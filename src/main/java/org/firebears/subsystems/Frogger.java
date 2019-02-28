package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Frogger extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX jumpMotor;
    WPI_TalonSRX forwardMotor;
    private final double FROGGER_SPEED = 0.2;
    private final double DRIVE_SPEED = 0.2;

    public Frogger() {
        jumpMotor = new WPI_TalonSRX(config.getInt("frogger.jumpMotor.canID", 11));
        forwardMotor = new WPI_TalonSRX(config.getInt("frogger.forwardMotor.canID", 17));
        jumpMotor.configFactoryDefault();
        forwardMotor.configFactoryDefault();
        addChild("jumpMotor", jumpMotor);
        addChild("forwardMotor", forwardMotor);
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
//public boolean isLimitHit(){
   // if jumpMotor.limit
//}
}
