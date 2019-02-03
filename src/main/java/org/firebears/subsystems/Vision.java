package org.firebears.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.firebears.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


public class Vision extends Subsystem {

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

    public Vision() {
        visionTargetTable = NetworkTableInstance.getDefault().getTable(VISION_TARGET_TABLE_NAME);
    }

    @Override
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    /**
     * @return Returns value of Angle X from networktable
     */

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
}
