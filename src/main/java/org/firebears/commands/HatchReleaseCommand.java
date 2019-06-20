
package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.HatchGrabber;

public class HatchReleaseCommand extends SendableCommandBase {

    final HatchGrabber hatchGrabber;

    public HatchReleaseCommand(final HatchGrabber hatchGrabber) {
        this.hatchGrabber = hatchGrabber;
        addRequirements(hatchGrabber);
    }

    @Override
    public void initialize() {
        System.out.println("INITIALIZE: " + this);
    }

    @Override
    public void execute() {
        hatchGrabber.rotate();
    }

    @Override
    public boolean isFinished() {
        if (hatchGrabber.getRotationSensorValue()) {
            return true;
        }
        return false;
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
