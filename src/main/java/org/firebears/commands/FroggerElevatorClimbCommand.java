package org.firebears.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.firebears.Robot;
import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.Frogger;

/**
 * Climb onto Hab 3.
 * 
 * When this command starts, the elevator should be up above Hab 3 and the
 * chassis should have been driven forward so it barely touches the Hab.
 */
public class FroggerElevatorClimbCommand extends SendableCommandBase {

  final Preferences config = Preferences.getInstance();
  private final double ELEVATOR_CLIMB_SPEED;
  private final double ELEVATOR_FINAL_SETPOINT;
  private double elevatorMinSpeed;
  private final double CHASSIS_SPEED = 0.5;
  private Timer timer = new Timer();
  private final Frogger frogger;
  private final Elevator elevator;

  public FroggerElevatorClimbCommand(final Frogger frogger, final Elevator elevator) {
    this.frogger = frogger;
    this.elevator = elevator;
    addRequirements(frogger);
    addRequirements(elevator);
    addRequirements(Robot.chassis);
    ELEVATOR_CLIMB_SPEED = config.getDouble("elevator.climbSpeed", 0.6);
    ELEVATOR_FINAL_SETPOINT = config.getDouble("elevator.finalSetpoint", -1.0);
  }

  @Override
  public void initialize() {
    elevator.disable();
    // frogger.enable();
    elevator.setCurrentLimiting(10, 25, 4000);
    timer.reset();
    timer.start();
    elevatorMinSpeed = elevator.getMinElevatorSpeed();
  }

  @Override
  public void execute() {
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
    boolean elevatorReached = elevator.inchesTraveled() <= ELEVATOR_FINAL_SETPOINT;
    if (elevatorReached) {
      elevator.setBrake(true);
    } else {
      elevator.setSpeed(-1 * elevatorSpeed);
    }

    frogger.footDown();

    frogger.driveForward();

    Robot.chassis.drive(CHASSIS_SPEED, -0.3);
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(6.0)) {
      return true;
    }
    boolean froggerReached = frogger.encoderDistance() >= Frogger.MAX_FROGGER_DISTANCE
        || frogger.isDownwardsLimitHit();
    boolean elevatorReached = elevator.inchesTraveled() <= ELEVATOR_FINAL_SETPOINT;

    return froggerReached && elevatorReached;
  }

  @Override
  public void end(boolean interrupted) {
    // frogger.footStop();
    elevator.setMinElevatorSpeed(elevatorMinSpeed);
    elevator.setSetpoint(elevator.inchesTraveled());
    elevator.enable();
  }

}
