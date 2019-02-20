package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class CargoGrabber extends Subsystem {
    final Preferences config = Preferences.getInstance();
    private WPI_TalonSRX motor;
    private final double MOTOR_SPEED = 1.0;
    private DigitalInput haveCargo;
    private DigitalInput cargoOnRight;
    private DigitalInput cargoOnLeft;

    public CargoGrabber() {
        motor = new WPI_TalonSRX(config.getInt("cargoGrabber.motor.canID", 13));
        motor.setNeutralMode(NeutralMode.Brake);
        haveCargo = new DigitalInput(config.getInt("cargoGrabber.haveCargo.dio", 5));
        cargoOnRight = new DigitalInput(config.getInt("cargoGrabber.cargoOnRight.dio", 6));
        cargoOnLeft = new DigitalInput(config.getInt("cargoGrabber.cargoOnLeft.dio", 7));
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("cargoMotorSpeed", motor.get());

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
