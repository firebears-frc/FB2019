package org.firebears.commands.auto.routines;

import org.firebears.commands.*;
import org.firebears.commands.auto.*;
import org.firebears.commands.auto.teleopAuto.*;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

public class CenterAutoCommand extends SequentialCommandGroup {

    public CenterAutoCommand() {
        super(
                new ResetNavXCommand(),
                new DistanceCommand(72),
                new StartingConfigurationLeaveCommand(),
                new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59))
        );
    }

    ;
    // addSequential(new RotateToAngleCommand(180));
}

