package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;
import org.firebears.subsystems.Elevator;

/**
 * Indicate that the elevator is at the bottom. This zeros out the starting
 * distance.
 */
public class ResetElevatorEncoderCommand extends InstantCommand {

    private final Elevator elevator;

    public ResetElevatorEncoderCommand(final Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void execute() {
        elevator.resetEncoder();
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
