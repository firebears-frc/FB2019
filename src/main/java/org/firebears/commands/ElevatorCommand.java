/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorCommand extends Command {
  double distanceGoal;
  public ElevatorCommand(double inches) {
    requires(Robot.elevator);
    distanceGoal = inches;

  }

 
  @Override
  protected void initialize() {
  }

  
  @Override
  protected void execute() {
    Robot.elevator.setSetpoint(distanceGoal);
  }

 
  @Override
  protected boolean isFinished() {
    return true;
  }

 
  @Override
  protected void end() {
  }
}
