package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.CargoGrabber;

public class CargoSpitCommand extends SendableCommandBase {
    final CargoGrabber cargoGrabber;

    public CargoSpitCommand(final CargoGrabber cargoGrabber) {
        this.cargoGrabber = cargoGrabber;
        addRequirements(cargoGrabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        cargoGrabber.spit();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cargoGrabber.hold();
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
