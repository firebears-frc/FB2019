package org.firebears.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.firebears.Robot;
import org.firebears.subsystems.Frogger;

/**
 * Climb onto Hab 3.
 * 
 * When this command starts, the elevator should be up above Hab 3 and the
 * chassis should have been driven forward so it barely touches the Hab.
 */
public class FroggerElevatorClimbCommand extends Command {

  final Preferences config = Preferences.getInstance();
  private final double ELEVATOR_CLIMB_SPEED;
  private final double ELEVATOR_FINAL_SETPOINT;
  private double elevatorMinSpeed;

  public FroggerElevatorClimbCommand() {
    requires(Robot.frogger);
    requires(Robot.elevator);
    ELEVATOR_CLIMB_SPEED = config.getDouble("elevator.climbSpeed", 0.6);
    ELEVATOR_FINAL_SETPOINT = config.getDouble("elevator.finalSetpoint", -1.0);
  }

  @Override
  protected void initialize() {
    Robot.elevator.disable();
    setTimeout(6);
    elevatorMinSpeed = Robot.elevator.getMinElevatorSpeed();
  }

  @Override
  protected void execute() {
    double elevatorSpeed = ELEVATOR_CLIMB_SPEED;
    System.out.println("::: pitch = " + Robot.chassis.getPitchAngle());
    SmartDashboard.putNumber("Pitch", Robot.chassis.getPitchAngle());
    // elevatorSpeed += Robot.chassis.getPitchAngle() * -0.02;
    // if (Robot.chassis.getPitchAngle() < -5.0) {
    // elevatorSpeed += 0.1; // Tipping forward, so raise elevator faster
    // System.out.println("::: TIPPING FORWARD");
    // } else if (Robot.chassis.getPitchAngle() > 5.0) {
    // elevatorSpeed += -0.1; // Tipping backwards, so raise elevator slower
    // System.out.println("::: TIPPING BACKWARDS");
    // }
    boolean elevatorReached = Robot.elevator.inchesTraveled() <= ELEVATOR_FINAL_SETPOINT;
    if (elevatorReached) {
      Robot.elevator.setBrake(true);
    } else {
      Robot.elevator.setSpeed(-1 * elevatorSpeed);
    }

    Robot.frogger.footDown();

    Robot.frogger.driveForward();
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    boolean froggerReached = Robot.frogger.encoderDistance() >= Frogger.MAX_FROGGER_DISTANCE
        || Robot.frogger.isDownwardsLimitHit();
    boolean elevatorReached = Robot.elevator.inchesTraveled() <= ELEVATOR_FINAL_SETPOINT;

    return froggerReached && elevatorReached;
  }

  @Override
  protected void end() {
    Robot.frogger.footStop();
    Robot.elevator.setMinElevatorSpeed(elevatorMinSpeed);
    Robot.elevator.setSetpoint(Robot.elevator.inchesTraveled());
    Robot.elevator.enable();
  }

}
