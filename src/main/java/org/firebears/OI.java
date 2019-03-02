package org.firebears;

import org.firebears.commands.FroggerClimbCommand;
import org.firebears.commands.CargoIntakeCommand;
import org.firebears.commands.CargoSpitCommand;
import org.firebears.commands.ElevatorCommand;
import org.firebears.commands.FroggerLowerCommand;
import org.firebears.commands.FroggerRaiseCommand;
import org.firebears.commands.FroggerDriveCommand;
import org.firebears.commands.HatchHoldCommand;
import org.firebears.commands.HatchReleaseCommand;
import org.firebears.commands.LineFollowCommand;
import org.firebears.commands.PIDSparkCommand;
import org.firebears.commands.RelativeAngleCommand;
import org.firebears.commands.ResetElevatorEncoderCommand;
import org.firebears.commands.ResetNavXCommand;
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

    private XboxController xboxController;
    private JoystickButton buttonTurn180; // b 2
    private JoystickButton buttonTurn90; // a 1
    private JoystickButton buttonY; // y 4 free button
    private JoystickButton buttonTurn45; // x 3
    private JoystickButton buttonReleaseHatch; // right bumper 6
    private JoystickButton buttonGrabHatch; // left bumper 5
    private JoystickButton buttonSuckCargo; // right trigger
    private JoystickButton buttonSpitCargo; // left trigger
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

        // buttonY = new JoystickButton(xboxController, 4);
        // buttonY.whenPressed(new Command());

        // buttonSuckCargo = new JoystickButton(xboxController, buttonNumber);
        // buttonSuckCargo.whileHeld(new CargoIntakeCommand());

        // buttonSpitCargo = new JoystickButton(xboxController, buttonNumber);
        // buttonSpitCargo.whileHeld(new CargoSpitCommand());

        buttonGrabHatch = new JoystickButton(xboxController, 5);
        buttonGrabHatch.whenPressed(new HatchHoldCommand());

        buttonReleaseHatch = new JoystickButton(xboxController, 6);
        buttonReleaseHatch.whenPressed(new HatchReleaseCommand());
        // joystick/button box
        hatch1Button = new JoystickButton(joystick, 11);
        hatch1Button.whenPressed(new SelectHatchCommand());

        hatch2Button = new JoystickButton(joystick, 9);
        hatch2Button.whenPressed(new ElevatorHatchPlaceCommand(35));

        hatch3Button = new JoystickButton(joystick, 7);
        hatch3Button.whenPressed(new ElevatorHatchPlaceCommand(61));

        groundCargoButton = new JoystickButton(joystick, 1);
        groundCargoButton.whenPressed(new ElevatorCommand(2));

        cargo1Button = new JoystickButton(joystick, 12);
        cargo1Button.whenPressed(new ElevatorCargoCommand(26));

        cargo2Button = new JoystickButton(joystick, 10);
        cargo2Button.whenPressed(new ElevatorCargoCommand(57));

        cargo3Button = new JoystickButton(joystick, 8);
        cargo3Button.whenPressed(new ElevatorCargoCommand(78));

        climbButton = new JoystickButton(joystick, 2);
        climbButton.whenPressed(new FroggerLowerCommand());


        SmartDashboard.putData(new ResetElevatorEncoderCommand());
        SmartDashboard.putData(new DriveToVisionTargetCommand());
        SmartDashboard.putData(new FroggerLowerCommand());
        SmartDashboard.putData(new FroggerRaiseCommand());
        SmartDashboard.putData(new FroggerDriveCommand());
        SmartDashboard.putData(new FroggerClimbCommand());
    }

    public XboxController getXboxController() {
        return xboxController;
    }

    public Joystick getJoystick() {
        return joystick;
    }
}
