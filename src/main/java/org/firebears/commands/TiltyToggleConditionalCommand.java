
package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.ConditionalCommand;
import org.firebears.subsystems.Tilty;

public class TiltyToggleConditionalCommand extends ConditionalCommand {
  public TiltyToggleConditionalCommand(final Tilty tilty) {
    super(new TiltyExtendCommand(tilty),
            new TiltyRetractCommand(tilty),
            () -> { return tilty.isRetracted();});
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
