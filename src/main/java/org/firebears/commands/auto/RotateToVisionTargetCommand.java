package org.firebears.commands.auto;

import org.firebears.Robot;

import org.firebears.commands.*;

public class RotateToVisionTargetCommand extends RelativeAngleCommand {

  private double x;

  public RotateToVisionTargetCommand() {
    super(0);
  }

  @Override
  public void initialize() {
    super.initialize();
    x = Robot.vision.getVisionTargetAngleX();
    setTargetAngle(Robot.chassis.getAngle() + x);
    Robot.vision.setVisionTargetSaveImageTime(500.0);
  }
    @Override
    public void end(boolean interrupted) {
      super.end(interrupted);
      Robot.vision.setVisionTargetSaveImageTime(0.0);
    }
}
