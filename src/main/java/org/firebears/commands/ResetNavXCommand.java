package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetNavXCommand extends Command {
  public ResetNavXCommand() {
  }

  @Override
  protected void initialize() {
    Robot.chassis.resetNavX();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
