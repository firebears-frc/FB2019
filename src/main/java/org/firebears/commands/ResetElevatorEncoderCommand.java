package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;

/**
 * Indicate that the elevator is at the bottom. This zeros out the starting
 * distance.
 */
public class ResetElevatorEncoderCommand extends InstantCommand {
  public ResetElevatorEncoderCommand() {
  }

  @Override
  public void execute() {
    Robot.elevator.resetEncoder();
  }


}
