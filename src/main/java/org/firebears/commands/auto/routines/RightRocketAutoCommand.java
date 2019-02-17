package org.firebears.commands.auto.routines;

import org.firebears.commands.auto.*;

import org.firebears.commands.ResetNavXCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RightRocketAutoCommand extends CommandGroup {
  public RightRocketAutoCommand() {
    addSequential(new ResetNavXCommand());
    // addSequential(new WaitCommand(.25));
    addSequential(new DistanceCommand(60));
    // addSequential(new WaitCommand(.25));
    addSequential(new RotateToAngleCommand(90));
    addSequential(new DistanceCommand(52));
    // addSequential(new WaitCommand(.25));
    addSequential(new RotateToAngleCommand(30));
    addSequential(new DistanceCommand(35));
    // addSequential(new DriveToVisionTargetCommand());
  }
}
