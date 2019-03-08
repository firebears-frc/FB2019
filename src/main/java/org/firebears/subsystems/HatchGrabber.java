package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGrabber extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX motor;
    private final double MOTOR_SPEED = 0.7;
    private DigitalInput hatchRotationSensor;
    private DigitalInput hatchCapturedSensor;

    private final NetworkTableEntry hatchCapturedwidget;
    private final NetworkTableEntry hatchRotationwidget;

    public HatchGrabber() {
        motor = new WPI_TalonSRX(config.getInt("hatchGrabber.motor.canID", 14));
        addChild(motor);
        hatchCapturedSensor = new DigitalInput(config.getInt("hatchGrabber.hatchCaptured.dio", 5));
        hatchRotationSensor = new DigitalInput(config.getInt("hatchGrabber.hatchRotationSensor.dio", 9));

        hatchCapturedwidget = Robot.programmerTab.add("hatch captured", false).withPosition(10, 3).getEntry();
        hatchRotationwidget = Robot.programmerTab.add("hatch rotation", false).withPosition(13, 3).getEntry();
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        hatchCapturedwidget.setBoolean(hatchCapturedSensor.get());
        hatchRotationwidget.setBoolean(hatchRotationSensor.get());
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
