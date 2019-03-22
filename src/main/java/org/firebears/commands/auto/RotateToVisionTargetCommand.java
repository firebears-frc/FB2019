package org.firebears.commands.auto;

import org.firebears.Robot;

import org.firebears.commands.*;

public class RotateToVisionTargetCommand extends RelativeAngleCommand {

  private double x;

  public RotateToVisionTargetCommand() {
    super(0);
  }

  @Override
  protected void initialize() {
    super.initialize();
    x = Robot.vision.getVisionTargetAngleX();
    setTargetAngle(Robot.chassis.getAngle() + x);
    Robot.vision.setVisionTargetSaveImageTime(500.0);
  }
    @Override
    protected void end() {
      super.end();
      Robot.vision.setVisionTargetSaveImageTime(0.0);
    }
}
