package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TiltyExtendCommand extends Command {
  public TiltyExtendCommand() {
    requires(Robot.tilty);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.tilty.extend();

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.tilty.freeze();
  }

  @Override
  protected void interrupted() {
  }
}
