/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FroggerClimbSyncCommand extends Command {

  double initElevatorDistance;
  double initFroggerDistance;
  double finalElevatorDistance;
  double finalFroggerDistance;
  double previousElevatorMinSpeed;

  double froggerSlope;
  double elevatorSetPoint;

  public FroggerClimbSyncCommand() {

    requires(Robot.elevator);
    requires(Robot.frogger);

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(10);
    initElevatorDistance = Robot.elevator.inchesTraveled();
    initFroggerDistance = Robot.frogger.encoderDistance();
    finalElevatorDistance = 1.0;
    finalFroggerDistance = 20.0;
    System.out.println("INITIALIZE: " + this);
    previousElevatorMinSpeed = Robot.elevator.minimumElevatorSpeed;
    Robot.elevator.minimumElevatorSpeed = -1.0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double currentFroggerDistance = Robot.frogger.encoderDistance();
    if (currentFroggerDistance < finalFroggerDistance) {
      Robot.frogger.footDown();
    } else {
      Robot.frogger.footStop();
    }

    if (Robot.elevator.inchesTraveled() > finalElevatorDistance) {
      Robot.elevator.setSetpoint(elevatorSetpoint(currentFroggerDistance));
    }
    System.out.println("EXECUTE: " + this + "   : " + currentFroggerDistance + " / " + finalFroggerDistance + "   : "
        + Robot.elevator.inchesTraveled() + " / " + finalElevatorDistance + " : "
        + elevatorSetpoint(currentFroggerDistance) + "    : " + Robot.elevator.getSpeed());
  }

  private double elevatorSetpoint(double currentFroggerDistance) {
    // (y2 - y1)/(x2 - x1)
    froggerSlope = (finalElevatorDistance - initElevatorDistance) / (finalFroggerDistance - initFroggerDistance);

    // y = mx + b
    elevatorSetPoint = (froggerSlope * currentFroggerDistance) + initElevatorDistance;

    return elevatorSetPoint;
  }

  // Mak this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      System.out.println("TIMED_OUT: " + this);
      return true;
    }
    double currentFroggerDistance = Robot.frogger.encoderDistance();
    boolean froggerReached = currentFroggerDistance >= finalFroggerDistance || Robot.frogger.isDownwardsLimitHit();
    boolean elevatorReached = Robot.elevator.inchesTraveled() <= finalElevatorDistance;
    return froggerReached && elevatorReached;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.frogger.footStop();
    Robot.elevator.setBrake(true);
    Robot.elevator.minimumElevatorSpeed = previousElevatorMinSpeed;
    System.out.println("END: " + this);
  }
}