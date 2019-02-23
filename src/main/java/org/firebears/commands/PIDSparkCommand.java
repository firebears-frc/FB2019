package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import org.firebears.util.PIDSparkMotor;

import edu.wpi.first.wpilibj.command.Command;

public class PIDSparkCommand extends Command {

  public static final double TOLERANCE = 1.0;

  private final PIDSparkMotor pidLeft = Robot.chassis.pidFrontLeft;
  private final PIDSparkMotor pidRight = Robot.chassis.pidFrontRight;
  private final double distanceGoalLeft;
  private final double distanceGoalRight;
  private double initDistanceLeft;
  private double initDistanceRight;

  public PIDSparkCommand(double leftInches, double rightInches) {
    requires(Robot.chassis);
    distanceGoalLeft = leftInches;
    distanceGoalRight = rightInches;
    setTimeout(5.0);
  }

  public PIDSparkCommand(double degrees) {
    requires(Robot.chassis);
    double circumference = Chassis.WHEEL_BASE_INCHES * Math.PI;
    distanceGoalLeft = circumference * degrees / 360.0;
    distanceGoalRight = -1.0 * circumference * degrees / 360.0;
    setTimeout(5.0);
  }

  @Override
  protected void initialize() {
    initDistanceLeft = pidLeft.inchesTraveled();
    initDistanceRight = pidRight.inchesTraveled();
    System.out.println("INITIALIZE: " + this + "\t" + initDistanceLeft);
  }

  @Override
  protected void execute() {
    pidLeft.driveToPosition(initDistanceLeft + distanceGoalLeft);
    pidRight.driveToPosition(initDistanceRight + distanceGoalRight);
    System.out.println("EXECUTE: " + this + "\t" + (initDistanceLeft + distanceGoalLeft));
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      System.out.println("TIMED OUT: " + this);
      return true;
    }
    double leftError = (initDistanceLeft + distanceGoalLeft) - pidLeft.inchesTraveled();
    double rightError = (initDistanceRight + distanceGoalRight) - pidRight.inchesTraveled();
    System.out.println("IS_FINISHED: " + this + "\t" + leftError + "\t" + rightError);
    return Math.abs(leftError) <= TOLERANCE && Math.abs(rightError) <= TOLERANCE;
  }

  @Override
  protected void end() {
    pidLeft.set(0.0);
    pidRight.set(0.0);
    System.out.println("END: " + this);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "(" + distanceGoalLeft + "," + distanceGoalRight + ")";
  }

}
