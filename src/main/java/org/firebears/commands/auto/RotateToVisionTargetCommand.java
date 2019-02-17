package org.firebears.commands.auto;

import org.firebears.Robot;

public class RotateToVisionTargetCommand extends PIDrelativeAngleCommand {

  private double x;

  public RotateToVisionTargetCommand() {
    super(0);
  }

  @Override
  protected void initialize() {
    super.initialize();
    x = Robot.vision.getVisionTargetAngleX();
    setTargetAngle(x);
  }

}
