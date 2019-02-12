/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.Auto.Routines;

import org.firebears.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterAutoCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CenterAutoCommand() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(60));
  }
}
