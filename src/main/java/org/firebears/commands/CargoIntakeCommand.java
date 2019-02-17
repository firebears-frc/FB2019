package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CargoIntakeCommand extends Command {

  public CargoIntakeCommand() {
    requires(Robot.cargoGrabber);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.cargoGrabber.intake();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.cargoGrabber.hold();
  }

  @Override
  protected void interrupted() {
  }
}
