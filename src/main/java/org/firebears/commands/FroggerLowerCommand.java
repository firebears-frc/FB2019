package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

public class FroggerLowerCommand extends Command {
  public FroggerLowerCommand() {
    requires(Robot.frogger);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.frogger.footDown();
  }

  @Override
  protected boolean isFinished() {
    if (Robot.frogger.getJumpMotor() == 0.0) {
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