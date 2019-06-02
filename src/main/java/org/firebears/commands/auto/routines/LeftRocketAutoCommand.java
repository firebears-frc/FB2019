package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;

import org.firebears.commands.*;
import org.firebears.commands.auto.teleopAuto.*;

import edu.wpi.first.wpilibj.experimental.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

public class LeftRocketAutoCommand extends SequentialCommandGroup {
  public LeftRocketAutoCommand() {
    super(
            new ResetNavXCommand(),
            new DistanceCommand(60),
            new ParallelCommandGroup(
                    new StartingConfigurationLeaveCommand(),
                    new SequentialCommandGroup(
                            new RotateToAngleCommand(-90),
                            new DistanceCommand(56),
                            new RotateToAngleCommand(-30),
                            new DistanceCommand(35),
                            new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59))
                    )
            )
    );

//    addSequential(new ResetNavXCommand());
//    addSequential(new DistanceCommand(60));
//    addParallel(new StartingConfigurationLeaveCommand());
//    addSequential(new RotateToAngleCommand(-90));
//    addSequential(new DistanceCommand(56));
//    addSequential(new RotateToAngleCommand(-30));
//    addSequential(new DistanceCommand(35));
//    addSequential(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59)));
//    //addSequential(new RotateToAngleCommand(180));
  }
}
