package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;

public class ResetNavXCommand extends InstantCommand {
  public ResetNavXCommand() {
  }

  @Override
  public void initialize() {
    Robot.chassis.resetNavX();
  }


}
