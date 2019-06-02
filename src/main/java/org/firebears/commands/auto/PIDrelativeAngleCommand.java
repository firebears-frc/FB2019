package org.firebears.commands.auto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.experimental.command.PIDCommand;

public class PIDrelativeAngleCommand extends PIDCommand {
  double targetAngle;
  double currentAngle;
  double previousSpeed;
  boolean previousBrakingMode;
  private Timer timer = new Timer();

  public PIDrelativeAngleCommand(double rotation) {
    super("PIDrelativeAngleCommand", Preferences.getInstance().getDouble("PIDrelativeAngleCommand.p", 0.0),
        Preferences.getInstance().getDouble("PIDrelativeAngleCommand.i", 0.0),
        Preferences.getInstance().getDouble("PIDrelativeAngleCommand.d", 0.0));

    targetAngle = rotation;
    addRequirements(Robot.chassis);

  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    double initAngle = Robot.chassis.getAngle();
    setSetpoint(initAngle + targetAngle);
    currentAngle = initAngle;
    previousBrakingMode = Robot.chassis.isBrakingMode();
    Robot.chassis.setBrakingMode(true);

  }

  protected void usePIDOutput(double output) {
    double speed = clamp((previousSpeed - 0.05), output, (previousSpeed + 0.05));
    if (speed > 0.0 && speed < 0.15) {
      speed = 0.15;
    } else if (speed < 0 && speed > -0.15) {
      speed = -0.15;
    }
    speed = clamp(-0.5, speed, 0.5);

    Robot.chassis.drive(0, speed);
    previousSpeed = speed;

  }

  protected static double bound(double angle) {
    while (angle > 180)
      angle -= 360;
    while (angle < -180)
      angle += 360;
    return angle;
  }

  private double clamp(double minValue, double value, double maxValue) {
    return Math.max(minValue, Math.min(value, maxValue));
  }

  protected double returnPIDInput() {
    return Robot.chassis.getAngle();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(10.0)) {
      return true;
    }
    double getAngleDifference = Robot.chassis.getAngle() - getSetpoint();
    return getAngleDifference < 3 && getAngleDifference > -3;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.chassis.drive(0, 0);
    Robot.chassis.setBrakingMode(previousBrakingMode);
  }

  public void setTargetAngle(double angle) {
    double initAngle = Robot.chassis.getAngle();
    targetAngle = bound(angle);
    setSetpoint(initAngle + targetAngle);
  }
}
