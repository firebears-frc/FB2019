package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class CargoIntakeCommand extends SendableCommandBase {

  public CargoIntakeCommand() {
    addRequirements(Robot.cargoGrabber);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    Robot.cargoGrabber.intake();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean inturrupted) {
    Robot.cargoGrabber.hold();
  }

}
