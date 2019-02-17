package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;

import org.firebears.commands.ResetNavXCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftRocketAutoCommand extends CommandGroup {
  public LeftRocketAutoCommand() {
    addSequential(new ResetNavXCommand());
    // addSequential(new WaitCommand(.25));
    addSequential(new DistanceCommand(60));
    // addSequential(new WaitCommand(.25));
    addSequential(new RotateToAngleCommand(-90));
    addSequential(new DistanceCommand(52));
    // addSequential(new WaitCommand(.25));
    addSequential(new RotateToAngleCommand(-30));
    addSequential(new DistanceCommand(35));
    // addSequential(new DriveToVisionTargetCommand());
  }
}
