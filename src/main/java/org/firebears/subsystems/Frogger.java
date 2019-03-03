package org.firebears.subsystems;

import org.firebears.Robot;
import org.firebears.commands.FroggerLowerCommand;

import com.revrobotics.CANSparkMax.IdleMode;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Frogger extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX jumpMotor;
    WPI_TalonSRX forwardMotor;
    private final double FROGGER_SPEED = 0.6;
    private final double DRIVE_SPEED = 0.5;

    private final NetworkTableEntry froggerBottomWidget;
    private final NetworkTableEntry froggerTopWidget;

    public Frogger() {
        jumpMotor = new WPI_TalonSRX(config.getInt("frogger.jumpMotor.canID", 11));
        forwardMotor = new WPI_TalonSRX(config.getInt("frogger.forwardMotor.canID", 17));

        jumpMotor.configFactoryDefault();
        forwardMotor.configFactoryDefault();

        addChild("jumpMotor", jumpMotor);
        addChild("forwardMotor", forwardMotor);

        froggerBottomWidget = Robot.programmerTab.add("froggerBottom", false).withPosition(13, 9).getEntry();
        froggerTopWidget = Robot.programmerTab.add("froggerTop", false).withPosition(10, 9).getEntry();
    }

    @Override
    public void initDefaultCommand() {
    }

    @Override
    public void periodic() {

        froggerBottomWidget.setBoolean(jumpMotor.getSensorCollection().isFwdLimitSwitchClosed());
        froggerTopWidget.setBoolean(jumpMotor.getSensorCollection().isRevLimitSwitchClosed());

     //   if (jumpMotor.get() == 0.0){
       //     IdleMode idleMode = braking ? IdleMode.kBrake : IdleMode.kCoast;
       // }
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
    public boolean isDownwardsLimitHit(){
     return jumpMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }
    public boolean isUpwardsLimitHit(){
        return jumpMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
}
