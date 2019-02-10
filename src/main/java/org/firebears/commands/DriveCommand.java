package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;


import org.firebears.Robot;
import edu.wpi.first.wpilibj.Preferences;

/** 
 *
 */
public class DriveCommand extends Command {

    final Preferences config;
    int joystickSpeedAxis;
    int joystickRotateAxis;
    double adjust;

    public DriveCommand() {
        config = Preferences.getInstance();
        joystickSpeedAxis = config.getInt("joystick1.speedAxis", 1);
        joystickRotateAxis = config.getInt("joystick1.rotateAxis", 4);
        adjust = config.getDouble("driveCommand.deadBand", 0.1);
        requires(Robot.chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double speed = -1 * Robot.oi.controller1.getRawAxis(joystickSpeedAxis);

        double rotation = Robot.oi.controller1.getRawAxis(joystickRotateAxis) * 0.7;
        Robot.chassis.drive(deadBand(speed), deadBand(rotation));
    }

    private double deadBand(double x) {

        double slope = 1 / (1 - adjust);

        if (Math.abs(x) <= adjust) {
            return 0.0;
        } else if (Math.abs(x) == 1) {
            return x;
        } else if (x > 0) {
            return slope * x - (slope - 1);
        } else {
            return slope * x + (slope - 1);
        } 
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }
}
