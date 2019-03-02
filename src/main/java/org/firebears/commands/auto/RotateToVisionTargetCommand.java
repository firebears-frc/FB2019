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
    if (Robot.vision.getVisionTargetConfidence() == 0.0) {
      this.cancel();
    } else {
      super.initialize();
      x = Robot.vision.getVisionTargetAngleX();
      setTargetAngle(Robot.chassis.getAngle() + x);
    }
  }

}
