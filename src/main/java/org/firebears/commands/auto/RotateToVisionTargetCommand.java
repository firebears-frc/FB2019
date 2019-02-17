package org.firebears.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Vision;

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
