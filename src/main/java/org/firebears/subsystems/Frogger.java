package org.firebears.subsystems;

import org.firebears.Robot;
import org.firebears.commands.FroggerLowerCommand;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Frogger extends Subsystem {
    final Preferences config = Preferences.getInstance();
    WPI_TalonSRX jumpMotor;
    WPI_TalonSRX forwardMotor;
    private final double FROGGER_SPEED = 0.75;
    private final double DRIVE_SPEED = 1.0;
    private final double TICKS_PER_INCH = 113.0;
    private final NetworkTableEntry froggerBottomWidget;
    //private final NetworkTableEntry froggerTopWidget;
    private final Encoder encoder;
    double startingDistance;

    public Frogger() {
        jumpMotor = new WPI_TalonSRX(config.getInt("frogger.jumpMotor.canID", 11));
        forwardMotor = new WPI_TalonSRX(config.getInt("frogger.forwardMotor.canID", 17));

        jumpMotor.configFactoryDefault();
        forwardMotor.configFactoryDefault();

        addChild("jumpMotor", jumpMotor);
        addChild("forwardMotor", forwardMotor);

        jumpMotor.setNeutralMode(NeutralMode.Brake);

        DigitalInput encoderInputA = new DigitalInput(config.getInt("frogger.encoder.dio.A", 0));
        DigitalInput encoderInputB = new DigitalInput(config.getInt("frogger.encoder.dio.B", 1));
        encoder = new Encoder(encoderInputA, encoderInputB, false, EncodingType.k4X);

        froggerBottomWidget = Robot.programmerTab.add("froggerBottom", false).withPosition(13, 7).getEntry();
        //froggerTopWidget = Robot.programmerTab.add("froggerTop", false).withPosition(10, 7).getEntry();
        resetEncoder();
    } 


    @Override
    public void initDefaultCommand() {
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Frogger Distance", encoderDistance());

        froggerBottomWidget.setBoolean(jumpMotor.getSensorCollection().isFwdLimitSwitchClosed());
    
     //   if (jumpMotor.get() == 0.0){
       //     IdleMode idleMode = braking ? IdleMode.kBrake : IdleMode.kCoast;
       // }
    }

    public void footDown() {
        jumpMotor.set(FROGGER_SPEED);
    }

    public void footup() {
        jumpMotor.set(-FROGGER_SPEED);
    }

    public void footStop() {
        jumpMotor.set(0.0);
    }

    public void resetEncoder() {
        startingDistance = encoder.getDistance();
    }

    public double encoderDistance(){  
        double currentDistance = encoder.getDistance();
        return Math.abs(currentDistance - startingDistance) / TICKS_PER_INCH;
    }

    public void driveForward() {
        forwardMotor.set(DRIVE_SPEED);
    }

    public void stopDrive() {
        forwardMotor.set(0.0);
    }

    public double getJumpMotor() {
        return jumpMotor.get();
    }
    public boolean isDownwardsLimitHit(){
     return jumpMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }
    public boolean isUpwardsLimitHit(){
        return jumpMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
}
