package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartingConfigurationEnterCommand extends CommandGroup {

  public StartingConfigurationEnterCommand() {
    addParallel(new ElevatorCommand(20));
    addParallel(new TiltyRetractCommand());
    addParallel(new HatchHoldCommand());
  }
}
