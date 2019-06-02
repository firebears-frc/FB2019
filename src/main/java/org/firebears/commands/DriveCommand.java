package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import edu.wpi.first.wpilibj.Preferences;

public class DriveCommand extends SendableCommandBase {

    final Preferences config;
    int joystickSpeedAxis;
    int joystickRotateAxis;
    double adjust;

    public DriveCommand(Chassis chassis) {
        config = Preferences.getInstance();
        joystickSpeedAxis = config.getInt("joystick1.speedAxis", 1);
        joystickRotateAxis = config.getInt("joystick1.rotateAxis", 4);
        adjust = config.getDouble("driveCommand.deadBand", 0.1);
        addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double speed = -1 * Robot.oi.getXboxController().getRawAxis(joystickSpeedAxis);
        double rotation = Robot.oi.getXboxController().getRawAxis(joystickRotateAxis) * 0.6;
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
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
