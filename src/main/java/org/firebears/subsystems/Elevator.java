package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Elevator extends PIDSubsystem {

  public static final double ENCODER_TICKS_PER_INCH = 48.7;

  private final WPI_TalonSRX motor1;
  private final WPI_TalonSRX motor2;
  private final SpeedControllerGroup motors;
  private final Encoder encoder;

  double startingDistance;

  private final NetworkTableEntry elevatorHeightWidget;
  private final NetworkTableEntry bottomLimitSwitchWidget;
  private final NetworkTableEntry topLimitSwitchWidget;
  private final NetworkTableEntry motor1CurrenthWidget;
  private final NetworkTableEntry motor2CurrenthWidget;

  final Preferences config = Preferences.getInstance();

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 0.11),
        Preferences.getInstance().getDouble("elevator.i", 0), Preferences.getInstance().getDouble("elevator.d", 0),
        Preferences.getInstance().getDouble("elevator.f", 0));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));
    motors = new SpeedControllerGroup(motor1, motor2);
    addChild("motors", motors);

    motor1.configFactoryDefault();
    motor1.configContinuousCurrentLimit(10, 10);
    motor1.enableCurrentLimit(true);
    motor2.configFactoryDefault();
    motor2.configContinuousCurrentLimit(10, 10);
    motor2.enableCurrentLimit(true);
    // motor1.configPeakCurrentLimit(15);
    // motor1.configPeakCurrentDuration(1000);
    // motor2.configPeakCurrentLimit(15);
    // motor2.configPeakCurrentDuration(1000);
    motor2.follow(motor1);

    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).getEntry();
    bottomLimitSwitchWidget = Robot.programmerTab.add("Elevator bottom Limit", false).getEntry();
    topLimitSwitchWidget = Robot.programmerTab.add("Elevator Top Limit", false).getEntry();
    motor1CurrenthWidget = Robot.programmerTab.add("Motor " + motor1.getDeviceID() + " current", false).getEntry();
    motor2CurrenthWidget = Robot.programmerTab.add("Motor " + motor2.getDeviceID() + " current", false).getEntry();

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
    motor1CurrenthWidget.setNumber(motor1.getOutputCurrent());
    motor2CurrenthWidget.setNumber(motor2.getOutputCurrent());
  }

  @Override
  public void initDefaultCommand() {
  }

  public double inchesTraveled() {
    double currentDistance = encoder.getDistance();
    return Math.abs(currentDistance - startingDistance) / ENCODER_TICKS_PER_INCH;
  }

  @Override
  protected double returnPIDInput() {
    return inchesTraveled();
  }

  @Override
  protected void usePIDOutput(double output) {
    output = Math.max(output, 0.03);
    motor1.set(-output);
  }

  public void resetEncoder() {
    startingDistance = encoder.getDistance();
    // startingDistance = motor1.getSelectedSensorPosition();
  }
}
