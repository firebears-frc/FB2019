package org.firebears.commands;

import org.firebears.subsystems.Tilty;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class TiltyRetractCommand extends SendableCommandBase {

  Timer timer = new Timer();
  private final Tilty tilty;

  public TiltyRetractCommand(Tilty t) {
    tilty = t;
    addRequirements(tilty);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    tilty.retract();
  }

  @Override
  public boolean isFinished() {
    if (timer.get() > 8.0) {
      return true;
    }
    // return tilty.isRetracted();
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    tilty.freeze();
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
