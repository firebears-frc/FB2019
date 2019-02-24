package org.firebears;

import org.firebears.commands.CargoIntakeCommand;
import org.firebears.commands.CargoSpitCommand;
import org.firebears.commands.ElevatorCommand;
import org.firebears.commands.FroggerLowerCommand;
import org.firebears.commands.HatchHoldCommand;
import org.firebears.commands.HatchReleaseCommand;
import org.firebears.commands.LineFollowCommand;
import org.firebears.commands.PIDSparkCommand;
import org.firebears.commands.ResetElevatorEncoderCommand;
import org.firebears.commands.ResetNavXCommand;
import org.firebears.commands.auto.DistanceCommand;
import org.firebears.commands.auto.DriveToVisionTargetCommand;
import org.firebears.commands.auto.PIDrelativeAngleCommand;
import org.firebears.commands.auto.RotateToAngleCommand;
import org.firebears.commands.auto.routines.CenterAutoCommand;
import org.firebears.recording.RecordingFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    private XboxController xboxController;
    private JoystickButton buttonB;
    private JoystickButton buttonA;
    private JoystickButton buttonY;
    private JoystickButton buttonX;
    private JoystickButton buttonRB;
    private JoystickButton buttonLB;
    private JoystickButton buttonStart;
    private JoystickButton buttonBack;
    private JoystickButton hatch1Button;
    private JoystickButton hatch2Button;
    private JoystickButton hatch3Button;
    private JoystickButton cargo1Button;
    private JoystickButton cargo2Button;
    private JoystickButton cargo3Button;
    private JoystickButton groundCargoButton;
    private JoystickButton cargoIntakeButton;
    private JoystickButton cargoSpitButton;
    private JoystickButton hatchHoldButton;
    private JoystickButton hatchReleaseButton;
    public final RecordingFactory recordingFactory;
    private Joystick joystick;

    public OI() {

        recordingFactory = new RecordingFactory();
        recordingFactory.add(Robot.chassis.pidFrontLeft, "leftMotors");
        recordingFactory.add(Robot.chassis.pidFrontRight, "rightMotors");

        xboxController = new XboxController(0);
        joystick = new Joystick(1);

        buttonA = new JoystickButton(xboxController, 1);
        // buttonA.whenPressed(new PIDrelativeAngleCommand(90));
        buttonA.whenPressed(new PIDSparkCommand(24, 24));

        buttonB = new JoystickButton(xboxController, 2);
        // buttonB.whenPressed(new LineFollowCommand());
        buttonB.whenPressed(new PIDSparkCommand(36, 36));

        buttonX = new JoystickButton(xboxController, 3);
        // buttonX.whenPressed(new CenterAutoCommand());
        buttonX.whenPressed(new PIDSparkCommand(-45));

        buttonY = new JoystickButton(xboxController, 4);
        // buttonY.whenPressed(new ResetNavXCommand());
        buttonY.whenPressed(new PIDSparkCommand(90));

        buttonRB = new JoystickButton(xboxController, 6);
        buttonRB.whenPressed(new DistanceCommand(36));

        buttonLB = new JoystickButton(xboxController, 5);
        buttonLB.whenPressed(new RotateToAngleCommand(90));

        buttonStart = new JoystickButton(xboxController, 8);
        buttonStart.whenPressed(new FroggerLowerCommand());

        hatch1Button = new JoystickButton(joystick, 11);
        hatch1Button.whenPressed(new ElevatorCommand(4));

        hatch2Button = new JoystickButton(joystick, 9);
        hatch2Button.whenPressed(new ElevatorCommand(35));

        hatch3Button = new JoystickButton(joystick, 7);
        hatch3Button.whenPressed(new ElevatorCommand(61));

        groundCargoButton = new JoystickButton(joystick, 1);
        groundCargoButton.whenPressed(new ElevatorCommand(2));

        cargo1Button = new JoystickButton(joystick, 12);
        cargo1Button.whenPressed(new ElevatorCommand(26));

        cargo2Button = new JoystickButton(joystick, 10);
        cargo2Button.whenPressed(new ElevatorCommand(53));

        cargo3Button = new JoystickButton(joystick, 8);
        cargo3Button.whenPressed(new ElevatorCommand(78));

        cargoIntakeButton = new JoystickButton(joystick, 6);
        cargoIntakeButton.whileHeld(new CargoIntakeCommand());

        cargoSpitButton = new JoystickButton(joystick, 4);
        cargoSpitButton.whileHeld(new CargoSpitCommand());

        hatchHoldButton = new JoystickButton(joystick, 5);
        hatchHoldButton.whenPressed(new HatchHoldCommand());

        hatchReleaseButton = new JoystickButton(joystick, 3);
        hatchReleaseButton.whenPressed(new HatchReleaseCommand());

        // buttonA = new JoystickButton(controller1, 6);
        // buttonA.whenPressed(new PlayRecordingCommand(recordingFactory));

        // buttonStart = new JoystickButton(controller1, 8);
        // buttonStart.whenPressed(new StartRecordingCommand(recordingFactory));

        // buttonBack = new JoystickButton(controller1, 7);
        // buttonBack.whenPressed(new StopRecordingCommand(recordingFactory));

        SmartDashboard.putData(new ResetElevatorEncoderCommand());
    }

    public XboxController getXboxController() {
        return xboxController;
    }

    public Joystick getJoystick() {
        return joystick;
    }
}
