/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto;
import org.firebears.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToVisionTargetCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveToVisionTargetCommand() {
    addSequential(new RotateToVisionTargetCommand());
    addSequential(new DriveToVisionTargetDistanceCommand());
  }
}
