
package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;
import org.firebears.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightCargoShip2Command extends CommandGroup {
  public RightCargoShip2Command() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(70));
    addSequential(new RotateToAngleCommand(-90));
    //addSequential(new DriveToVisionTargetCommand());
    //addSequential(new ElevatorCommand(2));
    // addSequential(new DistanceCommand(4));
    //addSequential(new HatchReleaseCommand());
     //addSequential(new DistanceCommand(-6));
    // addSequential(new RotateToAngleCommand(180));
  }
}
