/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Vision;

public class RotateToVisionTargetCommand extends RotateToAngleCommand {

  private double x;

  public RotateToVisionTargetCommand() {
    super(0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
    x = Robot.vision.getVisionTargetAngleX();
    setTargetAngle(x);
  }

}
