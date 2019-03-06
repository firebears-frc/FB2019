/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands.auto.teleopAuto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class SelectHatchCommand extends ConditionalCommand {
  public SelectHatchCommand() {
    super("SelectHatchConditional", new ElevatorHatchPlaceCommand(2.2), new ElevatorHatchGrabCommand());
    requires(Robot.hatchGrabber);
  }
@Override
protected boolean condition() {
  if (Robot.hatchGrabber.getCapturedSensorValue()){
    return true;
  }
  return false;
}
}
