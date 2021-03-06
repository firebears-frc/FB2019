package org.firebears.commands.auto;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DistanceCommand extends PIDCommand {

  double distanceGoal;
  double previousSpeed;
  boolean previousBrakingMode;
  private final Preferences config = Preferences.getInstance();
  private final boolean DEBUG = config.getBoolean("debug", false);

  public DistanceCommand(double inches) {
    super("DistanceCommand", 0.024, 0.0, 0.0024);
    distanceGoal = inches;
    requires(Robot.chassis);
  }

  @Override
  protected void initialize() {
    setTimeout(2 + distanceGoal / 30.0);
    double initDistance = Robot.chassis.inchesTraveled();
    setSetpoint(initDistance + distanceGoal);
    previousSpeed = 0;
    previousBrakingMode = Robot.chassis.isBrakingMode();
    Robot.chassis.setBrakingMode(true);
    if (DEBUG) {
      System.out.println("INITIALIZE: " + this);
    }
  }

  protected void usePIDOutput(double output) {
    double speed = clamp((previousSpeed - 0.05), output, (previousSpeed + 0.05));
    Robot.chassis.drive(speed, 0);
    previousSpeed = speed;
  }

  private double clamp(double minValue, double value, double maxValue) {
    return Math.max(minValue, Math.min(value, maxValue));
  }

  protected double returnPIDInput() {
    return Robot.chassis.inchesTraveled();
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      System.out.println("TIMED OUT");
      return true;
    }
    // System.out.println(Math.abs(getSetpoint() - Robot.chassis.inchesTraveled()));
    return Math.abs(getSetpoint() - Robot.chassis.inchesTraveled()) < 4;
  }

  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
    Robot.chassis.setBrakingMode(previousBrakingMode);

  }

  @Override
  public String toString() {
    return "DistanceCommand(" + distanceGoal + ")";
  }
}