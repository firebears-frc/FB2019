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
//    addSequential(new ElevatorCommand(30));
    addSequential(new DriveToWallCommand(12));
    addSequential(new DistanceCommand(9));
//    addSequential(new FroggerElevatorClimbCommand());
//    addSequential(new FroggerDriveCommand());
//    addSequential(new FroggerRaiseCommand());
  }
}