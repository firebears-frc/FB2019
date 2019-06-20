package org.firebears.commands.auto;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

import org.firebears.commands.DriveToWallCommand;
import org.firebears.commands.VisionConditionalCommand;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Vision;

public class DriveToVisionTargetCommand extends SequentialCommandGroup {
    /**
     * Add your docs here.
     */
    public DriveToVisionTargetCommand(final Chassis chassis, final Vision vision) {
        super(new VisionConditionalCommand(new RotateToVisionTargetCommand(chassis, vision), vision),
                new DriveToWallCommand(20, chassis));
    }
    
}
