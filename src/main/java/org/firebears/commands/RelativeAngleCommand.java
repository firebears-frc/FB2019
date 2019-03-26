package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RelativeAngleCommand extends Command {

  private final double angle;
  private double targetAngle;

  public RelativeAngleCommand(double a) {
    angle = a;
    requires(Robot.chassis);
  }

  @Override
  protected void initialize() {
    setTargetAngle(Robot.chassis.getAngle() + angle);
    setTimeout(10);
    System.out.println("INITIALIZE: " + this);
  }

  @Override
  protected void execute() {
    double diff = getAngleDifference();
    if (diff < -25) {
      Robot.chassis.drive(0, -0.5);
    } else if (diff < 0) {
      Robot.chassis.drive(0, -0.25);
    } else if (diff > 25) {
      Robot.chassis.drive(0, 0.5);
    } else {
      Robot.chassis.drive(0, 0.25);
    }
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    return getAngleDifference() < 1 && getAngleDifference() > -1;
  }

  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
  }

  private static double getAngleDifference(double angle1, double angle2) {
    return bound(angle2 - angle1);
  }

  /**
   * @return the angle folded into the range -180 to 180.
   */
  protected static double bound(double angle) {
    while (angle > 180)
      angle -= 360;
    while (angle < -180)
      angle += 360;
    return angle;
  }

  /**
   * @return the angle from the current heading to get back to the target angle,
   *         in the range -180 to 180.
   */
  private double getAngleDifference() {
    return getAngleDifference(Robot.chassis.getAngle(), targetAngle);
  }

  public void setTargetAngle(double angle) {
    targetAngle = bound(angle);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "(" + angle + ")";
  }
}
