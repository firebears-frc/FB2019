package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.Robot;

public class FroggerLowerCommand extends SendableCommandBase {

  private Timer timer = new Timer();

  public FroggerLowerCommand() {
    addRequirements(Robot.frogger);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    // Robot.frogger.enable();
  }

  @Override
  public void execute() {
    Robot.frogger.footDown();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(3.0)){
      return true;
    }
    if (Robot.frogger.isDownwardsLimitHit() == true) {
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.frogger.footStop();
  }

}
