package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

public class StartingConfigurationLeaveCommand extends ParallelCommandGroup {

  public StartingConfigurationLeaveCommand() {
    super(
            new TiltyExtendCommand(),
            new ParallelCommandGroup(
                    new DisableBrakeCommand(),
                    new ElevatorGroundCommand(),
                    new ElevatorCommand(6)
            )
    );
  }
}
