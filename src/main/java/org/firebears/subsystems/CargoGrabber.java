package org.firebears.subsystems;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CargoGrabber extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX motor;
    private final double MOTOR_SPEED = 1.0;
    public DigitalInput cargoCapturedSensor;
    public DigitalInput cargoLeftSensor;
    public DigitalInput cargoRightSensor;

    private final NetworkTableEntry leftSensorWidget;
    private final NetworkTableEntry rightSensorWidget;
    private final NetworkTableEntry cargoCapturedSensorWidget;

    public CargoGrabber() {
        motor = new WPI_TalonSRX(config.getInt("cargoGrabber.motor.canID", 13));
        motor.setNeutralMode(NeutralMode.Brake);
        cargoCapturedSensor = new DigitalInput(config.getInt("cargoGrabcber.cargoCaptured.dio", 6));
        cargoLeftSensor = new DigitalInput(config.getInt("cargoGrabcber.cargoLeft.dio", 7));
        cargoRightSensor = new DigitalInput(config.getInt("cargoGrabcber.cargoRight.dio", 8));
        leftSensorWidget = Robot.programmerTab.add("Left Sensor", false).getEntry();
        rightSensorWidget = Robot.programmerTab.add("Right Sensor", false).getEntry();
        cargoCapturedSensorWidget = Robot.programmerTab.add("Center Sensor", false).getEntry();
        addChild("motor", motor);
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("cargoMotorSpeed", motor.get());
        leftSensorWidget.setBoolean(cargoLeftSensor.get());
        rightSensorWidget.setBoolean(cargoRightSensor.get());
        cargoCapturedSensorWidget.setBoolean(cargoCapturedSensor.get());
    }

    public void intake() {
        motor.set(MOTOR_SPEED);

    }

    public void spit() {
        motor.set(-MOTOR_SPEED);
    }

    public void hold() {
        motor.set(0.2);
    }
}
