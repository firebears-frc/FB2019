package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TiltyExtendCommand extends Command {
  public TiltyExtendCommand() {
    requires(Robot.tilty);
  }

  @Override
  protected void initialize() {
    setTimeout(4);
  }

  @Override
  protected void execute() {
    Robot.tilty.extend();

  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    // return Robot.tilty.isExtended();
    return false;
  }

  @Override
  protected void end() {
    Robot.tilty.freeze();
  }
}
