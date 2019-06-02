package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;

public class HatchGrabber extends SendableSubsystemBase {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX motor;
    final double MOTOR_SPEED = 0.7;
    private DigitalInput hatchRotationSensor;
    private DigitalInput hatchCapturedSensor;

    private  NetworkTableEntry hatchCapturedwidget;
    private  NetworkTableEntry hatchRotationwidget;

    private final long dashDelay;
    private long dashTimeout;

    public HatchGrabber(WPI_TalonSRX motor, DigitalInput hatchRotationSensor, DigitalInput hatchCapturedSensor, ShuffleboardTab programmerTab) {
        this.motor = motor;
        this.hatchCapturedSensor = hatchCapturedSensor;
        this.hatchRotationSensor = hatchRotationSensor;

        hatchCapturedwidget = programmerTab.add("hatch captured", false).withPosition(10, 3).getEntry();
        hatchRotationwidget = programmerTab.add("hatch rotation", false).withPosition(13, 3).getEntry();

        dashDelay = config.getLong("dashDelay", 250);
        dashTimeout = System.currentTimeMillis() + dashDelay;
    }

    @Override
    public void periodic() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > dashTimeout) {
            hatchCapturedwidget.setBoolean(hatchCapturedSensor.get());
            hatchRotationwidget.setBoolean(hatchRotationSensor.get());
            dashTimeout = currentTime + dashDelay;
        }
    }

    public void rotate() {
        motor.set(MOTOR_SPEED);
    }

    public void stopRotate() {
        motor.set(0.0);
    }

    public boolean getRotationSensorValue() {
        return hatchRotationSensor.get();
    }
    public boolean getCapturedSensorValue() {
        return hatchCapturedSensor.get();
    }
}
