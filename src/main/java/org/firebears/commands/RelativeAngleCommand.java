/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    targetAngle = Robot.chassis.getAngle() + angle;
    setTimeout(10);

  


  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (angle < 0) {
      Robot.chassis.drive(0, -0.2);
    } else {
      Robot.chassis.drive(0, 0.2);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (isTimedOut()){
      return true;
    }
    System.out.println(Robot.chassis.getAngle()); 
    //System.out.println(getAngleDifference());
    SmartDashboard.putNumber("difference", getAngleDifference()); 
    return getAngleDifference() < 5 && getAngleDifference() > -5;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run

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
