/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class PIDrelativeAngleCommand extends PIDCommand {
  double targetAngle;
  double currentAngle;
  double previousSpeed;
  boolean previousBrakingMode;

  public PIDrelativeAngleCommand(double rotation) {
    // super("PIDrelativeAngleCommand", 0.0083, 0.0, 0.00083);
    super("PIDrelativeAngleCommand", 0.0085, 0.0, 0.0);

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

    Robot.chassis.drive(0, speed);
    previousSpeed = speed;

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
}
