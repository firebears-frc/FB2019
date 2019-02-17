package org.firebears.commands.auto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToVisionTargetDistanceCommand extends DistanceCommand {
  public DriveToVisionTargetDistanceCommand() {
    super(0);
  }

  @Override
  protected void initialize() {
    double distance = Robot.vision.getVisionTargetDistance();
    distanceGoal = distance - 15;
    super.initialize();
  }
}
