package org.firebears.commands.auto;

import org.firebears.subsystems.Chassis;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.experimental.command.PIDCommand;

public class DistanceCommand extends PIDCommand {

  double distanceGoal;
  double previousSpeed;
  boolean previousBrakingMode;
  private final Preferences config = Preferences.getInstance();
  private final boolean DEBUG = config.getBoolean("debug", false);

  private Timer timer = new Timer();
  private double timeout = 0.0;
  private final Chassis chassis;

  public DistanceCommand(double inches, final Chassis chassis) {
    super("DistanceCommand", 0.024, 0.0, 0.0024);
    this.chassis = chassis;
    distanceGoal = inches;
    addRequirements(chassis);
  }

  @Override
  public void initialize() {
    super.initialize();
    timeout = 2 + distanceGoal / 30.0;
    timer.reset();
    timer.start();
    double initDistance = chassis.inchesTraveled();
    setSetpoint(initDistance + distanceGoal);
    previousSpeed = 0;
    previousBrakingMode = chassis.isBrakingMode();
    chassis.setBrakingMode(true);
    System.out.println("INITIALIZE: " + this);
  }

  protected void usePIDOutput(double output) {
    double speed = clamp((previousSpeed - 0.05), output, (previousSpeed + 0.05));
    chassis.drive(speed, 0);
    previousSpeed = speed;
  }

  private double clamp(double minValue, double value, double maxValue) {
    return Math.max(minValue, Math.min(value, maxValue));
  }

  protected double returnPIDInput() {
    return chassis.inchesTraveled();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(timeout)) {
      System.out.println("TIMED OUT");
      return true;
    }
    // System.out.println(Math.abs(getSetpoint() - chassis.inchesTraveled()));
    return Math.abs(getSetpoint() - chassis.inchesTraveled()) < 4;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    chassis.drive(0, 0);
    chassis.setBrakingMode(previousBrakingMode);

  }

  @Override
  public String toString() {
    return "DistanceCommand(" + distanceGoal + ")";
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}