package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import org.firebears.util.PIDSparkMotor;

/**
 * Command that uses the PID software inside the Chassis' SparkMAX motor controllers to
 * move specific distances or angles.
 */
public class PIDSparkCommand extends SendableCommandBase {

    public static final double TOLERANCE = 1.0;

    private final PIDSparkMotor pidLeft = Robot.chassis.pidFrontLeft;
    private final PIDSparkMotor pidRight = Robot.chassis.pidFrontRight;
    private final double distanceGoalLeft;
    private final double distanceGoalRight;
    private double initDistanceLeft;
    private double initDistanceRight;

    private Timer timer = new Timer();
    private double timeout = 0.0;

    /**
     * Move the left and right sides specific distances.
     */
    public PIDSparkCommand(double leftInches, double rightInches) {
        addRequirements(Robot.chassis);
        distanceGoalLeft = leftInches;
        distanceGoalRight = rightInches;
        setTimeout();
    }

    /**
     * Rotate a specific number of degrees.
     */
    public PIDSparkCommand(double degrees) {
        addRequirements(Robot.chassis);
        double circumference = Chassis.WHEEL_BASE_INCHES * Math.PI;
        distanceGoalLeft = circumference * degrees / 360.0;
        distanceGoalRight = -1.0 * circumference * degrees / 360.0;
        setTimeout();
    }

    @Override
    public void initialize() {
        initDistanceLeft = pidLeft.inchesTraveled();
        initDistanceRight = pidRight.inchesTraveled();
        System.out.println("INITIALIZE: " + this + "\t" + initDistanceLeft);
    }

    /**
     * Set a reasonable timeout, assuming that the robot can move 60 inches per second.
     */
    private void setTimeout() {
        double maxDistance = Math.max(Math.abs(distanceGoalLeft), Math.abs(distanceGoalRight));
        double time = maxDistance / 60.0;
        timeout = Math.max(1.0, Math.min(time, 10.0));
        timer.reset();
        timer.start();
    }

    private boolean isTimedOut() {
        return timer.hasPeriodPassed(timeout);
    }

    @Override
    public void execute() {
        pidLeft.driveToPosition(initDistanceLeft + distanceGoalLeft);
        pidRight.driveToPosition(initDistanceRight + distanceGoalRight);
    }

    @Override
    public boolean isFinished() {
        if (isTimedOut()) {
            System.out.println("TIMED OUT: " + this);
            return true;
        }
        double leftError = (initDistanceLeft + distanceGoalLeft) - pidLeft.inchesTraveled();
        double rightError = (initDistanceRight + distanceGoalRight) - pidRight.inchesTraveled();
        return Math.abs(leftError) <= TOLERANCE && Math.abs(rightError) <= TOLERANCE;
    }

    @Override
    public void end(boolean interrupted) {
        pidLeft.set(0.0);
        pidRight.set(0.0);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + distanceGoalLeft + "," + distanceGoalRight + ")";
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
