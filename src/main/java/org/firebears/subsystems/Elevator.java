package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {

  public static final double ENCODER_TICKS_PER_INCH = 48.7;

  private final WPI_TalonSRX motor1;
  private final WPI_TalonSRX motor2;
  private final SpeedControllerGroup motors;
  private final Encoder encoder;
  public DigitalInput elevatorHighASensor;
  public DigitalInput elevatorHighBSensor;
  private Servo brakeServo;

  double startingDistance;

  private final NetworkTableEntry elevatorHeightWidget;
  private final NetworkTableEntry bottomLimitSwitchWidget;
  private final NetworkTableEntry topLimitSwitchWidget;
  private final NetworkTableEntry motor1CurrenthWidget;
  private final NetworkTableEntry motor2CurrenthWidget;

  final Preferences config = Preferences.getInstance();
  private double minimumElevatorSpeed = 0.06;

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 0.25),
        Preferences.getInstance().getDouble("elevator.i", 0.0008), 
        Preferences.getInstance().getDouble("elevator.d", 0),
        Preferences.getInstance().getDouble("elevator.f", 0.15));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));
    motors = new SpeedControllerGroup(motor1, motor2);
    addChild("motors", motors);

    motor1.configFactoryDefault();
    motor1.enableCurrentLimit(true);
    motor1.configContinuousCurrentLimit(10);
    motor2.configFactoryDefault();
    motor2.enableCurrentLimit(true);
    motor2.configContinuousCurrentLimit(10);
    motor1.configPeakCurrentLimit(50);
    motor1.configPeakCurrentDuration(1000);
    motor2.configPeakCurrentLimit(50);
    motor2.configPeakCurrentDuration(1000);
    motor2.follow(motor1);

    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).withPosition(6, 2).withSize(4, 2).getEntry();
    bottomLimitSwitchWidget = Robot.programmerTab.add("Elevator bottom", false).withPosition(6, 7).getEntry();
    topLimitSwitchWidget = Robot.programmerTab.add("Elevator Top", false).withPosition(6, 4).getEntry();
    motor1CurrenthWidget = Robot.programmerTab.add("Motor " + motor1.getDeviceID() + " current", -1.0).withPosition(24, 0).withSize(4, 2).getEntry();
    motor2CurrenthWidget = Robot.programmerTab.add("Motor " + motor2.getDeviceID() + " current", -1.0).withPosition(24, 2).withSize(4, 2).getEntry();

    DigitalInput encoderInputA = new DigitalInput(config.getInt("elevator.encoder.dio.A", 3));
    DigitalInput encoderInputB = new DigitalInput(config.getInt("elevator.encoder.dio.B", 4));
    encoder = new Encoder(encoderInputA, encoderInputB, false, EncodingType.k4X);

    elevatorHighASensor = new DigitalInput(config.getInt("elevator.highA.dio", 0));
    elevatorHighBSensor = new DigitalInput(config.getInt("elevator.highB.dio", 1));

    brakeServo = new Servo(config.getInt("elevator.brakeServo.pwm", 0));

    resetEncoder();
    setSetpoint(6);
  }

  @Override
  public void periodic() {
    elevatorHeightWidget.setNumber(inchesTraveled());
    bottomLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isRevLimitSwitchClosed());
    topLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isFwdLimitSwitchClosed());
    motor1CurrenthWidget.setNumber(Robot.powerDistributionPanel.getCurrent(0));
    motor2CurrenthWidget.setNumber(Robot.powerDistributionPanel.getCurrent(1));
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
    output = Math.max(output, minimumElevatorSpeed);
    setSpeed(output);
  }

  public void setSpeed(double output) {
    motors.set(output);
  }

  public void resetEncoder() {
    startingDistance = encoder.getDistance();
  }

  public double getMinElevatorSpeed() {
    return minimumElevatorSpeed;
  }

  public void setMinElevatorSpeed(double speed) {
    minimumElevatorSpeed = speed;
  }

  public void setBrake(boolean engaged) { 
    brakeServo.set(engaged ? 1.0 : 0.0);
  }

  public void reset() {
    setBrake(true);
    disable();
  }
}
