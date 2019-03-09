package org.firebears.commands.auto.routines;

import org.firebears.commands.*;
import org.firebears.commands.auto.*;
import org.firebears.commands.auto.teleopAuto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterAutoCommand extends CommandGroup {
  
  public CenterAutoCommand() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(72));
    addParallel(new StartingConfigurationLeaveCommand());
    addSequential(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59)));
    addSequential(new RotateToAngleCommand(180));
  }
}
