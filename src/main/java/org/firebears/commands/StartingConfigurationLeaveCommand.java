package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartingConfigurationLeaveCommand extends CommandGroup {

  public StartingConfigurationLeaveCommand() {
    addParallel(new ElevatorWithBrakeCommand(6));
    addParallel(new TiltyExtendCommand());
  }
}
