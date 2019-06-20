package org.firebears.commands;

import org.firebears.subsystems.Chassis;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;

public class ResetNavXCommand extends InstantCommand {

  private final Chassis chassis;

  public ResetNavXCommand(final Chassis chassis) {
    this.chassis = chassis;
  }

  @Override
  public void initialize() {
    chassis.resetNavX();
    System.out.println("INITIALIZE: " + this);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
