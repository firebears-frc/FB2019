package org.firebears.commands.auto;

import org.firebears.commands.*;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Vision;

public class RotateToVisionTargetCommand extends RelativeAngleCommand {

  private double x;
  private final Vision vision;

  public RotateToVisionTargetCommand(final Chassis chassis, final Vision vision) {
    super(0, chassis);
    this.vision = vision;
  }

  @Override
  public void initialize() {
    super.initialize();
    x = vision.getVisionTargetAngleX();
    setTargetAngle(chassis.getAngle() + x);
    vision.setVisionTargetSaveImageTime(500.0);
    System.out.println("INITIALIZE: " + this);
  }

    @Override
    public void end(boolean interrupted) {
      super.end(interrupted);
      vision.setVisionTargetSaveImageTime(0.0);
    }
}
