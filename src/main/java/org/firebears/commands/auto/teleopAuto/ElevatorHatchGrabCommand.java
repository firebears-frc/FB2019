/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto.teleopAuto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.firebears.commands.auto.*;
import org.firebears.commands.*;
import org.firebears.subsystems.*;

public class ElevatorHatchGrabCommand extends CommandGroup {
  /**
   * for grabbing hatches in teleop
   */
  public ElevatorHatchGrabCommand() {
    addSequential(new DriveToVisionTargetCommand());
    addSequential(new ElevatorCommand(6));
    addSequential(new DistanceCommand(4));
    addSequential(new HatchHoldCommand());
    addSequential(new DistanceCommand(-6));
  }
}
