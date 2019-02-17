// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.firebears.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
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
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }
public void footDown(){
    jumpMotor.set(FROGGER_SPEED);
}
public void footup(){
    jumpMotor.set(-FROGGER_SPEED);
}
public void footStop(){
    jumpMotor.set(0.0);
}
public void driveForward(){
    forwardMotor.set(DRIVE_SPEED);
} 
public void stopDrive(){
    forwardMotor.set(0.0);
}
public double getJumpMotor(){
    return jumpMotor.get();
}

}

