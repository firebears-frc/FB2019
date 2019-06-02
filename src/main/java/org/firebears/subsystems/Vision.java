package org.firebears.subsystems;

import org.firebears.Robot;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;

public class Vision extends SendableSubsystemBase {

    NetworkTable visionTargetTable;

    public static final String VISION_TARGET_TABLE_NAME = "visionTarget";

    /** Horizontal angle in degrees to the target. */
    public static final String VISION_TARGET_ANGLE_X = "visionTarget.angleX";

    /** Vertical angle in degrees to the target. */
    public static final String VISION_TARGET_ANGLE_Y = "visionTarget.angleY";

    /** Distanct to the target in inches. */
    public static final String VISION_TARGET_DISTANCE = "visionTarget.distance";

    /** Confidence that we see a valid target, in the range 0.0 to 1.0. */
    public static final String VISION_TARGET_CONFIDENCE = "visionTarget.confidence";

    /** Number of vision target pairs */
    public static final String VISION_TARGET_PAIRS = "visionTarget.pairs";

    /** Processing throughput in frames per second. */
    public static final String VISION_TARGET_FPS = "visionTarget.fps";

    public static final String VISION_TARGET_WIDTH = "visionTarget.width";

    public static final String VISION_TARGET_SAVE_IMAGE_TIME = "visionTarget.saveImageTime";

    private NetworkTableEntry angleXWidget;
    private NetworkTableEntry distanceWidget;
    private NetworkTableEntry targetCountWidget;

    public Vision() {
        visionTargetTable = NetworkTableInstance.getDefault().getTable(VISION_TARGET_TABLE_NAME);
        angleXWidget = Robot.programmerTab.add("Vision Angle X", 0.0).withSize(4, 2).withPosition(20, 0).getEntry();
        distanceWidget = Robot.programmerTab.add("Vision Distance", 0.0).withSize(4, 2).withPosition(20, 2).getEntry();
        targetCountWidget = Robot.programmerTab.add("Vision Target Pairs", 0.0).withSize(4, 2).withPosition(20, 4).getEntry();
    }


int count = 0;
    @Override
    public void periodic() {
        angleXWidget.setNumber(getVisionTargetAngleX());
        distanceWidget.setNumber(getVisionTargetDistance());
        targetCountWidget.setNumber(getVisionTargetPairs());
        if ((++ count)% 50 == 0){
            System.out.println("VISION :"+getVisionTargetConfidence()+ " " + getVisionTargetPairs());
        }
   }

    public double getVisionTargetAngleX() {
        return visionTargetTable.getEntry(VISION_TARGET_ANGLE_X).getDouble(0);
    }

    public double getVisionTargetAngleY() {
        return visionTargetTable.getEntry(VISION_TARGET_ANGLE_X).getDouble(0);
    }

    public double getVisionTargetDistance() {
        return visionTargetTable.getEntry(VISION_TARGET_DISTANCE).getDouble(0);
    }

    public double getVisionTargetConfidence() {
        return visionTargetTable.getEntry(VISION_TARGET_CONFIDENCE).getDouble(0);
    }

    public double getVisionTargetPairs() {
        return visionTargetTable.getEntry(VISION_TARGET_PAIRS).getDouble(0);
    }

    public double getVisionTargetFPS() {
        return visionTargetTable.getEntry(VISION_TARGET_PAIRS).getDouble(0);
    }

    public double getVisionTargetWidth() {
        return visionTargetTable.getEntry(VISION_TARGET_WIDTH).getDouble(0);
    }

    public void setVisionTargetSaveImageTime(double miliSeconds) {
        visionTargetTable.getEntry(VISION_TARGET_SAVE_IMAGE_TIME).setNumber(miliSeconds);
    }

    public boolean getVisionTargetConfidenceBoolean(){
        if (getVisionTargetConfidence() == 1){
            return true;
        } else {
            return false;
        }
    }
}
