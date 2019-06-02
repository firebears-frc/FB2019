package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class FroggerRaiseCommand extends SendableCommandBase {

  private final double goal;
  private Timer timer = new Timer();

  /** Raise Frogger all the way up. */
  public FroggerRaiseCommand() {
    addRequirements(Robot.frogger);
    goal = 0.0;
  }

  /** Raise Frogger to a specific height.  Note: height=0 is all the way retracted. */
  public FroggerRaiseCommand(double setpoint) {
    addRequirements(Robot.frogger);
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
    Robot.frogger.footup();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(3.0)) {
      return true;
    }
    return (Math.abs(Robot.frogger.encoderDistance() - goal) <= 0.5);
  }

  @Override
  public void end(boolean interrupted) {
    // Robot.frogger.footStop();
    Robot.frogger.setIsJumping(false);
  }
}
