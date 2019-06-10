package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.Frogger;

public class FroggerLowerCommand extends SendableCommandBase {

  private Timer timer = new Timer();
  private final Frogger frogger;

  public FroggerLowerCommand(final Frogger frogger) {
    this.frogger = frogger;
    addRequirements(frogger);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    // Robot.frogger.enable();
  }

  @Override
  public void execute() {
    frogger.footDown();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(3.0)){
      return true;
    }
    if (frogger.isDownwardsLimitHit() == true) {
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    frogger.footStop();
  }

}
