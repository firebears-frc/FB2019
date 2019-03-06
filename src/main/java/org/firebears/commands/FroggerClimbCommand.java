package org.firebears.commands;

import org.firebears.commands.auto.DistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Climb to Hab 3.
 * 
 * Before staring, the robot should be on Hab 1 or at least close to Hab.
 */
public class FroggerClimbCommand extends CommandGroup {
  public FroggerClimbCommand() {
    addSequential(new ClimbStartCommand());
    addSequential(new FroggerElevatorClimbCommand());
    addSequential(new FroggerDriveCommand());
    // addParallel(new ElevatorWithBrakeCommand(0.0));
    addSequential(new FroggerRaiseCommand());
  }
}