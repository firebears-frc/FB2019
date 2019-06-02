package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class ElevatorCommand extends SendableCommandBase {
  private Timer timer = new Timer();
  double distanceGoal;

  public ElevatorCommand(double inches) {
    addRequirements(Robot.elevator);
    distanceGoal = inches;

  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    Robot.elevator.setSetpoint(distanceGoal);
  }

  @Override
  public boolean isFinished() {
    if (timer.get() > 3.0){
      return true;
    }
    return Math.abs(distanceGoal - Robot.elevator.inchesTraveled()) < 1;
  }

  @Override
  public void end(boolean interrupt) {
  }
}
