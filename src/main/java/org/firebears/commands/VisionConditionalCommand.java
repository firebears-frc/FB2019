
package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.WaitCommand;
import org.firebears.subsystems.Vision;

import edu.wpi.first.wpilibj.experimental.command.Command;
import edu.wpi.first.wpilibj.experimental.command.ConditionalCommand;

public class VisionConditionalCommand extends ConditionalCommand {
    public VisionConditionalCommand(Command Cmd, final Vision vision) {
        super(Cmd, new WaitCommand(0.0), () -> { return vision.getVisionTargetConfidence() == 1; });
    }
}
