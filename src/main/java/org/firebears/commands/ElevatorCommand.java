package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.Elevator;

public class ElevatorCommand extends SendableCommandBase {
    private Timer timer = new Timer();
    double distanceGoal;
    private final Elevator elevator;

    public ElevatorCommand(double inches, final Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
        distanceGoal = inches;

    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        System.out.println("INITIALIZE: " + this);
    }

    @Override
    public void execute() {
        elevator.setSetpoint(distanceGoal);
    }

    @Override
    public boolean isFinished() {
        if (timer.get() > 3.0) {
            return true;
        }
        return Math.abs(distanceGoal - elevator.inchesTraveled()) < 1;
    }

    @Override
    public void end(boolean interrupt) {
    }

    @Override
    public String toString() {
        return "ElevatorCommand(" + distanceGoal + ")";
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
