package org.firebears.commands;

import org.firebears.Robot;

/**
 * Drive forward the Frogger foot until we are a certain distance from the wall.
 * NOTE: You must consider the width of the Elevator.  20 inches gets us to the wall.
 */
public class FroggerDriveToWallCommand extends FroggerDriveCommand {

  private double distanceFromWall;

  public FroggerDriveToWallCommand(double inches) {
    distanceFromWall = inches;
  }

  @Override
  protected boolean isFinished() {
    if (super.isFinished()) {
      return true;
    }
    double distance = 0.0;
    if (Robot.chassis.getLidarDistanceInches() != -1.0) {
      distance = Robot.chassis.getLidarDistanceInches();
    }
    return distance <= distanceFromWall;
  }
}
