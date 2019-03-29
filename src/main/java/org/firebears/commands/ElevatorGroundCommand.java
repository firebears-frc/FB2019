/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorGroundCommand extends Command {
  public ElevatorGroundCommand() {
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.disable();
    setTimeout(4);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevator.setSpeed(-0.3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (Robot.elevator.getGroundSensor() == false){
      return true;
    }
    if (isTimedOut()){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.enable();
    Robot.elevator.setSetpoint(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
