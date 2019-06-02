
package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;
import org.firebears.commands.*;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

public class RightCargoShip1Command extends ParallelCommandGroup {
  public RightCargoShip1Command() {
    super(
            new StartingConfigurationLeaveCommand(),
            new SequentialCommandGroup(
                    new ResetNavXCommand(),
                    new DistanceCommand(60),
                    new RotateToAngleCommand(-90)
            )
    );

//    addParallel(new StartingConfigurationLeaveCommand());
//    addSequential(new ResetNavXCommand());
//    addSequential(new DistanceCommand(60));
//    addSequential(new RotateToAngleCommand(-90));
//    //addSequential(new DriveToVisionTargetCommand());
//    //addSequential(new ElevatorCommand(2));
//    // addSequential(new DistanceCommand(4));
//    //addSequential(new HatchReleaseCommand());
//     //addSequential(new DistanceCommand(-6));
//    // addSequential(new RotateToAngleCommand(180));
  }
}
