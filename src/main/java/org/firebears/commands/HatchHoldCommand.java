
package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class HatchHoldCommand extends SendableCommandBase {

    private Timer timer = new Timer();

    public HatchHoldCommand() {
        addRequirements(Robot.hatchGrabber);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        Robot.hatchGrabber.rotate();
    }

    @Override
    public boolean isFinished() {
        if (Robot.hatchGrabber.getRotationSensorValue()) {
            return false;
        }
        if (timer.get() > 3.0){
            return true;
        }
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.hatchGrabber.stopRotate();
    }

}
