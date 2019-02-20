package org.firebears.commands.auto.routines;

import org.firebears.commands.*;
import org.firebears.commands.auto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterAutoCommand extends CommandGroup {
  
  public CenterAutoCommand() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(72));
    // addSequential(new DriveToVisionTargetCommand());
    //addSequential(new ElevatorCommand(2));
    // addSequential(new DistanceCommand(4));
    //addSequential(new HatchReleaseCommand());
    //addSequential(new DistanceCommand(-6));
    // addSequential(new RotateToAngleCommand(180));
  }
}
