package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class ElevatorWithBrakeCommand extends SendableCommandBase {
  double distanceGoal;

  enum STATE {
    INITIAL, WAITING_FOR_BRAKE_TO_DISENGAGE, MOVING, WAITING_FOR_BRAKE_TO_ENAGE, ENDING
  };

  private STATE state = STATE.INITIAL;
  private long timeout;
  private Timer timer = new Timer();

  public ElevatorWithBrakeCommand(double inches) {
    addRequirements(Robot.elevator);
    distanceGoal = inches;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    state = STATE.INITIAL;
  }

  @Override
  public void execute() {
    switch (state) {
    case INITIAL:
      timeout = System.currentTimeMillis() + 300;
      Robot.elevator.setBrake(false);
      Robot.elevator.enable();
      Robot.elevator.setSetpoint(Robot.elevator.inchesTraveled() + 2);
      state = STATE.WAITING_FOR_BRAKE_TO_DISENGAGE;
      break;
    case WAITING_FOR_BRAKE_TO_DISENGAGE:
      if (System.currentTimeMillis() > timeout) {
        Robot.elevator.setSetpoint(distanceGoal);
        state = STATE.MOVING;
      }
      break;
    case MOVING:
      if (reachedSetpoint()) {
        Robot.elevator.setBrake(true);
        timeout = System.currentTimeMillis() + 200;
        state = STATE.WAITING_FOR_BRAKE_TO_ENAGE;
      }
      break;
    case WAITING_FOR_BRAKE_TO_ENAGE:
      if (System.currentTimeMillis() > timeout) {
        Robot.elevator.disable();
        state = STATE.ENDING;
      }
      break;
    case ENDING:
      Robot.elevator.disable();
    }
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(3.0)) {
      return true;
    }
    return state == STATE.ENDING;
  }

  private boolean reachedSetpoint() {
    return Math.abs(distanceGoal - Robot.elevator.inchesTraveled()) < 1;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.elevator.reset();
    Robot.elevator.setBrake(true);
  }
}
