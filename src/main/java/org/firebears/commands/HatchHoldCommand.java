
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HatchHoldCommand extends Command {

    public HatchHoldCommand() {
        requires(Robot.hatchGrabber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.hatchGrabber.rotate();
    }

    @Override
    protected boolean isFinished() {
        if (Robot.hatchGrabber.getRotationSensorValue()) {
            return false;
        }
        return true;
    }

    @Override
    protected void end() {
        Robot.hatchGrabber.stopRotate();
    }

}
