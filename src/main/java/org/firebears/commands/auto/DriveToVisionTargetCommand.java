package org.firebears.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.firebears.commands.DriveToWallCommand;
import org.firebears.commands.VisionConditionalCommand;

public class DriveToVisionTargetCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveToVisionTargetCommand() {
    addSequential(new VisionConditionalCommand(new RotateToVisionTargetCommand()));
    addSequential(new DriveToWallCommand(20));
  }
}
