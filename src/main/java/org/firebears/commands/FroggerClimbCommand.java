/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FroggerClimbCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public FroggerClimbCommand() {
    addSequential(new FroggerLowerCommand());
    addSequential(new FroggerDriveCommand());
    addSequential(new FroggerRaiseCommand());
  }
}
