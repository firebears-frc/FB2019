package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.Elevator;

public class ElevatorWithBrakeCommand extends SendableCommandBase {
  double distanceGoal;
  private final Elevator elevator;

  enum STATE {
    INITIAL, WAITING_FOR_BRAKE_TO_DISENGAGE, MOVING, WAITING_FOR_BRAKE_TO_ENAGE, ENDING
  };

  private STATE state = STATE.INITIAL;
  private long timeout;
  private Timer timer = new Timer();

  public ElevatorWithBrakeCommand(double inches, final Elevator elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
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
      elevator.setBrake(false);
      elevator.enable();
      elevator.setSetpoint(elevator.inchesTraveled() + 2);
      state = STATE.WAITING_FOR_BRAKE_TO_DISENGAGE;
      break;
    case WAITING_FOR_BRAKE_TO_DISENGAGE:
      if (System.currentTimeMillis() > timeout) {
        elevator.setSetpoint(distanceGoal);
        state = STATE.MOVING;
      }
      break;
    case MOVING:
      if (reachedSetpoint()) {
        elevator.setBrake(true);
        timeout = System.currentTimeMillis() + 200;
        state = STATE.WAITING_FOR_BRAKE_TO_ENAGE;
      }
      break;
    case WAITING_FOR_BRAKE_TO_ENAGE:
      if (System.currentTimeMillis() > timeout) {
        elevator.disable();
        state = STATE.ENDING;
      }
      break;
    case ENDING:
      elevator.disable();
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
    return Math.abs(distanceGoal - elevator.inchesTraveled()) < 1;
  }

  @Override
  public void end(boolean interrupted) {
    elevator.reset();
    elevator.setBrake(true);
  }
}
