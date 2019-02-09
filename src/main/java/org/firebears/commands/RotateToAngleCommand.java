/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;


import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;


public class RotateToAngleCommand extends PIDCommand {
  protected double turnValue;
	protected final double SPEED = 0.5;
	protected double angleTolerance = 2;
	protected double targetAngle;
	double angle;
	long timeout;

  public RotateToAngleCommand(double angle) {
    super("RotateToAngleCommand",
    Preferences.getInstance().getDouble("RotateToAngleCommand.p", 0.0),
    Preferences.getInstance().getDouble("RotateToAngleCommand.i", 0.0),
    Preferences.getInstance().getDouble("RotateToAngleCommand.d", 0.0));
    requires(Robot.chassis);
   
    this.angle = bound(angle);
		
		getPIDController().setInputRange(-180, 180);
		getPIDController().setContinuous(true);
		getPIDController().setAbsoluteTolerance(angleTolerance);
  }
 
  private static double getAngleDifference(double angle1, double angle2) {
		return bound(angle2 - angle1);
  }
  
  protected static double bound(double angle) {
		while (angle > 180)
			angle -= 360;
		while (angle < -180)
			angle += 360;
		return angle;
	}

  private double getAngleDifference() {
		return getAngleDifference(Robot.chassis.getAngle(), targetAngle);
	}
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeout = System.currentTimeMillis() + 1000 * 5;
		turnValue = bound(angle - Robot.chassis.getAngle());
		targetAngle = bound(angle);
    getPIDController().setSetpoint(0.0);
    System.out.println("Begin " + this);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double difference = getAngleDifference();
		if (System.currentTimeMillis() >= timeout) {
        return true;
    	} else {

return Math.abs(Robot.chassis.getVelocity()) < 1
 && Math.abs(getAngleDifference()) < angleTolerance;
     }
    //return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0.0, 0.0);
    System.out.println("End " + this);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  protected void usePIDOutput(double output) {
    output = Math.max(-SPEED, Math.min(output, SPEED));
		Robot.chassis.drive(0, -output);
  }

  protected double returnPIDInput() {
    return getAngleDifference();
  }

  @Override
  public String toString() {
    return "RotateToAngleCommand(" + angle + ")";
  }
}
