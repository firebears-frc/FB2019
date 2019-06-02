/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto.teleopAuto;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;
import org.firebears.commands.auto.*;
import org.firebears.commands.*;
import org.firebears.subsystems.*;

public class ElevatorHatchGrabCommand extends SequentialCommandGroup {
  /**
   * for grabbing hatches in teleop
   */
  public ElevatorHatchGrabCommand() {
    super(
            new DriveToVisionTargetCommand(),
            new ElevatorCommand(6),
            new DistanceCommand(4),
            new HatchHoldCommand(),
            new DistanceCommand(-6)
    );
//    addSequential(new DriveToVisionTargetCommand());
//    addSequential(new ElevatorCommand(6));
//    addSequential(new DistanceCommand(4));
//    addSequential(new HatchHoldCommand());
//    addSequential(new DistanceCommand(-6));
  }
}
