package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Indicate that the elevator is at the bottom. This zeros out the starting
 * distance.
 */
public class ResetElevatorEncoderCommand extends Command {
  public ResetElevatorEncoderCommand() {

  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.elevator.resetEncoder();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

}
