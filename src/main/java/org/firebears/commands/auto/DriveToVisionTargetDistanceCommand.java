package org.firebears.commands.auto;

import org.firebears.Robot;

public class DriveToVisionTargetDistanceCommand extends DistanceCommand {
  public DriveToVisionTargetDistanceCommand() {
    super(0);
  }

  @Override
  protected void initialize() {
      double distance = Robot.vision.getVisionTargetDistance();
      distanceGoal = distance - 23;
      super.initialize();
  }
}
