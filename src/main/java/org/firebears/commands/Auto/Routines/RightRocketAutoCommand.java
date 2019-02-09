/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.Auto.Routines;

import org.firebears.commands.DistanceCommand;
import org.firebears.commands.RotateToAngleCommand;
import org.firebears.commands.ResetNavXCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RightRocketAutoCommand extends CommandGroup {
  public RightRocketAutoCommand() {
    addSequential(new ResetNavXCommand());
    //addSequential(new WaitCommand(.25));
    addSequential(new DistanceCommand(60));
    // addSequential(new WaitCommand(.25));
    addSequential(new RotateToAngleCommand(90));
    addSequential(new DistanceCommand(52));
   //  addSequential(new WaitCommand(.25));
    addSequential(new RotateToAngleCommand(30));
    addSequential(new DistanceCommand(45));
  }
}
