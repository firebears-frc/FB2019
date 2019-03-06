package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartingConfigurationCommand extends CommandGroup {

  public StartingConfigurationCommand() {
    addSequential(new ElevatorWithBrakeCommand(20));
    addParallel(new TiltyRetractCommand());
    addParallel(new HatchHoldCommand());
  }
}
