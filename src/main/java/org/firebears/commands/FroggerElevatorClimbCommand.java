package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.firebears.Robot;

/**
 * Climb onto Hab 3.
 * 
 * When this command starts, the elevator should be up above Hab 3 and the
 * chassis should have been driven forward so it barely touches the Hab.
 */
public class FroggerElevatorClimbCommand extends Command {

  private final double ELEVATOR_SPEED = 1.0;
  private double elevatorMinSpeed;

  public FroggerElevatorClimbCommand() {
    requires(Robot.frogger);
    requires(Robot.elevator);
  }

  @Override
  protected void initialize() {
    setTimeout(6);
    elevatorMinSpeed = Robot.elevator.getMinElevatorSpeed();
  }

  @Override
  protected void execute() {
    Robot.frogger.footDown();
    double elevatorSpeed = ELEVATOR_SPEED;
    System.out.println("::: pitch = " + Robot.chassis.getPitchAngle());
    SmartDashboard.putNumber("Pitch", Robot.chassis.getPitchAngle());
//  elevatorSpeed += Robot.chassis.getPitchAngle() * -0.02;
//     if (Robot.chassis.getPitchAngle() < -5.0) {
//       elevatorSpeed += 0.1; // Tipping forward, so raise elevator faster
//       System.out.println("::: TIPPING FORWARD");
//     } else if (Robot.chassis.getPitchAngle() > 5.0) {
//       elevatorSpeed += -0.1; // Tipping backwards, so raise elevator slower
//       System.out.println("::: TIPPING BACKWARDS");
//     }
    Robot.elevator.setSpeed(-1 * elevatorSpeed);
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    return Robot.frogger.isDownwardsLimitHit() && Robot.elevator.inchesTraveled() < 0.5;
  }

  @Override
  protected void end() {
    Robot.frogger.footStop();
    Robot.elevator.setMinElevatorSpeed(elevatorMinSpeed);
    double setpoint = 0.0;
    // Robot.elevator.enable();
  }

}
