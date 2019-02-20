package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;

import org.firebears.commands.*;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRocketAutoCommand extends CommandGroup {
  public LeftRocketAutoCommand() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(60));
    addSequential(new RotateToAngleCommand(-90));
    addSequential(new DistanceCommand(52));
    addSequential(new RotateToAngleCommand(-30));
    addSequential(new DistanceCommand(35));
    // addSequential(new DriveToVisionTargetCommand());
    //addSequential(new ElevatorCommand(62));
    //addSequential(new DistanceCommand(4));
    //addSequential(new HatchReleaseCommand());
    //addSequential(new DistanceCommand(-6));
     //addSequential(new ElevatorCommand(2));
    // addSequential(new RotateToAngleCommand(180));
  }
}
