package org.firebears.commands.auto.routines;

import org.firebears.commands.*;
import org.firebears.subsystems.*;
import org.firebears.commands.auto.*;
import org.firebears.commands.auto.teleopAuto.*;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

public class CenterAutoCommand extends SequentialCommandGroup {

    public CenterAutoCommand(final Chassis chassis, final Tilty tilty, final Elevator elevator, final HatchGrabber hatchGrabber, final Vision vision) {
        super(
                new ResetNavXCommand(chassis),
                new DistanceCommand(72, chassis),
                new StartingConfigurationLeaveCommand(tilty, elevator),
                new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59, hatchGrabber, elevator, chassis, vision), vision)
        );
    }

    ;
    // addSequential(new RotateToAngleCommand(180));
}

