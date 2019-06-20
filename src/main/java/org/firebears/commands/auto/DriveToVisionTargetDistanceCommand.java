package org.firebears.commands.auto;

import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Vision;

public class DriveToVisionTargetDistanceCommand extends DistanceCommand {

    private final Vision vision;

    public DriveToVisionTargetDistanceCommand(final Chassis chassis, final Vision vision) {
        super(0, chassis);
        this.vision = vision;
    }

    @Override
    public void initialize() {
        double distance = vision.getVisionTargetDistance();
        distanceGoal = distance - 23;
        super.initialize();
        vision.setVisionTargetSaveImageTime(500);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        vision.setVisionTargetSaveImageTime(0.0);
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
