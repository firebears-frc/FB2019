package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FroggerRaiseCommand extends Command {

  private final double goal;

  /** Raise Frogger all the way up. */
  public FroggerRaiseCommand() {
    requires(Robot.frogger);
    goal = 0.0;
  }

  /** Raise Frogger to a specific height.  Note: height=0 is all the way retracted. */
  public FroggerRaiseCommand(double setpoint) {
    requires(Robot.frogger);
    goal = setpoint;
  }

  @Override
  protected void initialize() {
    setTimeout(3);
    Robot.frogger.enable();
  }

  @Override
  protected void execute() {
    Robot.frogger.setSetpoint(goal);
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      return true;
    }
    return (Math.abs(Robot.frogger.encoderDistance() - goal) <= 0.5);
  }

  @Override
  protected void end() {
    Robot.frogger.footStop();
  }
}
