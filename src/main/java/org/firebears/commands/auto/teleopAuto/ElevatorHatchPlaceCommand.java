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

public class ElevatorHatchPlaceCommand extends SequentialCommandGroup {
    /**
     * for placing hatches on the rocket durring teleop
     */
    public ElevatorHatchPlaceCommand(Double elevatorHeight, final HatchGrabber hatchGrabber, final Elevator elevator, final Chassis chassis, final Vision vision) {
        super(
                new DriveToVisionTargetCommand(chassis, vision),
                new ElevatorCommand(elevatorHeight, elevator),
                new DriveToWallCommand(15, chassis),
                new HatchReleaseCommand(hatchGrabber),
//                new DistanceCommand(-6),
                new ElevatorCommand(6, elevator)
        );
    }
}
