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

    public DriveCommand() {
        config = Preferences.getInstance();
        joystickSpeedAxis = config.getInt("joystick1.speedAxis", 1);
        joystickRotateAxis = config.getInt("joystick1.rotateAxis", 4);
        requires(Robot.chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double speed = -1 * Robot.oi.controller1.getRawAxis(joystickSpeedAxis);
        double rotation = Robot.oi.controller1.getRawAxis(joystickRotateAxis);
        Robot.chassis.drive(speed, rotation);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }
}
