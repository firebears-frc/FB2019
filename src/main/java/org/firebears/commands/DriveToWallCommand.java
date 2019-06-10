package org.firebears.commands;

import org.firebears.subsystems.Chassis;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class DriveToWallCommand extends SendableCommandBase {

  private Timer timer = new Timer();
  private double distance;
  private double distanceFromWall;
  private final Chassis chassis;

  public DriveToWallCommand(double inches, final Chassis chassis) {
    this.chassis = chassis;
    addRequirements(chassis);
    distanceFromWall = inches;
  }

  @Override
  public void initialize() {
    distance = chassis.getLidarDistanceInches();
    timer.reset();
    timer.start();
    System.out.println("INITIALIZE: " + this);
  }

  @Override
  public void execute() {
    if (chassis.getLidarDistanceInches() != -1.0) {
      distance = chassis.getLidarDistanceInches();
    } else {
      distance = 0;
    }

    chassis.drive(0.4, 0.0);

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
    chassis.drive(0.0, 0.0);
  }

  @Override
  public String toString() {
    return "DriveToWallCommand(" + distanceFromWall + ")";
  }

}
