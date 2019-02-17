
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HatchReleaseCommand extends Command {

    public HatchReleaseCommand() {
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
        if (Robot.hatchGrabber.getSensorValue()) {
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        Robot.hatchGrabber.stopRotate();

    }

}
