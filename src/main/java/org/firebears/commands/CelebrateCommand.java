package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;

/**
 * Turn on the celebration lights.
 */
public class CelebrateCommand extends InstantCommand {
  public CelebrateCommand() {
    addRequirements(Robot.lights);
  }

  @Override
  public void initialize() {
    Robot.lights.setCelebrateMode(true);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
