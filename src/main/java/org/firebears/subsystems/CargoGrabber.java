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

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.firebears.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;



/**
 *
 */
public class CargoGrabber extends Subsystem {
    final Preferences config = Preferences.getInstance();
WPI_TalonSRX motor;
   private final double MOTOR_SPEED = 1.0;
    public CargoGrabber() {
        motor = new WPI_TalonSRX(config.getInt("cargoGrabber.motor.canID", 14));
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
       
    }

    @Override
    public void periodic() {
       SmartDashboard.putNumber("cargoMotorSpeed", motor.get());

    }

   
 public void intake(){
     motor.set(MOTOR_SPEED);

 }

 public void spit(){
     motor.set(-MOTOR_SPEED);
}

public void hold(){
     motor.set(0.2);
}
}

