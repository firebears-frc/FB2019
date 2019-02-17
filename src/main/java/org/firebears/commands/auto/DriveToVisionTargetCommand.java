package org.firebears.commands.auto;

import org.firebears.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToVisionTargetCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveToVisionTargetCommand() {
    addSequential(new RotateToVisionTargetCommand());
    addSequential(new DriveToVisionTargetDistanceCommand());
  }
}
