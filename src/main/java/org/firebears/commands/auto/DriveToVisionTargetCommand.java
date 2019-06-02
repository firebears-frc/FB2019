package org.firebears.commands.auto;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

import org.firebears.commands.DriveToWallCommand;
import org.firebears.commands.VisionConditionalCommand;

public class DriveToVisionTargetCommand extends SequentialCommandGroup {
    /**
     * Add your docs here.
     */
    public DriveToVisionTargetCommand() {
        super(new VisionConditionalCommand(new RotateToVisionTargetCommand()),
                new DriveToWallCommand(20));
    }
}
