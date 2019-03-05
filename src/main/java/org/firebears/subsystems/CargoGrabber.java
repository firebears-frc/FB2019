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
        cargoCapturedSensor = new DigitalInput(config.getInt("cargoGrabber.haveCargo.dio", 6));
        cargoLeftSensor = new DigitalInput(config.getInt("cargoGrabber.cargoOnLeft.dio", 8));
        cargoRightSensor = new DigitalInput(config.getInt("cargoGrabber.cargoOnRight.dio", 7));
        cargoCapturedSensorWidget = Robot.programmerTab.add("Cargo captured", false).withPosition(10, 0).getEntry();
        leftSensorWidget = Robot.programmerTab.add("Cargo on Left", false).withPosition(13, 0).getEntry();
        rightSensorWidget = Robot.programmerTab.add("Cargo on Right", false).withPosition(16, 0).getEntry();
        addChild("motor", motor);
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("cargoMotorSpeed", motor.get());
        leftSensorWidget.setBoolean(isCargoOnLeft());
        rightSensorWidget.setBoolean(isCargoOnRight());
        cargoCapturedSensorWidget.setBoolean(isCargoCaptured());
    }

    public boolean isCargoCaptured() {
        return cargoCapturedSensor.get();
    }

    public boolean isCargoOnLeft() {
        return cargoLeftSensor.get();
    }

    public boolean isCargoOnRight() {
        return cargoRightSensor.get();
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
