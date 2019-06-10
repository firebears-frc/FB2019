package org.firebears.commands;

//import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Frogger;

/**
 * Drive forward the Frogger foot until we are a certain distance from the wall.
 * NOTE: You must consider the width of the Elevator.  20 inches gets us to the wall.
 */
public class FroggerDriveToWallCommand extends FroggerDriveCommand {

  private double distanceFromWall;

  public FroggerDriveToWallCommand(double inches, final Frogger frogger, final Chassis chassis) {
    super(frogger, chassis);
    distanceFromWall = inches;
  }

  @Override
  public boolean isFinished() {
    if (super.isFinished()) {
      return true;
    }
    double distance = 0.0;
    if (chassis.getLidarDistanceInches() != -1.0) {
      distance = chassis.getLidarDistanceInches();
    }
    return distance <= distanceFromWall;
  }
}
