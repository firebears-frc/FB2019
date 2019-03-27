package org.firebears.commands;

import org.firebears.subsystems.Frogger;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Climb to Hab 3.
 * 
 * Before staring, the robot should be on Hab 1 or at least close to Hab.
 */
public class FroggerClimbCommand extends CommandGroup {
  public FroggerClimbCommand() {
    addSequential(new ElevatorCommand(22));
    addSequential(new DriveToWallCommand(5));
    addSequential(new FroggerElevatorClimbCommand());
    addSequential(new FroggerDriveCommand());
    addSequential(new FroggerRaiseCommand(Frogger.MAX_FROGGER_DISTANCE - 2.0));
    addSequential(new ElevatorSetBrakeCommand(false));

    // //addSequential(new DisableBrakeCommand());
    // addSequential(new FroggerElevatorClimbCommand());
    // addParallel(new ElevatorCommand(0.0));
    // addSequential(new FroggerDriveCommand());
    // addSequential(new FroggerRaiseCommand());
  }
}