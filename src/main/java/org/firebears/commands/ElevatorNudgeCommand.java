/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

public class ElevatorNudgeCommand extends ElevatorCommand {
  private double offsetHeight;
  public ElevatorNudgeCommand(double offset) {
    super(0.0);
    offsetHeight = offset;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    distanceGoal = Robot.elevator.inchesTraveled() + offsetHeight;
  }
}
