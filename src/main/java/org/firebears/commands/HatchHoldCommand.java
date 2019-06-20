
package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.HatchGrabber;

public class HatchHoldCommand extends SendableCommandBase {

    private Timer timer = new Timer();
    final HatchGrabber hatchGrabber;

    public HatchHoldCommand(final HatchGrabber hatchGrabber) {
        this.hatchGrabber = hatchGrabber;
        addRequirements(hatchGrabber);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        hatchGrabber.rotate();
    }

    @Override
    public boolean isFinished() {
        if (hatchGrabber.getRotationSensorValue()) {
            return false;
        }
        if (timer.get() > 3.0){
            return true;
        }
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        hatchGrabber.stopRotate();
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
