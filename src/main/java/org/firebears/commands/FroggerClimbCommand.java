package org.firebears.commands;

import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Frogger;
import org.firebears.subsystems.Elevator;

import edu.wpi.first.wpilibj.experimental.command.SequentialCommandGroup;

/**
 * Climb to Hab 3.
 * <p>
 * Before staring, the robot should be on Hab 1 or at least close to Hab.
 */
public class FroggerClimbCommand extends SequentialCommandGroup {
    public FroggerClimbCommand(final Frogger frogger, final Elevator elevator, final Chassis chassis) {
        super(
                new ElevatorCommand(22, elevator),
                new DriveToWallCommand(5, chassis),
                new FroggerElevatorClimbCommand(frogger, elevator),
                new FroggerDriveCommand(frogger, chassis),
                new FroggerRaiseCommand(Frogger.MAX_FROGGER_DISTANCE - 2.0, frogger),
                new ElevatorSetBrakeCommand(false, elevator)
        );

        // //addSequential(new DisableBrakeCommand());
        // addSequential(new FroggerElevatorClimbCommand());
        // addParallel(new ElevatorCommand(0.0));
        // addSequential(new FroggerDriveCommand());
        // addSequential(new FroggerRaiseCommand());
    }
}