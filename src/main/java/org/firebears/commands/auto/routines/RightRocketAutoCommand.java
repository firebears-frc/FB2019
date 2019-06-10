package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;
import org.firebears.commands.auto.teleopAuto.ElevatorHatchPlaceCommand;
import org.firebears.commands.*;

import org.firebears.commands.ResetNavXCommand;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;
import org.firebears.commands.VisionConditionalCommand;
import org.firebears.commands.*;
import org.firebears.subsystems.*;

public class RightRocketAutoCommand extends SequentialCommandGroup {
  public RightRocketAutoCommand(final Chassis chassis, final Tilty tilty, final Elevator elevator, final HatchGrabber hatchGrabber, final Vision vision) {
    super(
            new ResetNavXCommand(chassis),
            new DistanceCommand(60, chassis),
            new ParallelCommandGroup(
                    new StartingConfigurationLeaveCommand(tilty, elevator),
                    new SequentialCommandGroup(
                            new RotateToAngleCommand(90, chassis),
                            new DistanceCommand(56, chassis),
                            new RotateToAngleCommand(30, chassis),
                            new DistanceCommand(35, chassis),
                            new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59, hatchGrabber, elevator, chassis, vision), vision)
                    )
            )
    );

//    addSequential(new ResetNavXCommand());
//    addSequential(new DistanceCommand(60));
//    addParallel(new StartingConfigurationLeaveCommand());
//    addSequential(new RotateToAngleCommand(90));
//    addSequential(new DistanceCommand(56));
//    addSequential(new RotateToAngleCommand(30));
//    addSequential(new DistanceCommand(35));
//    addSequential(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59)));
//   // addSequential(new RotateToAngleCommand(180));
  }
}
