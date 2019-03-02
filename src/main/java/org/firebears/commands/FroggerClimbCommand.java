package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 * Climb onto Hab 3.
 * 
 * When this command starts, the elevator should be up above Hab 3 and the chassis
 * should have been driven forward so it barely touches the Hab.
 */
public class FroggerClimbCommand extends Command {

  private double elevatorMinSpeed;

  public FroggerClimbCommand() {
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
    double elevatorSpeed = -0.5;
    // TODO:  modify elevator speed so chassis pitch stays horizontal.
    Robot.elevator.setSpeed(elevatorSpeed);
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()){
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
    // TODO:  Set elevator setpoint to current height, so the elevator doesn't move
    Robot.elevator.enable();
  }

}
