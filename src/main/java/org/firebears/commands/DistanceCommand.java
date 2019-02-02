/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;



public class DistanceCommand extends PIDCommand {

  double distanceGoal;
  double previousSpeed;
  boolean previousBrakingMode;

  public DistanceCommand(double inches) {
    super("DistanceCommand", 0.024, 0.0, 0.0024);
    distanceGoal = inches;
    requires(Robot.chassis);
  }

  @Override
  protected void initialize() {
    setTimeout(5);
    double initDistance = Robot.chassis.inchesTraveled();
    setSetpoint(initDistance + distanceGoal);
    previousSpeed = 0;
    previousBrakingMode = Robot.chassis.isBrakingMode();
    Robot.chassis.setBrakingMode(true);
  }

  protected void usePIDOutput(double output) {
    double speed = output;
    if ((speed - previousSpeed) > 0.05){
      speed = previousSpeed + 0.05;
    }
    Robot.chassis.drive(speed, 0);
    previousSpeed = speed;
  }

  protected double returnPIDInput() {
    return Robot.chassis.inchesTraveled();
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()){
      return true;
    }
    return getSetpoint() <= Robot.chassis.inchesTraveled();
  }

  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
    Robot.chassis.setBrakingMode(previousBrakingMode);
  }

}