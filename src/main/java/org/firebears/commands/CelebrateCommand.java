package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Turn on the celebration lights.
 */
public class CelebrateCommand extends InstantCommand {
  public CelebrateCommand() {
    requires(Robot.lights);
  }

  @Override
  protected void initialize() {
    Robot.lights.setCelebrateMode(true);
  }

}
