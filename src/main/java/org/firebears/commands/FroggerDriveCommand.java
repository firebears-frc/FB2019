package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FroggerDriveCommand extends Command {
  public FroggerDriveCommand() {
    requires(Robot.frogger);
  }

  @Override
  protected void initialize() {
    setTimeout(3);
  }

  @Override
  protected void execute() {
    Robot.frogger.footDown();
    Robot.frogger.driveForward();
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
    Robot.frogger.stopDrive();
    Robot.frogger.footStop();
  }

  @Override
  protected void interrupted() {
  }
}
