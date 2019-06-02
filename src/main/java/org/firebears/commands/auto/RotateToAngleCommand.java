package org.firebears.commands.auto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.experimental.command.PIDCommand;

public class RotateToAngleCommand extends PIDCommand {
  protected double turnValue;
  protected final double SPEED = 0.5;
  protected double angleTolerance = 3;
  protected double targetAngle;
  double angle;
  long timeout;
  double rampRate;

  public RotateToAngleCommand(double angle) {
    super("RotateToAngleCommand", Preferences.getInstance().getDouble("RotateToAngleCommand.p", 0.0),
        Preferences.getInstance().getDouble("RotateToAngleCommand.i", 0.0),
        Preferences.getInstance().getDouble("RotateToAngleCommand.d", 0.0));
    addRequirements(Robot.chassis);

    this.angle = bound(angle);

    getPIDController().setInputRange(-180, 180);
    getPIDController().setContinuous(true);
    getPIDController().setAbsoluteTolerance(angleTolerance);
  }

  private static double getAngleDifference(double angle1, double angle2) {
    return bound(angle2 - angle1);
  }

  protected static double bound(double angle) {
    while (angle > 180)
      angle -= 360;
    while (angle < -180)
      angle += 360;
    return angle;
  }

  private double getAngleDifference() {
    return getAngleDifference(Robot.chassis.getAngle(), targetAngle);
  }

  private boolean isClosedLoop;

  @Override
  public void initialize() {
    super.initialize();
    isClosedLoop = Robot.chassis.pidFrontLeft.isClosedLoop();
    Robot.chassis.pidFrontLeft.setClosedLoop(false);
    Robot.chassis.pidFrontRight.setClosedLoop(false);
    rampRate = Robot.chassis.getRampRate();
    Robot.chassis.setRampRate(0.1);
    timeout = System.currentTimeMillis() + 1000 * 5;
    turnValue = bound(angle - Robot.chassis.getAngle());
    targetAngle = bound(angle);
    getPIDController().setSetpoint(0.0);

  }

  @Override
  public void execute() {
  }

  @Override
  public boolean isFinished() {
    double difference = getAngleDifference();
    if (System.currentTimeMillis() >= timeout) {
      return true;
    } else {

      return Math.abs(Robot.chassis.getVelocity()) < 1 && Math.abs(getAngleDifference()) < angleTolerance;
    }
    // return false;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.chassis.pidFrontLeft.setClosedLoop(isClosedLoop);
    Robot.chassis.pidFrontRight.setClosedLoop(isClosedLoop);
    Robot.chassis.drive(0.0, 0.0);
    Robot.chassis.setRampRate(rampRate);

  }

  protected void usePIDOutput(double output) {
    output = Math.max(-SPEED, Math.min(output, SPEED));
    Robot.chassis.drive(0, -output);
  }

  protected double returnPIDInput() {
    return getAngleDifference();
  }

  @Override
  public String toString() {
    return "RotateToAngleCommand(" + angle + ")";
  }

  public void setTargetAngle(double angle) {
    targetAngle = bound(angle);
  }
}
