package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class TiltyRetractCommand extends SendableCommandBase {

  Timer timer = new Timer();

  public TiltyRetractCommand() {
    addRequirements(Robot.tilty);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    Robot.tilty.retract();
  }

  @Override
  public boolean isFinished() {
    if (timer.get() > 8.0) {
      return true;
    }
    // return Robot.tilty.isRetracted();
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.tilty.freeze();
  }

}
