/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToVisionTargetDistanceCommand extends DistanceCommand {
  public DriveToVisionTargetDistanceCommand() {
    super (0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    double distance = Robot.vision.getVisionTargetDistance();
    distanceGoal = distance;
    super.initialize();
  }
}
