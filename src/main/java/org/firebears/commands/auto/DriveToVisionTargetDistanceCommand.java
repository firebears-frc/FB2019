package org.firebears.commands.auto;

import org.firebears.Robot;

public class DriveToVisionTargetDistanceCommand extends DistanceCommand {
  public DriveToVisionTargetDistanceCommand() {
    super(0);
  }

  @Override
  public void initialize() {
      double distance = Robot.vision.getVisionTargetDistance();
      distanceGoal = distance - 23;
      super.initialize();
      Robot.vision.setVisionTargetSaveImageTime(500);
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.vision.setVisionTargetSaveImageTime(0.0);
  }
}
