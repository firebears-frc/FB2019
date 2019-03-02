package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 * Climb onto Hab 3.
 * 
 * When this command starts, the elevator should be up above Hab 3 and the
 * chassis should have been driven forward so it barely touches the Hab.
 */
public class FroggerElevatorClimbCommand extends Command {

  private double elevatorMinSpeed;

  public FroggerElevatorClimbCommand() {
    requires(Robot.frogger);
    requires(Robot.elevator);
  }

  @Override
  protected void initialize() {
    setTimeout(3);
    Robot.elevator.disable();
    elevatorMinSpeed = Robot.elevator.getMinElevatorSpeed();
  }

  @Override
  protected void execute() {
    Robot.frogger.footDown();
    double elevatorSpeed = 0.5;
    if (Robot.chassis.getPitchAngle() < -5.0) {
      elevatorSpeed += 0.1; // Tipping forward, so raise elevator faster
    } else if (Robot.chassis.getPitchAngle() > 5.0) {
      elevatorSpeed += -0.1; // Tipping backwards, so raise elevator slower
    }
    Robot.elevator.setSpeed(-1 * elevatorSpeed);
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    if (Robot.frogger.isDownwardsLimitHit()) {
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.frogger.footStop();
    Robot.elevator.setMinElevatorSpeed(elevatorMinSpeed);
    double setpoint = Robot.elevator.inchesTraveled();
    Robot.elevator.setSetpoint(setpoint);
    Robot.elevator.enable();
  }

}