package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RelativeAngleCommand extends Command {

  private double angle;
  private double targetAngle;

  public RelativeAngleCommand(double a) {
    angle = a;
    requires(Robot.chassis);

  }

  @Override
  protected void initialize() {
    targetAngle = Robot.chassis.getAngle() + angle;
    setTimeout(10);

  }

  @Override
  protected void execute() {
    double diff = getAngleDifference();
    if (diff < -25) {
      Robot.chassis.drive(0, -0.4);
    } else if (diff < 0) {
      Robot.chassis.drive(0, -0.2);
    } else if (diff > 25) {
      Robot.chassis.drive(0, 0.4);
    } else {
      Robot.chassis.drive(0, 0.2);
    }
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    SmartDashboard.putNumber("difference", getAngleDifference());
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
}
