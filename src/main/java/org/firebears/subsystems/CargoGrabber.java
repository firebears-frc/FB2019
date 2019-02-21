package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoGrabber extends Subsystem {
    final Preferences config = Preferences.getInstance();
    private WPI_TalonSRX motor;
    private final double MOTOR_SPEED = 1.0;
    private DigitalInput haveCargo;
    private DigitalInput cargoOnRight;
    private DigitalInput cargoOnLeft;
    private NetworkTableEntry cargoStatusWidget;
    private NetworkTableEntry cargoSpeedWidget;

    public static enum CargoStatus {NONE, LEFT, RIGHT, MIDDLE};

    public CargoGrabber() {
        motor = new WPI_TalonSRX(config.getInt("cargoGrabber.motor.canID", 13));
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
        haveCargo = new DigitalInput(config.getInt("cargoGrabber.haveCargo.dio", 6));
        cargoOnRight = new DigitalInput(config.getInt("cargoGrabber.cargoOnRight.dio", 7));
        cargoOnLeft = new DigitalInput(config.getInt("cargoGrabber.cargoOnLeft.dio", 8));
        cargoStatusWidget = Robot.programmerTab.add("Cargo Status", CargoStatus.NONE.toString()).getEntry();
        cargoSpeedWidget = Robot.programmerTab.add("Cargo speed", 0.0).getEntry();
    }

    public CargoStatus getCargoStatus() {
        if (!haveCargo.get()) {
            return CargoStatus.NONE;
        } else if (cargoOnRight.get()) {
            return CargoStatus.RIGHT;
        } else if (cargoOnLeft.get()) {
            return CargoStatus.LEFT;
        } else {
            return CargoStatus.MIDDLE;
        }
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        cargoStatusWidget.setString(getCargoStatus().toString());
        cargoSpeedWidget.setNumber(motor.get());
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
