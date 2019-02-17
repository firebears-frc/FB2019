package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

public class CargoSpitCommand extends Command {
  public CargoSpitCommand() {
    requires(Robot.cargoGrabber);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.cargoGrabber.spit();
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
