// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.firebears.subsystems;

import org.firebears.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.firebears.util.Config;
import edu.wpi.first.wpilibj.Preferences;

/**
 *
 */
public class Chassis extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private CANSparkMax rearRight;
    private CANSparkMax frontRight;
    private SpeedControllerGroup rightMotors;
    private CANSparkMax frontLeft;
    private CANSparkMax rearLeft;
    private SpeedControllerGroup leftMotors;
    private DifferentialDrive robotDrive;
    public DigitalInput rightSensor;
    public DigitalInput centerSensor;
    public DigitalInput leftSensor;
    private CANEncoder rearRightEncoder;
    private CANEncoder frontRightEncoder;
    private CANEncoder rearLeftEncoder;
    private CANEncoder frontLeftEncoder;
    public static final double ENCODER_TICKS_PER_INCH = 0.4449;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public Chassis() {
        final Preferences config = Preferences.getInstance();

        int chassisRearRightCanID = config.getInt("chassis.rearright.canID", 2);
        rearRight = new CANSparkMax(chassisRearRightCanID, MotorType.kBrushless);
        rearRight.setInverted(false);
        rearRightEncoder = rearRight.getEncoder();

        int chassisFrontRightCanID = config.getInt("chassis.frontright.canID", 3);
        frontRight = new CANSparkMax(chassisFrontRightCanID, MotorType.kBrushless);
        frontRight.setInverted(false);
        frontRightEncoder = frontRight.getEncoder();

        rightMotors = new SpeedControllerGroup(rearRight, frontRight);
        addChild("RightMotors", rightMotors);

        int chassisFrontLeftCanID = config.getInt("chassis.frontleft.canID", 4);
        frontLeft = new CANSparkMax(chassisFrontLeftCanID, MotorType.kBrushless);
        frontLeft.setInverted(false);
        frontLeftEncoder = frontLeft.getEncoder();

        int chassisRearLeftCanID = config.getInt("chassis.rearleft.canID", 5);
        rearLeft = new CANSparkMax(chassisRearLeftCanID, MotorType.kBrushless);
        rearLeft.setInverted(false);
        rearLeftEncoder = rearLeft.getEncoder();

        leftMotors = new SpeedControllerGroup(frontLeft, rearLeft);
        addChild("LeftMotors", leftMotors);

        robotDrive = new DifferentialDrive(leftMotors, rightMotors);
        addChild("RobotDrive", robotDrive);
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setMaxOutput(1.0);

        int chassisRightSensorDio = config.getInt("chassis.rightSensor.dio", 0);
        rightSensor = new DigitalInput(chassisRightSensorDio);

        int chassisCenterSensorDio = config.getInt("chassis.centerSensor.dio", 1);
        centerSensor = new DigitalInput(chassisCenterSensorDio);

        int chassisLeftSensorDio = config.getInt("chassis.leftSensor.dio", 2);
        leftSensor = new DigitalInput(chassisLeftSensorDio);

    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    public void setBrakingMode(boolean braking) {
        rearRight.setIdleMode(braking ? IdleMode.kBrake : IdleMode.kCoast);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveCommand());
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("rightSensor", rightSensor.get());
        SmartDashboard.putBoolean("centerSensor", centerSensor.get());
        SmartDashboard.putBoolean("leftSensor", leftSensor.get());
        SmartDashboard.putNumber("distanceInInches", inchesTraveled());
    }

    public double inchesTraveledLeft() {
        return frontLeftEncoder.getPosition() / ENCODER_TICKS_PER_INCH;
    }

    public double inchesTraveledRight() {
        return -1 * frontRightEncoder.getPosition() / ENCODER_TICKS_PER_INCH;
    }

    public double inchesTraveled() {
        return (inchesTraveledLeft() + inchesTraveledRight()) / 2;
    }
}
