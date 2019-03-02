package org.firebears.commands.auto;

import org.firebears.Robot;

public class DriveToVisionTargetDistanceCommand extends DistanceCommand {
  public DriveToVisionTargetDistanceCommand() {
    super(0);
  }

  @Override
  protected void initialize() {
    if (Robot.vision.getVisionTargetConfidence() == 0.0) {
      this.cancel();
    } else {
      double distance = Robot.vision.getVisionTargetDistance();
      distanceGoal = distance - 23;
      super.initialize();
    }
  }
}
