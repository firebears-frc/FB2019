/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class ElevatorGroundCommand extends SendableCommandBase {

  private Timer timer = new Timer();

  public ElevatorGroundCommand() {
    addRequirements(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Robot.elevator.disable();
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    Robot.elevator.setSpeed(-0.3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    if (Robot.elevator.getGroundSensor() == false){
      return true;
    }
    if (timer.get() > 4.0){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    Robot.elevator.enable();
    Robot.elevator.setSetpoint(0);
  }

}
