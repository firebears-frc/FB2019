package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;

import org.firebears.commands.*;
import org.firebears.commands.auto.teleopAuto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRocketAutoCommand extends CommandGroup {
  public LeftRocketAutoCommand() {
    addParallel(new StartingConfigurationLeaveCommand());
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(60));
    addSequential(new RotateToAngleCommand(-90));
    addSequential(new DistanceCommand(56));
    addSequential(new RotateToAngleCommand(-30));
    addSequential(new DistanceCommand(35));
    addSequential(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59)));
    addSequential(new RotateToAngleCommand(180));
  }
}
