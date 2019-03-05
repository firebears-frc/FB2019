package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorWithBrakeCommand extends Command {
  double distanceGoal;

  enum STATE {
    INITIAL, WAITING_FOR_BRAKE_TO_DISENGAGE, MOVING, WAITING_FOR_BRAKE_TO_ENAGE, ENDING
  };

  private STATE state = STATE.INITIAL;
  private long timeout;

  public ElevatorWithBrakeCommand(double inches) {
    requires(Robot.elevator);
    distanceGoal = inches;
  }

  @Override
  protected void initialize() {
    setTimeout(10);
    state = STATE.INITIAL;
  }

  @Override
  protected void execute() {
    switch (state) {
    case INITIAL:
      timeout = System.currentTimeMillis() + 100;
      Robot.elevator.setBrake(false);
      Robot.elevator.enable();
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
        timeout = System.currentTimeMillis() + 100;
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
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    return state == STATE.ENDING;
  }

  private boolean reachedSetpoint() {
    return Math.abs(distanceGoal - Robot.elevator.inchesTraveled()) < 4;
  }

  @Override
  protected void end() {
    Robot.elevator.reset();
  }
}
