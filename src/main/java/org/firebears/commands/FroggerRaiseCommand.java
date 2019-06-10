package org.firebears.commands;

import org.firebears.subsystems.Frogger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class FroggerRaiseCommand extends SendableCommandBase {

  private final double goal;
  private Timer timer = new Timer();
  private final Frogger frogger;

  /** Raise Frogger all the way up. */
  public FroggerRaiseCommand(final Frogger frogger) {
    this(0.0, frogger);
  }

  /** Raise Frogger to a specific height.  Note: height=0 is all the way retracted. */
  public FroggerRaiseCommand(double setpoint, final Frogger frogger) {
    this.frogger = frogger;
    addRequirements(frogger);
    goal = setpoint;
  }

  @Override
  public void initialize() {
   timer.reset();
   timer.start();
    // Robot.frogger.enable();
  }

  @Override
  public void execute() {
    // Robot.frogger.setSetpoint(goal);
    frogger.footup();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(3.0)) {
      return true;
    }
    return (Math.abs(frogger.encoderDistance() - goal) <= 0.5);
  }

  @Override
  public void end(boolean interrupted) {
    // Robot.frogger.footStop();
    frogger.setIsJumping(false);
  }
}
