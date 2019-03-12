package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;
import org.firebears.commands.auto.teleopAuto.ElevatorCargoCommand;
import org.firebears.commands.auto.teleopAuto.ElevatorHatchPlaceCommand;
import org.firebears.commands.ElevatorWithBrakeCommand;
import org.firebears.commands.HatchReleaseCommand;

import org.firebears.commands.ResetNavXCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.firebears.commands.VisionConditionalCommand;
import org.firebears.commands.*;


public class RightRocketAutoCommand extends CommandGroup {
  public RightRocketAutoCommand() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(60));
    addParallel(new StartingConfigurationLeaveCommand());
    addSequential(new RotateToAngleCommand(90));
    addSequential(new DistanceCommand(56));
    addSequential(new RotateToAngleCommand(30));
    addSequential(new DistanceCommand(35));
    addSequential(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(4.59)));
   // addSequential(new RotateToAngleCommand(180));
  }
}
