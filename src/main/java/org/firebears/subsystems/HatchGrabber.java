package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchGrabber extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX motor;
    private final double MOTOR_SPEED = 0.5;
    public DigitalInput rotationSensor;

    public HatchGrabber() {
        motor = new WPI_TalonSRX(config.getInt("hatchGrabber.motor.canID", 14));
        addChild(motor);
        int chassisRightSensorDio = config.getInt("chassis.rotationSensor.dio", 5);
        rotationSensor = new DigitalInput(chassisRightSensorDio);

    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {

    }

    public void rotate() {
        motor.set(MOTOR_SPEED);
    }

    public void stopRotate() {
        motor.set(0.0);
    }

    public boolean getSensorValue() {
        return rotationSensor.get();
    }
}
