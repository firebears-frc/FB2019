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

  double froggerSlope;
  double elevatorSetPoint;

  public FroggerClimbSyncCommand() {

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initElevatorDistance = Robot.elevator.inchesTraveled();
    initFroggerDistance = Robot.frogger.encoderDistance();
    finalElevatorDistance = 0.0;
    finalFroggerDistance = 20.0;
    System.out.println("INITIALIZE: " + this);
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

      // (y2 - y1)/(x2 - x1)
      froggerSlope = (finalElevatorDistance - initElevatorDistance) / (finalFroggerDistance - initFroggerDistance);

      // y = mx + b
      elevatorSetPoint = (froggerSlope * currentFroggerDistance) + initElevatorDistance;

      Robot.elevator.setSetpoint(elevatorSetPoint);
    }
    System.out.println("EXECUTE: " + this + "   : " + currentFroggerDistance + " / " + finalFroggerDistance + "   : "
        + elevatorSetPoint + " / " + Robot.elevator.inchesTraveled());
  }

  // Mak this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double currentFroggerDistance = Robot.frogger.encoderDistance();
    return (currentFroggerDistance >= finalFroggerDistance && Robot.elevator.inchesTraveled() <= finalElevatorDistance);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.frogger.footStop();
    Robot.elevator.setBrake(true);
    System.out.println("END: " + this);
  }
}