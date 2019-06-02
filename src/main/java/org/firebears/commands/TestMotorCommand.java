package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class TestMotorCommand extends SendableCommandBase {
  double speed;

  public TestMotorCommand(double s) {
    addRequirements(Robot.chassis);
    speed = s;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    Robot.chassis.drive(0, speed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.chassis.drive(0, 0);
  }

}
