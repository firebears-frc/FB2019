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
  public ElevatorHatchGrabCommand(final HatchGrabber hatchGrabber, final Elevator elevator, final Chassis chassis, final Vision vision) {
    super(
            new DriveToVisionTargetCommand(chassis, vision),
            new ElevatorCommand(6, elevator),
            new DistanceCommand(4, chassis),
            new HatchHoldCommand(hatchGrabber),
            new DistanceCommand(-6, chassis)
    );
//    addSequential(new DriveToVisionTargetCommand());
//    addSequential(new ElevatorCommand(6));
//    addSequential(new DistanceCommand(4));
//    addSequential(new HatchHoldCommand());
//    addSequential(new DistanceCommand(-6));
  }
}
