package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.Robot;

public class CargoSpitCommand extends SendableCommandBase {
  public CargoSpitCommand() {
    addRequirements(Robot.cargoGrabber);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    Robot.cargoGrabber.spit();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.cargoGrabber.hold();
  }

}
