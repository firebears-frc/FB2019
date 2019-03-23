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
    addSequential(new ElevatorCommand(24));
    addSequential(new DriveToWallCommand(19));
    addSequential(new FroggerClimbSyncCommand());
    addSequential(new FroggerDriveCommand());
    addSequential(new FroggerRaiseCommand());

    // //addSequential(new DisableBrakeCommand());
    // addSequential(new FroggerElevatorClimbCommand());
    // addParallel(new ElevatorCommand(0.0));
    // addSequential(new FroggerDriveCommand());
    // addSequential(new FroggerRaiseCommand());
  }
}