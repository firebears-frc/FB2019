
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class VisionConditionalCommand extends ConditionalCommand {
  public VisionConditionalCommand(Command Cmd) {
    super("VisionConditional", Cmd);
  }
  @Override
protected boolean condition() {
  if (Robot.vision.getVisionTargetConfidence() == 1){
    return true;
  }
  return false;
}

}
