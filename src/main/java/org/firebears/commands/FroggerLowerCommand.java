package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

public class FroggerLowerCommand extends Command {
  public FroggerLowerCommand() {
    requires(Robot.frogger);
  }

  @Override
  protected void initialize() {
    setTimeout(3);

  }

  @Override
  protected void execute() {
    Robot.frogger.footDown();
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()){
      return true;
    }
    if (Robot.frogger.isDownwardsLimitHit() == true) {
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
