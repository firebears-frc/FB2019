package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.CargoGrabber;

public class CargoIntakeCommand extends SendableCommandBase {

  final CargoGrabber cargoGrabber;

  public CargoIntakeCommand(final CargoGrabber cargoGrabber) {
    this.cargoGrabber = cargoGrabber;
    addRequirements(cargoGrabber);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    cargoGrabber.intake();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean inturrupted) {
    cargoGrabber.hold();
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
