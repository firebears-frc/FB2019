/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class PIDrelativeAngleCommand extends PIDCommand {
  double targetAngle;
  double currentAngle;
  double previousSpeed;
  boolean previousBrakingMode;

  public PIDrelativeAngleCommand(double rotation) {
    super("PIDrelativeAngleCommand", Preferences.getInstance().getDouble("PIDrelativeAngleCommand.p", 0.0),
        Preferences.getInstance().getDouble("PIDrelativeAngleCommand.i", 0.0),
        Preferences.getInstance().getDouble("PIDrelativeAngleCommand.d", 0.0));

    targetAngle = rotation;
    requires(Robot.chassis);

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(10);
    double initAngle = Robot.chassis.getAngle();
    setSetpoint(initAngle + targetAngle);
    currentAngle = initAngle;
    previousBrakingMode = Robot.chassis.isBrakingMode();
    Robot.chassis.setBrakingMode(true);

  }

  // Called repeatedly when this Command is scheduled to run
  protected void usePIDOutput(double output) {
    double speed = clamp((previousSpeed - 0.05), output, (previousSpeed + 0.05));
    if (speed > 0.0 && speed < 0.15) {
      speed = 0.15;
    } else if (speed < 0 && speed > -0.15) {
      speed = -0.15;
    }
    speed = clamp(-0.5, speed, 0.5);

    Robot.chassis.drive(0, speed);
    previousSpeed = speed;

  }

  protected static double bound(double angle) {
    while (angle > 180)
      angle -= 360;
    while (angle < -180)
      angle += 360;
    return angle;
  }

  private double clamp(double minValue, double value, double maxValue) {
    return Math.max(minValue, Math.min(value, maxValue));
  }

  protected double returnPIDInput() {
    return Robot.chassis.getAngle();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    double getAngleDifference = Robot.chassis.getAngle() - getSetpoint();
    return getAngleDifference < 3 && getAngleDifference > -3;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
    Robot.chassis.setBrakingMode(previousBrakingMode);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  public void setTargetAngle(double angle) {
    double initAngle = Robot.chassis.getAngle();
    targetAngle = bound(angle);
    setSetpoint(initAngle + targetAngle);
  }
}
