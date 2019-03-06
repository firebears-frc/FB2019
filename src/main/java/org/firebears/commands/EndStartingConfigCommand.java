package org.firebears.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class EndStartingConfigCommand extends CommandGroup {

  public EndStartingConfigCommand() {
    addSequential(new ElevatorWithBrakeCommand(6));
    addParallel(new TiltyExtendCommand());
  }
}
