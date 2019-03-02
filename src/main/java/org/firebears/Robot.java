package org.firebears;

import static org.firebears.util.Config.cleanAllPreferences;
import static org.firebears.util.Config.loadConfiguration;
import static org.firebears.util.Config.printPreferences;

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

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    private Command autonomousCommand = null;

    private static final String RIGHT_ROCKET_AUTO = "rightRocketAuto (default)";
    private static final String LEFT_ROCKET_AUTO = "leftRocketAuto";
    private static final String CENTER_AUTO = "centerAuto";
    private final SendableChooser<String> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {

        cleanAllPreferences();
        loadConfiguration("/home/lvuser/deploy/config.properties", "/home/lvuser/config.properties",
                "/u/config.properties");
        printPreferences(System.out);

        programmerTab = Shuffleboard.getTab("Programmers");

        chassis = new Chassis();
        elevator = new Elevator();
        hatchGrabber = new HatchGrabber();
        cargoGrabber = new CargoGrabber();
        tilty = new Tilty();
        frogger = new Frogger();
        lights = new Lights();
        vision = new Vision();

        oi = new OI();

        chooser.setDefaultOption("rightRocketAuto (default)", RIGHT_ROCKET_AUTO);
        chooser.addOption("leftRocketAuto", LEFT_ROCKET_AUTO);
        chooser.addOption("centerAuto", CENTER_AUTO);
        SmartDashboard.putData("Auto mode", chooser);

        powerDistributionPanel = new PowerDistributionPanel();
        powerDistributionPanel.clearStickyFaults();

        lights.reset();
    }

    @Override
    public void disabledInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        lights.reset();
        elevator.disable();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        if (RIGHT_ROCKET_AUTO.equals(chooser.getSelected())) {
            autonomousCommand = new RightRocketAutoCommand();
        } else if (LEFT_ROCKET_AUTO.equals(chooser.getSelected())) {
            autonomousCommand = new LeftRocketAutoCommand();
        } else if (CENTER_AUTO.equals(chooser.getSelected())) {
            autonomousCommand = new CenterAutoCommand();
        }
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
        lights.reset();
        elevator.enable();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        lights.reset();
        elevator.enable();
        elevator.setSetpoint(6);
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    @Override
    public void testPeriodic() {
        elevator.periodic();
        Scheduler.getInstance().run();
    }
}
