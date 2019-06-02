package org.firebears.commands;

import org.firebears.subsystems.Frogger;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

/**
 * Climb to Hab 3.
 * <p>
 * Before staring, the robot should be on Hab 1 or at least close to Hab.
 */
public class FroggerClimbCommand extends SequentialCommandGroup {
    public FroggerClimbCommand() {
        super(
                new ElevatorCommand(22),
                new DriveToWallCommand(5),
                new FroggerElevatorClimbCommand(),
                new FroggerDriveCommand(),
                new FroggerRaiseCommand(Frogger.MAX_FROGGER_DISTANCE - 2.0),
                new ElevatorSetBrakeCommand(false)
        );

        // //addSequential(new DisableBrakeCommand());
        // addSequential(new FroggerElevatorClimbCommand());
        // addParallel(new ElevatorCommand(0.0));
        // addSequential(new FroggerDriveCommand());
        // addSequential(new FroggerRaiseCommand());
    }
}