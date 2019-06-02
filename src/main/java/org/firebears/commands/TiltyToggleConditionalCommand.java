
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.Command;
import edu.wpi.first.wpilibj.experimental.command.ConditionalCommand;

public class TiltyToggleConditionalCommand extends ConditionalCommand {
  public TiltyToggleConditionalCommand() {
    super(new TiltyExtendCommand(), new TiltyRetractCommand(), () -> { return Robot.tilty.isRetracted();});
  }

}
