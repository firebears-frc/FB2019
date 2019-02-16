package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Elevator extends PIDSubsystem {

  WPI_TalonSRX motor1;
  WPI_TalonSRX motor2;

  double startingDistance;

  private NetworkTableEntry elevatorHeightWidget;
  private NetworkTableEntry forwardLimitSwitchWidget, reverseLimitSwitchWidget;

  final Preferences config = Preferences.getInstance();
  private final boolean DEBUG = config.getBoolean("debug", false);

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 1),
        Preferences.getInstance().getDouble("elevator.i", 0), Preferences.getInstance().getDouble("elevator.d", 0));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));

    resetEncoder();

    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).getEntry();
    forwardLimitSwitchWidget = Robot.programmerTab.add("Forward Limit", false).getEntry();
    reverseLimitSwitchWidget = Robot.programmerTab.add("Reverse Limit", false).getEntry();
  }

  @Override
  public void periodic() {
    if (motor1.getSensorCollection().isRevLimitSwitchClosed()) {
      this.resetEncoder();
      if (DEBUG) {
        System.out.println("Elevator: reset encoder to zero");
      }
    }
    elevatorHeightWidget.setNumber(inchesTraveled());
    forwardLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isFwdLimitSwitchClosed());
    reverseLimitSwitchWidget.setBoolean(motor1.getSensorCollection().isRevLimitSwitchClosed());

  }

  @Override
  public void initDefaultCommand() {

  }

  public double inchesTraveled() {
    double currentDistance = motor1.getSelectedSensorPosition();
    return Math.abs(currentDistance - startingDistance);
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
    startingDistance = motor1.getSelectedSensorPosition(0);
  }
}
