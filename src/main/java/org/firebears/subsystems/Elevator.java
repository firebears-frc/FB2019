package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {

  public static final double ENCODER_TICKS_PER_INCH = 1.0;

  WPI_TalonSRX motor1;
  WPI_TalonSRX motor2;
  SpeedControllerGroup motors;

  double startingDistance;

  private NetworkTableEntry elevatorHeightWidget;
  private NetworkTableEntry bottomLimitSwitchWidget, topLimitSwitchWidget;

  final Preferences config = Preferences.getInstance();
  private final boolean DEBUG = config.getBoolean("debug", false);

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 1),
        Preferences.getInstance().getDouble("elevator.i", 0), Preferences.getInstance().getDouble("elevator.d", 0),
        Preferences.getInstance().getDouble("elevator.f", 0));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));
    motors = new SpeedControllerGroup(motor1, motor2);
    addChild("motors", motors);

    resetEncoder();

    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).getEntry();
    bottomLimitSwitchWidget = Robot.programmerTab.add("bottom Limit", false).getEntry();
    topLimitSwitchWidget = Robot.programmerTab.add("Top Limit", false).getEntry();
  }

  @Override
  public void periodic() {
    elevatorHeightWidget.setNumber(inchesTraveled());
    bottomLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isRevLimitSwitchClosed());
    topLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isFwdLimitSwitchClosed());
    SmartDashboard.putNumber("elevator encoder", motor1.getSelectedSensorPosition()); // TODO - delete later
  }

  @Override
  public void initDefaultCommand() {
  }

  public double inchesTraveled() {
    double currentDistance = motor1.getSelectedSensorPosition();
    return Math.abs(currentDistance - startingDistance) / ENCODER_TICKS_PER_INCH;
  }

  @Override
  protected double returnPIDInput() {
    return inchesTraveled();
  }

  @Override
  protected void usePIDOutput(double output) {
    motor1.set(output);
    motor2.set(output);
  }

  public void resetEncoder() {
    startingDistance = motor1.getSelectedSensorPosition();
  }
}
