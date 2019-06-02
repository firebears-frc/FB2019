/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.Robot;
import org.firebears.subsystems.Frogger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class FroggerClimbSyncCommand extends SendableCommandBase {

  double initElevatorDistance;
  double initFroggerDistance;
  double finalElevatorDistance;
  double finalFroggerDistance;
  double previousElevatorMinSpeed;
  private Timer timer = new Timer();

  double climbTime = 2.0;
  long startTimeMilis;
  


  double froggerSlope;
  double elevatorSetPoint;

  public FroggerClimbSyncCommand() {

    addRequirements(Robot.elevator);
    addRequirements(Robot.frogger);

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    initElevatorDistance = Robot.elevator.inchesTraveled();
    initFroggerDistance = Robot.frogger.encoderDistance();
    finalElevatorDistance = -1.0;
    finalFroggerDistance = Frogger.MAX_FROGGER_DISTANCE;
    startTimeMilis = System.currentTimeMillis();
    System.out.println("INITIALIZE: " + this);
    previousElevatorMinSpeed = Robot.elevator.minimumElevatorSpeed;
    Robot.elevator.minimumElevatorSpeed = -1.0;
    Robot.elevator.setBrake(false);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double currentFroggerDistance = Robot.frogger.encoderDistance();
    long currentTime = System.currentTimeMillis();
    double elapsedTime = (currentTime - startTimeMilis) / 1000.0;
    if (currentFroggerDistance < finalFroggerDistance) {
      Robot.frogger.setSetpoint((elapsedTime/climbTime) * finalFroggerDistance);
    } 

    if (Robot.elevator.inchesTraveled() > finalElevatorDistance) {
      Robot.elevator.setSetpoint(elevatorSetpoint(currentFroggerDistance));
    }

    Robot.frogger.driveForward();

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
  public boolean isFinished() {
    if (timer.hasPeriodPassed(climbTime+2)) {
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
  public void end(boolean interrupted) {
    Robot.frogger.footStop();
    Robot.elevator.setBrake(true);
    Robot.elevator.minimumElevatorSpeed = previousElevatorMinSpeed;
    System.out.println("END: " + this);
  }
}