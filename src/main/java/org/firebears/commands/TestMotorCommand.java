package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestMotorCommand extends Command {
  double speed;

  public TestMotorCommand(double s) {
    requires(Robot.chassis);
    speed = s;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.chassis.drive(0, speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
  }

  @Override
  protected void interrupted() {
  }
}
