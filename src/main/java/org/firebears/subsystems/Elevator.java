/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.subsystems;
import org.firebears.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;

public class Elevator extends PIDSubsystem {

  private WPI_TalonSRX motor1;
  private WPI_TalonSRX motor2;

  private double startingDistance;

  private NetworkTableEntry elevatorHeightWidget;

  private final Preferences config = Preferences.getInstance();

  public Elevator() {
    super("Elevator", Preferences.getInstance().getDouble("elevator.p", 1),
        Preferences.getInstance().getDouble("elevator.i", 0), Preferences.getInstance().getDouble("elevator.d", 0));

    motor1 = new WPI_TalonSRX(config.getInt("elevator.motor1.canID", 16));
    motor2 = new WPI_TalonSRX(config.getInt("elevator.motor2.canID", 15));
    addChild("motor1", motor1);
    addChild("motor2", motor2);

    resetEncoder();

    elevatorHeightWidget = Robot.programmerTab.add("Elevator Height", 0).getEntry();
  }

  
  @Override
  public void periodic() {
    elevatorHeightWidget.setNumber(inchesTraveled());
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
