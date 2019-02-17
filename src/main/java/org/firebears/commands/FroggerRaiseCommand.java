package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FroggerRaiseCommand extends Command {
  public FroggerRaiseCommand() {
    requires(Robot.frogger);
  }

  @Override
  protected void initialize() {
    setTimeout(2);
  }

  @Override
  protected void execute() {
    Robot.frogger.footup();
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.frogger.footStop();
  }

  @Override
  protected void interrupted() {
  }
}
