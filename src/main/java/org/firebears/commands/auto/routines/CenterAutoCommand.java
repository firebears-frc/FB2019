package org.firebears.commands.auto.routines;

import org.firebears.commands.*;
import org.firebears.commands.auto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterAutoCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CenterAutoCommand() {
    addSequential(new ResetNavXCommand());
    addSequential(new DistanceCommand(72));
    // addSequential(new DriveToVisionTargetCommand());
  }
}
