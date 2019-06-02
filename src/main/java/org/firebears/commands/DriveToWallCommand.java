package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class DriveToWallCommand extends SendableCommandBase {

  private Timer timer = new Timer();
  private double distance;
  private double distanceFromWall;

  public DriveToWallCommand(double inches) {
    addRequirements(Robot.chassis);
    distanceFromWall = inches;
  }

  @Override
  public void initialize() {
    distance = Robot.chassis.getLidarDistanceInches();
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    if (Robot.chassis.getLidarDistanceInches() != -1.0) {
      distance = Robot.chassis.getLidarDistanceInches();
    } else {
      distance = 0;
    }

    Robot.chassis.drive(0.4, 0.0);

  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(2.0)) {
      return true;
    }
    if (distance <= distanceFromWall) {
      return true;
    }

    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.chassis.drive(0.0, 0.0);
  }


}
