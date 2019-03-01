package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TiltyRetractCommand extends Command {
  public TiltyRetractCommand() {
    requires(Robot.tilty);
  }

  @Override
  protected void initialize() {
    setTimeout(10);
  }

  @Override
  protected void execute() {
    Robot.tilty.retract();
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    return Robot.tilty.isRetracted();
  }

  @Override
  protected void end() {
    Robot.tilty.freeze();
  }

  @Override
  protected void interrupted() {
  }
}
