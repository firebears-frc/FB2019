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

public class ElevatorCargoCommand extends CommandGroup {
  /**
   * for depositing cargo into the rocket durring teleop
   */
  public ElevatorCargoCommand(double elevatorHeight) {
    addSequential(new DriveToVisionTargetCommand());
    addSequential(new ElevatorCommand(elevatorHeight));
    addSequential(new DriveToWallCommand(18));
    addSequential(new CargoSpitCommand(), 3);
    addSequential(new DistanceCommand(-6));
    addSequential(new ElevatorCommand(6));
  }
}
