package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.Tilty;

import edu.wpi.first.wpilibj.command.Command;

public class TiltyExtendCommand extends SendableCommandBase {

  private Timer timer = new Timer();
  private final Tilty tilty;

  public TiltyExtendCommand(Tilty t) {
    tilty = t;
    addRequirements(tilty);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    System.out.println("INITIALIZE: " + this);
  }

  @Override
  public void execute() {
    tilty.extend();
  }

  @Override
  public boolean isFinished() {
    if (timer.get() > 4.0) {
      return true;
    }
    // return tilty.isExtended();
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    tilty.freeze();
  }
}
