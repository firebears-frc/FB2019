package org.firebears.commands;

import com.revrobotics.CANPIDController;

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
    if (angle < 0) {
      Robot.chassis.drive(0, -0.3);
    } else {
      Robot.chassis.drive(0, 0.3);
    }
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    SmartDashboard.putNumber("difference", getAngleDifference());
    return getAngleDifference() < 5 && getAngleDifference() > -5;
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
