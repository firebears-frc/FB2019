package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;
import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.Tilty;

public class StartingConfigurationLeaveCommand extends ParallelCommandGroup {

  public StartingConfigurationLeaveCommand(final Tilty tilty, Elevator elevator) {
    super(
            new TiltyExtendCommand(tilty),
            new SequentialCommandGroup(
                    new DisableBrakeCommand(elevator),
                    new ElevatorGroundCommand(elevator),
                    new ElevatorCommand(6, elevator)
            )
    );
  }
}
