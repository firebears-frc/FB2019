/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;

/**
 * Turns on need cargo lights
 */
public class NeedsCargoCommand extends InstantCommand {
  
  public NeedsCargoCommand() {
    addRequirements(Robot.lights);
  }

  @Override
  public void initialize() {
    Robot.lights.setCargoMode(true);
  }

}
