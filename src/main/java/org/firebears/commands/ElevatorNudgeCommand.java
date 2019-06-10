/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import org.firebears.subsystems.Elevator;

public class ElevatorNudgeCommand extends ElevatorCommand {
    private double offsetHeight;
    private final Elevator elevator;

    public ElevatorNudgeCommand(double offset, final Elevator elevator) {
        super(0.0, elevator);
        this.elevator = elevator;
        offsetHeight = offset;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        distanceGoal = elevator.inchesTraveled() + offsetHeight;
    }
}
