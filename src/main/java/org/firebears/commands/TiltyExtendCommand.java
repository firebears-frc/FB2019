package org.firebears.commands;

import org.firebears.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

import edu.wpi.first.wpilibj.command.Command;

public class TiltyExtendCommand extends SendableCommandBase {

  private Timer timer = new Timer();

  public TiltyExtendCommand() {
    addRequirements(Robot.tilty);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    Robot.tilty.extend();

  }

  @Override
  public boolean isFinished() {
    if (timer.get() > 4.0) {
      return true;
    }
    // return Robot.tilty.isExtended();
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.tilty.freeze();
  }
}
