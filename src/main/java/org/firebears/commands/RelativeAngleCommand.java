package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class RelativeAngleCommand extends SendableCommandBase {

  private final double angle;
  private double targetAngle;
  private Timer timer = new Timer();

  public RelativeAngleCommand(double a) {
    angle = a;
    addRequirements(Robot.chassis);
  }

  @Override
  public void initialize() {
    setTargetAngle(Robot.chassis.getAngle() + angle);
    timer.reset();
    timer.start();
    System.out.println("INITIALIZE: " + this);
  }

  @Override
  public void execute() {
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
  public boolean isFinished() {
    if (timer.hasPeriodPassed(10.0)) {
      return true;
    }
    return getAngleDifference() < 1 && getAngleDifference() > -1;
  }

  @Override
  public void end(boolean interrupted) {
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
