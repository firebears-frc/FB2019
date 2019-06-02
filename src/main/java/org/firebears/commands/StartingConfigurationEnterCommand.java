package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;

public class StartingConfigurationEnterCommand extends ParallelCommandGroup {

    public StartingConfigurationEnterCommand() {
        // addSequential(new ElevatorSetBrakeCommand(false));
        super(
                new ElevatorCommand(20),
                new TiltyRetractCommand(),
                new HatchHoldCommand()
        );
        // addSequential(new ElevatorSetBrakeCommand(true));
    }
}
