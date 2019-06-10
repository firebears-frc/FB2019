/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto.teleopAuto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.experimental.command.ConditionalCommand;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.HatchGrabber;
import org.firebears.subsystems.Vision;

public class SelectHatchCommand extends ConditionalCommand {
  public SelectHatchCommand(final HatchGrabber hatchGrabber, final Elevator elevator, final Chassis chassis, final Vision vision) {
    super(new ElevatorHatchPlaceCommand(2.2, hatchGrabber, elevator, chassis, vision),
            new ElevatorHatchGrabCommand(hatchGrabber, elevator, chassis, vision),
            () -> { return hatchGrabber.getCapturedSensorValue(); });
    addRequirements(hatchGrabber);
  }

}
