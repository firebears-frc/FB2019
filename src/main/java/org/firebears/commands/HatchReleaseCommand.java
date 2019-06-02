
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class HatchReleaseCommand extends SendableCommandBase {

    public HatchReleaseCommand() {
        addRequirements(Robot.hatchGrabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Robot.hatchGrabber.rotate();
    }

    @Override
    public boolean isFinished() {
        if (Robot.hatchGrabber.getRotationSensorValue()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.hatchGrabber.stopRotate();

    }

}
