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

public class Elevator extends PIDSubsystem {

  public static final double ENCODER_TICKS_PER_INCH = 51.09;

  private final WPI_TalonSRX motor1;
  private final WPI_TalonSRX motor2;
  private final SpeedControllerGroup motors;
  private final Encoder encoder;
  public DigitalInput elevatorHighASensor;
  public DigitalInput elevatorHighBSensor;
  public DigitalInput elevatorGroundSensor;
  private Servo brakeServo;
  private double currentSpeed;

  double startingDistance;

  private final NetworkTableEntry elevatorHeightWidget;
  private final NetworkTableEntry bottomLimitSwitchWidget;
  private final NetworkTableEntry topLimitSwitchWidget;
  private final NetworkTableEntry motor1CurrenthWidget;
  private final NetworkTableEntry motor2CurrenthWidget;
  private final NetworkTableEntry elevatorGroundWidget;

  final Preferences config = Preferences.getInstance();
  public double minimumElevatorSpeed = -0.5;
  double maximumElevatorSpeed = 1.0;

  private final long dashDelay;
  private long dashTimeout;

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 0.25),
        Preferences.getInstance().getDouble("elevator.i", 0.0008), Preferences.getInstance().getDouble("elevator.d", 0),
        Preferences.getInstance().getDouble("elevator.f", 0.15));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));  // PDP channel 0
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));  // PDP channel 1
    motors = new SpeedControllerGroup(motor1, motor2);
    addChild("motors", motors);

    motor1.configFactoryDefault();
    motor2.configFactoryDefault();
    setCurrentLimiting(10, 50, 1000);
    motor2.follow(motor1);

    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).withPosition(6, 2).withSize(4, 2).getEntry();
    bottomLimitSwitchWidget = Robot.programmerTab.add("Elevator bottom", false).withPosition(6, 7).getEntry();
    topLimitSwitchWidget = Robot.programmerTab.add("Elevator Top", false).withPosition(6, 4).getEntry();
    motor1CurrenthWidget = Robot.programmerTab.add("Motor " + motor1.getDeviceID() + " current", -1.0)
        .withPosition(24, 0).withSize(4, 2).getEntry();
    motor2CurrenthWidget = Robot.programmerTab.add("Motor " + motor2.getDeviceID() + " current", -1.0)
        .withPosition(24, 2).withSize(4, 2).getEntry();
    elevatorGroundWidget = Robot.programmerTab.add("groundSensorValue", false).getEntry();

    DigitalInput encoderInputA = new DigitalInput(config.getInt("elevator.encoder.dio.A", 3));
    DigitalInput encoderInputB = new DigitalInput(config.getInt("elevator.encoder.dio.B", 4));
    encoder = new Encoder(encoderInputA, encoderInputB, false, EncodingType.k4X);

    elevatorGroundSensor = new DigitalInput(config.getInt("elevator.ground.dio", 4));

    brakeServo = new Servo(config.getInt("elevator.brakeServo.pwm", 0));
    addChild("brakeServo", brakeServo);

    // resetEncoder();
    // setBrake(false)
    // setSetpoint(6);

    dashDelay = config.getLong("dashDelay", 250);
    dashTimeout = System.currentTimeMillis() + dashDelay;
  }

  public void setCurrentLimiting(int continuous, int high, int time) {
    System.out.println("setCurrentLimiting: " + continuous + " : " + high + " : " + time);
    motor1.enableCurrentLimit(true);
    motor1.configContinuousCurrentLimit(continuous);
    motor2.enableCurrentLimit(true);
    motor2.configContinuousCurrentLimit(continuous);
    motor1.configPeakCurrentLimit(high);
    motor1.configPeakCurrentDuration(time);
    motor2.configPeakCurrentLimit(high);
    motor2.configPeakCurrentDuration(time);
  }

  @Override
  public void periodic() {
    long currentTime = System.currentTimeMillis();
    if (currentTime > dashTimeout) {
      elevatorHeightWidget.setNumber(inchesTraveled());
      bottomLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isRevLimitSwitchClosed());
      topLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isFwdLimitSwitchClosed());
      motor1CurrenthWidget.setNumber(Robot.powerDistributionPanel.getCurrent(0));
      motor2CurrenthWidget.setNumber(Robot.powerDistributionPanel.getCurrent(1));
      elevatorGroundWidget.setBoolean(elevatorGroundSensor.get());
      dashTimeout = currentTime + dashDelay;
    }
    if (elevatorGroundSensor.get() == false) {
      resetEncoder();
    }
  }

  @Override
  public void initDefaultCommand() {
  }

  public double inchesTraveled() {
    double currentDistance = encoder.getDistance();
    return (startingDistance - currentDistance) / ENCODER_TICKS_PER_INCH;
  }

  @Override
  protected double returnPIDInput() {
    return inchesTraveled();
  }

  @Override
  protected void usePIDOutput(double output) {
    output = Math.max(output, minimumElevatorSpeed);
    output = Math.min(output, maximumElevatorSpeed);
    setSpeed(output);
    currentSpeed = output;
  }

  public double getSpeed() {
    return currentSpeed;
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
    brakeServo.set(engaged ? 0.770 : 0.700);
    System.out.println("DIE" + engaged);
  }

  public void reset() {
    // setBrake(true);
   // disable();
  }

  public boolean getGroundSensor() {
    return elevatorGroundSensor.get();
  }
}
