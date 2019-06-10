package org.firebears;

import static org.firebears.util.Config.cleanAllPreferences;
import static org.firebears.util.Config.loadConfiguration;
import static org.firebears.util.Config.printPreferences;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import org.firebears.commands.*;
import org.firebears.commands.auto.routines.CenterAutoCommand;
import org.firebears.commands.auto.routines.LeftRocketAutoCommand;
import org.firebears.commands.auto.routines.RightRocketAutoCommand;
import org.firebears.subsystems.CargoGrabber;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.Frogger;
import org.firebears.subsystems.HatchGrabber;
import org.firebears.subsystems.Lights;
import org.firebears.subsystems.Tilty;
import org.firebears.subsystems.Vision;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.experimental.command.Command;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;

public class Robot extends TimedRobot {

    public static OI oi;
    public static Chassis chassis;
    public static Elevator elevator;
    public static HatchGrabber hatchGrabber;
    public static CargoGrabber cargoGrabber;
    public static Tilty tilty;
    public static Frogger frogger;
    public static Lights lights;
    public static Vision vision;
    public static PowerDistributionPanel powerDistributionPanel;
    public static ShuffleboardTab programmerTab;
    public static ShuffleboardTab driverTab;


    private Command autonomousCommand = null;

    private static final String RIGHT_ROCKET_AUTO = "rightRocketAuto (default)";
    private static final String LEFT_ROCKET_AUTO = "leftRocketAuto";
    private static final String CENTER_AUTO = "centerAuto";
    private static final String NO_AUTO = "noAuto";
    private final SendableChooser<String> chooser = new SendableChooser<>();

    private NetworkTableEntry cargoAquiredWidget;
    private NetworkTableEntry hatchAquiredWidget;
    private NetworkTableEntry visionAquiredWidget;
    private NetworkTableEntry elevatorHeightWidget;
    private NetworkTableEntry hatchRotationWidget;

    @Override
    public void robotInit() {

        cleanAllPreferences();
        loadConfiguration("/home/lvuser/deploy/config.properties", "/home/lvuser/config.properties",
                "/u/config.properties");
        printPreferences(System.out);
        Preferences config = Preferences.getInstance();

        programmerTab = Shuffleboard.getTab("Programmers");
        driverTab = Shuffleboard.getTab("Drivers");
        // HttpCamera drivingCamera = new HttpCamera("Driving Camera", new String[]{"http://10.28.46.18:1181", "http://frcvision.local:1181/"});
        // driverTab.add(drivingCamera).withPosition(0, 0).withSize(10, 10);

        chassis = new Chassis();
        chassis.setDefaultCommand(new DriveCommand(chassis));
        elevator = new Elevator();
        hatchGrabber = new HatchGrabber(
                new WPI_TalonSRX(config.getInt("hatchGrabber.motor.canID", 14)),
                new DigitalInput(config.getInt("hatchGrabber.hatchCaptured.dio", 5)),
                new DigitalInput(config.getInt("hatchGrabber.hatchRotationSensor.dio", 9)),
                programmerTab
        );

        cargoGrabber = new CargoGrabber();

        WPI_TalonSRX tiltyMotor = new WPI_TalonSRX(config.getInt("tilty.motor.canID", 12));
        tiltyMotor.configFactoryDefault();
        tiltyMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        tiltyMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        tilty = new Tilty(tiltyMotor, programmerTab);

        frogger = new Frogger();
        lights = new Lights();
        vision = new Vision();

        oi = new OI();
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);

        chooser.addOption("centerAuto", CENTER_AUTO);
        chooser.addOption("rightRocketAuto", RIGHT_ROCKET_AUTO);
        chooser.addOption("leftRocketAuto", LEFT_ROCKET_AUTO);
        chooser.setDefaultOption("noAuto", NO_AUTO);
        SmartDashboard.putData("Auto mode", chooser);

        powerDistributionPanel = new PowerDistributionPanel();
        powerDistributionPanel.clearStickyFaults();

        lights.reset();
        elevator.reset();

        cargoAquiredWidget = Robot.driverTab.add("Cargo Aquired", Robot.cargoGrabber.isCargoCaptured()).withPosition(0, 8).withSize(3, 3).getEntry();
        hatchAquiredWidget = Robot.driverTab.add("Hatch Aquired", Robot.hatchGrabber.getCapturedSensorValue()).withPosition(0, 5).withSize(3, 3).getEntry();
        hatchRotationWidget = Robot.driverTab.add("Hatch Rotation", hatchGrabber.getRotationSensorValue()).withPosition(3, 8).withSize(3, 3).getEntry();
        visionAquiredWidget = Robot.driverTab.add("Vision Aquired", Robot.vision.getVisionTargetConfidenceBoolean()).withPosition(0, 2).withSize(3, 3).getEntry();
        Robot.driverTab.add("Raise Frogger", new FroggerRaiseCommand(frogger)).withPosition(8, 2).withSize(5, 2);
        Robot.driverTab.add("Test Wheel", new FroggerTestWheelCommand()).withPosition(8, 4).withSize(5, 2);
        Robot.driverTab.add("Enter Starting Config", new StartingConfigurationEnterCommand(Robot.tilty, Robot.elevator, Robot.hatchGrabber)).withPosition(3, 4).withSize(5, 2);
        Robot.driverTab.add("Leave Starting Config", new StartingConfigurationLeaveCommand(Robot.tilty, Robot.elevator)).withPosition(3, 6).withSize(5, 2);
        Robot.driverTab.add("Disable Elevator Brake", new ElevatorSetBrakeCommand(false, elevator)).withPosition(3, 2).withSize(5, 2);
        Robot.driverTab.add("Enable Elevator Brake", new ElevatorSetBrakeCommand(true, elevator)).withPosition(3, 0).withSize(5, 2);
        Robot.driverTab.add("Auto mode", chooser).withPosition(8, 0).withSize(5, 2);
        elevatorHeightWidget = Robot.driverTab.add("Elevator Height", "0").withPosition(0, 0).withSize(3, 2).getEntry();
    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void disabledInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        lights.reset();
        elevator.reset();
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
        updateDriverTab();
    }

    @Override
    public void autonomousInit() {
        if (RIGHT_ROCKET_AUTO.equals(chooser.getSelected())) {
            autonomousCommand = new RightRocketAutoCommand(chassis, tilty, elevator, hatchGrabber, vision);
        } else if (LEFT_ROCKET_AUTO.equals(chooser.getSelected())) {
            autonomousCommand = new LeftRocketAutoCommand(chassis, tilty, elevator, hatchGrabber, vision);
        } else if (CENTER_AUTO.equals(chooser.getSelected())) {
            autonomousCommand = new CenterAutoCommand(chassis, tilty, elevator, hatchGrabber, vision);
        } else if (NO_AUTO.equals(chooser.getSelected())){
            autonomousCommand = null;
        }
        if (autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(autonomousCommand);
//            autonomousCommand.start();
        }
        lights.reset();
        elevator.enable();
        frogger.enable();
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
        updateDriverTab();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        lights.reset();
        elevator.enable();
        frogger.disable();
        // frogger.setSetpoint(0.25);
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
        updateDriverTab();
    }

    @Override
    public void testPeriodic() {
        CommandScheduler.getInstance().run();
        elevator.periodic();
        chassis.periodic();
        tilty.periodic();
        frogger.periodic();
        updateDriverTab();
    }

    private void updateDriverTab(){
        cargoAquiredWidget.setBoolean(Robot.cargoGrabber.isCargoCaptured());
        hatchAquiredWidget.setBoolean(Robot.hatchGrabber.getCapturedSensorValue());
        visionAquiredWidget.setBoolean(Robot.vision.getVisionTargetConfidenceBoolean());
        hatchRotationWidget.setBoolean(Robot.hatchGrabber.getRotationSensorValue());
        DecimalFormat df = new DecimalFormat("###.###");
        elevatorHeightWidget.setString(df.format(elevator.inchesTraveled()));
    }
}
