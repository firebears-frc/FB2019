/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto;

import org.firebears.commands.RelativeAngleCommand;

public class AbsoluteAngleCommand extends RelativeAngleCommand {
  private double targetAngle;
  public AbsoluteAngleCommand(double absoluteAngle) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    super(0.0);
    targetAngle = absoluteAngle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
    setTargetAngle(targetAngle);
  }
}