
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class TiltyToggleConditionalCommand extends ConditionalCommand {
  public TiltyToggleConditionalCommand() {
    super("TiltyToggleConditional", new TiltyExtendCommand(), new TiltyRetractCommand());
  }
  @Override
protected boolean condition() {
  if (Robot.tilty.isRetracted()){
    return true;
  }
  return false;
}

}
