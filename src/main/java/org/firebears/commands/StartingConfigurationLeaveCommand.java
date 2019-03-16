package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartingConfigurationLeaveCommand extends CommandGroup {

  public StartingConfigurationLeaveCommand() {
    addParallel(new TiltyExtendCommand());
    addSequential(new DisableBrakeCommand());
    addSequential(new ElevatorGroundCommand());
    addSequential(new ElevatorCommand(6));
  }
}
