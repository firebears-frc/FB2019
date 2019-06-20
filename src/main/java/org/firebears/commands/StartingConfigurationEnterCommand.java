package org.firebears.commands;

import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.HatchGrabber;
import org.firebears.subsystems.Tilty;
import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;

public class StartingConfigurationEnterCommand extends ParallelCommandGroup {

    public StartingConfigurationEnterCommand(final Tilty tilty, final Elevator elevator, final HatchGrabber hatchGrabber) {
        super(
                new ElevatorCommand(20, elevator),
                new TiltyRetractCommand(tilty),
                new HatchHoldCommand(hatchGrabber)
        );
    }
}
