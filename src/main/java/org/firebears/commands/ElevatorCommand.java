package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorCommand extends Command {
  double distanceGoal;

  public ElevatorCommand(double inches) {
    requires(Robot.elevator);
    distanceGoal = inches;

  }

  @Override
  protected void initialize() {
    setTimeout(3);
  }

  @Override
  protected void execute() {
    Robot.elevator.setSetpoint(distanceGoal);
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()){
      return true;
    }
    return Math.abs(distanceGoal - Robot.elevator.inchesTraveled()) < 4;
  }

  @Override
  protected void end() {
  }
}
