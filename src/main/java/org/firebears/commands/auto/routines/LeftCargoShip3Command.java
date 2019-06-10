package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;
import org.firebears.commands.*;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.HatchGrabber;
import org.firebears.subsystems.Tilty;

public class LeftCargoShip3Command extends ParallelCommandGroup {
  public LeftCargoShip3Command(final Chassis chassis, final Tilty tilty, final Elevator elevator, final HatchGrabber hatchGrabber) {
    super(
            new StartingConfigurationLeaveCommand(tilty, elevator),
            new SequentialCommandGroup(
                    new ResetNavXCommand(chassis),
                    new DistanceCommand(80, chassis),
                    new RotateToAngleCommand(90, chassis)
            )
    );

//    addParallel(new StartingConfigurationLeaveCommand());
//    addSequential(new ResetNavXCommand());
//    addSequential(new DistanceCommand(80));
//    addSequential(new RotateToAngleCommand(90));
//    //addSequential(new DriveToVisionTargetCommand());
//    //addSequential(new ElevatorCommand(2));
//    // addSequential(new DistanceCommand(4));
//    //addSequential(new HatchReleaseCommand());
//     //addSequential(new DistanceCommand(-6));
//    // addSequential(new RotateToAngleCommand(180));
  }
}