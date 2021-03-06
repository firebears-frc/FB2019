package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToWallCommand extends Command {

  private double distance;
  private double distanceFromWall;

  public DriveToWallCommand(double inches) {
    requires(Robot.chassis);
    distanceFromWall = inches;
  }

  @Override
  protected void initialize() {
    distance = Robot.chassis.getLidarDistanceInches();
    setTimeout(2);
  }

  @Override
  protected void execute() {
    if (Robot.chassis.getLidarDistanceInches() != -1.0) {
      distance = Robot.chassis.getLidarDistanceInches();
    } else {
      distance = 0;
    }

    Robot.chassis.drive(0.4, 0.0);

  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    if (distance <= distanceFromWall) {
      return true;
    }

    return false;
  }

  @Override
  protected void end() {
    Robot.chassis.drive(0.0, 0.0);
  }


}
