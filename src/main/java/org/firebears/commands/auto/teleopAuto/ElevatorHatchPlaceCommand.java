/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto.teleopAuto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.firebears.commands.auto.*;
import org.firebears.commands.*;
import org.firebears.subsystems.*;

public class ElevatorHatchPlaceCommand extends CommandGroup {
  /**
   * for placing hatches on the rocket durring teleop
   */
  public ElevatorHatchPlaceCommand(Double elevatorHeight) {
    addSequential(new DriveToVisionTargetCommand());
    addSequential(new ElevatorCommand(elevatorHeight));
    addSequential(new DriveToWallCommand(15));
    addSequential(new HatchReleaseCommand());
    addSequential(new DistanceCommand(-6));
    addSequential(new ElevatorCommand(6));
  }
}
