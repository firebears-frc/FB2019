package org.firebears;

import org.firebears.commands.*;
import org.firebears.commands.auto.DistanceCommand;
import org.firebears.commands.auto.DriveToVisionTargetCommand;
import org.firebears.commands.auto.PIDrelativeAngleCommand;
import org.firebears.commands.auto.RotateToAngleCommand;
import org.firebears.commands.auto.routines.CenterAutoCommand;
import org.firebears.commands.auto.teleopAuto.SelectHatchCommand;
import org.firebears.recording.RecordingFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.ADXL345_I2C.Axes;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.firebears.commands.auto.teleopAuto.*;

public class OI {

    public XboxController xboxController;
    private JoystickButton buttonTurn180; // b 2
    private JoystickButton buttonTurn90; // a 1
    private JoystickButton buttonY; // y 4 free button
    private JoystickButton buttonTurn45; // x 3
    private JoystickButton buttonReleaseHatch; // right bumper 6
    private JoystickButton buttonGrabHatch; // left bumper 5
    private JoystickButton buttonSuckCargo; // right trigger
    private JoystickButton buttonSpitCargo; // left trigger
    private JoystickButton buttonElevator24; //start button
    private JoystickButton buttonClimb; //back button
    private Joystick joystick;
    private JoystickButton hatch1Button;
    private JoystickButton hatch2Button;
    private JoystickButton hatch3Button;
    private JoystickButton cargo1Button;
    private JoystickButton cargo2Button;
    private JoystickButton cargo3Button;
    private JoystickButton groundCargoButton;
    private JoystickButton climbButton;
    public final RecordingFactory recordingFactory;

    public OI() {

        recordingFactory = new RecordingFactory();
        recordingFactory.add(Robot.chassis.pidFrontLeft, "leftMotors");
        recordingFactory.add(Robot.chassis.pidFrontRight, "rightMotors");

        xboxController = new XboxController(0);
        joystick = new Joystick(1);

        // xbox
        buttonTurn45 = new JoystickButton(xboxController, 3);
        buttonTurn45.whenPressed(new PIDrelativeAngleCommand(45));

        buttonTurn90 = new JoystickButton(xboxController, 1);
        buttonTurn90.whenPressed(new PIDrelativeAngleCommand(90));

        buttonTurn180 = new JoystickButton(xboxController, 2);
        buttonTurn180.whenPressed(new PIDrelativeAngleCommand(180));

        buttonElevator24 = new JoystickButton(xboxController, 8);
        buttonElevator24.whenPressed(new ElevatorWithBrakeCommand(21));

        buttonClimb = new JoystickButton(xboxController, 7);
        buttonClimb.whenPressed(new FroggerClimbCommand());


        // Removed because they use an axis in CargoGrabber.periodic
        /*
        buttonSuckCargo = new JoystickButton(xboxController, buttonNumber);
        buttonSuckCargo.whileHeld(new CargoIntakeCommand());

        buttonSpitCargo = new JoystickButton(xboxController, buttonNumber);
        buttonSpitCargo.whileHeld(new CargoSpitCommand());
        */

        buttonGrabHatch = new JoystickButton(xboxController, 5);
        buttonGrabHatch.whenPressed(new HatchHoldCommand());

        buttonReleaseHatch = new JoystickButton(xboxController, 6);
        buttonReleaseHatch.whenPressed(new HatchReleaseCommand());
        // joystick/button box

        groundCargoButton = new JoystickButton(joystick, 1);
        groundCargoButton.whenPressed(new ElevatorWithBrakeCommand(0));
        /*
        // Auto teleop
        hatch1Button = new JoystickButton(joystick, 11);
        hatch1Button.whenPressed(new VisionConditionalCommand(new SelectHatchCommand()));
        hatch2Button = new JoystickButton(joystick, 9);
        hatch2Button.whenPressed(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(22.4)));
        hatch3Button = new JoystickButton(joystick, 7);
        hatch3Button.whenPressed(new VisionConditionalCommand(new ElevatorHatchPlaceCommand(48.7)));
        cargo1Button = new JoystickButton(joystick, 12);
        cargo1Button.whenPressed(new VisionConditionalCommand(new ElevatorCargoCommand(24.8)));
        cargo2Button = new JoystickButton(joystick, 10);
        cargo2Button.whenPressed(new VisionConditionalCommand(new ElevatorCargoCommand(47.5)));
        cargo3Button = new JoystickButton(joystick, 8);
        cargo3Button.whenPressed(new VisionConditionalCommand(new ElevatorCargoCommand(67.4)));
         */

        // Manual teleop
        hatch1Button = new JoystickButton(joystick, 11);
        hatch1Button.whenPressed(new ElevatorWithBrakeCommand(4.59));
        hatch2Button = new JoystickButton(joystick, 9);
        hatch2Button.whenPressed(new ElevatorWithBrakeCommand(28.4));
        hatch3Button = new JoystickButton(joystick, 7);
        hatch3Button.whenPressed(new ElevatorWithBrakeCommand(58.2));
        cargo1Button = new JoystickButton(joystick, 12);
        cargo1Button.whenPressed(new ElevatorWithBrakeCommand(23.0));
        cargo2Button = new JoystickButton(joystick, 10);
        cargo2Button.whenPressed(new ElevatorWithBrakeCommand(53.5));
        cargo3Button = new JoystickButton(joystick, 8);
        cargo3Button.whenPressed(new ElevatorWithBrakeCommand(73.4));


        SmartDashboard.putData(new ResetElevatorEncoderCommand());
        SmartDashboard.putData(new DriveToVisionTargetCommand());
        SmartDashboard.putData(new FroggerLowerCommand());
        SmartDashboard.putData(new FroggerRaiseCommand());
        SmartDashboard.putData(new FroggerDriveCommand());
        SmartDashboard.putData(new FroggerClimbCommand());
        SmartDashboard.putData(new FroggerElevatorClimbCommand());

        SmartDashboard.putData(new TiltyRetractCommand());
        SmartDashboard.putData(new TiltyExtendCommand());
        SmartDashboard.putData(new StartingConfigurationCommand());
        SmartDashboard.putData(new EndStartingConfigCommand());
        
        SmartDashboard.putData(new DriveToWallCommand(20));

         SmartDashboard.putData("Brake 24", new ElevatorWithBrakeCommand(24));
         SmartDashboard.putData("Brake 36", new ElevatorWithBrakeCommand(36));

        // Robot.driverTab.add


        // SmartDashboard.putData("Brake on", new ElevatorSetBrakeCommand(true));
        // SmartDashboard.putData("Brake off", new ElevatorSetBrakeCommand(false));
    }

    public XboxController getXboxController() {
        return xboxController;
    }

    public Joystick getJoystick() {
        return joystick;
    }
}
