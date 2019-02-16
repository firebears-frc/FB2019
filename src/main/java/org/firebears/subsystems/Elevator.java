package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;

public class Elevator extends PIDSubsystem {

  public static final double ENCODER_TICKS_PER_INCH = 48.7;

  WPI_TalonSRX motor1;
  WPI_TalonSRX motor2;
  SpeedControllerGroup motors;
  public Encoder encoder;

  double startingDistance;

  private NetworkTableEntry elevatorHeightWidget;
  private NetworkTableEntry bottomLimitSwitchWidget, topLimitSwitchWidget;

  final Preferences config = Preferences.getInstance();
  private final boolean DEBUG = config.getBoolean("debug", false);

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 0.11),
        Preferences.getInstance().getDouble("elevator.i", 0),
         Preferences.getInstance().getDouble("elevator.d", 0),
        Preferences.getInstance().getDouble("elevator.f", 0));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));
    motors = new SpeedControllerGroup(motor1, motor2);
    addChild("motors", motors);
    motor1.enableCurrentLimit(true);
    motor1.configContinuousCurrentLimit(5);
    motor2.enableCurrentLimit(true);
    motor2.configContinuousCurrentLimit(5);

   
    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).getEntry();
    bottomLimitSwitchWidget = Robot.programmerTab.add("bottom Limit", false).getEntry();
    topLimitSwitchWidget = Robot.programmerTab.add("Top Limit", false).getEntry();

    DigitalInput encoderInputA = new DigitalInput(config.getInt("elevator.encoder.dio.A", 3));
    DigitalInput encoderInputB = new DigitalInput(config.getInt("elevator.encoder.dio.B", 4));
    encoder = new Encoder(encoderInputA, encoderInputB, false, EncodingType.k4X);

    resetEncoder();
    setSetpoint(6);
  }

  @Override
  public void periodic() {
    elevatorHeightWidget.setNumber(inchesTraveled());
    bottomLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isRevLimitSwitchClosed());
    topLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isFwdLimitSwitchClosed());
    // SmartDashboard.putNumber("elevator encoder",
    // motor1.getSelectedSensorPosition()); // TODO - delete later
    SmartDashboard.putNumber("quad encoder", encoder.getDistance()); // TODO - delete later
  }

  @Override
  public void initDefaultCommand() {
  }

  public double inchesTraveled() {
    // double currentDistance = motor1.getSelectedSensorPosition();
    double currentDistance = encoder.getDistance();
    return Math.abs(currentDistance - startingDistance) / ENCODER_TICKS_PER_INCH;
  }

  @Override
  protected double returnPIDInput() {
    return inchesTraveled();
  }

  @Override
  protected void usePIDOutput(double output) {
    output = Math.max(output, 0.04);
    motor1.set(-output);
    motor2.set(-output);
  }

  public void resetEncoder() {
    startingDistance = encoder.getDistance();
    // startingDistance = motor1.getSelectedSensorPosition();
  }
}
