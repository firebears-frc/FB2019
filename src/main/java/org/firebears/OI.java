package org.firebears;

import org.firebears.commands.auto.*;
import org.firebears.commands.*;
import org.firebears.commands.auto.routines.*;
import org.firebears.recording.PlayRecordingCommand;
import org.firebears.recording.RecordingFactory;
import org.firebears.recording.StartRecordingCommand;
import org.firebears.recording.StopRecordingCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
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
    public final RecordingFactory recordingFactory;
    private Joystick joystick;

    public OI() {

        recordingFactory = new RecordingFactory();
        recordingFactory.add(Robot.chassis.pidFrontLeft, "leftMotors");
        recordingFactory.add(Robot.chassis.pidFrontRight, "rightMotors");
        
        xboxController = new XboxController(0);
        joystick = new Joystick(1);

        buttonB = new JoystickButton(xboxController, 2);  
        buttonB.whenPressed(new LineFollowCommand());

        buttonA = new JoystickButton(xboxController, 1);  
        buttonA.whenPressed(new PIDrelativeAngleCommand(90));

        buttonY = new JoystickButton(xboxController, 4);   
        buttonY.whenPressed(new ResetNavXCommand());

        buttonRB = new JoystickButton(xboxController, 6);
        buttonRB.whenPressed(new DistanceCommand(36));

        buttonLB = new JoystickButton(xboxController, 5);
        buttonLB.whenPressed(new RotateToAngleCommand(90));

        buttonX = new JoystickButton(xboxController, 3);   
        buttonX.whenPressed(new CenterAutoCommand());

        buttonStart = new JoystickButton(xboxController, 8);
        buttonStart.whenPressed(new DriveToVisionTargetCommand());

       // hatch1Button = new JoystickButton(joystick, 11);
        //hatch1Button.whenPressed(new ElevatorCommand(18));

        //hatch2Button = new JoystickButton(joystick, 9);
        //hatch2Button.whenPressed(new ElevatorCommand(46));

        //hatch3Button = new JoystickButton(joystick, 7);
        //hatch3Button.whenPressed(new ElevatorCommand(74));
        groundCargoButton = new JoystickButton(joystick, 1);
        groundCargoButton.whenPressed(new ElevatorCommand(2));

        cargo1Button = new JoystickButton(joystick, 12);
        cargo1Button.whenPressed(new ElevatorCommand(36));

        //cargo2Button = new JoystickButton(joystick, 10);
        //cargo2Button.whenPressed(new ElevatorCommand(54));

        //cargo3Button = new JoystickButton(joystick, 8);
        //cargo3Button.whenPressed(new ElevatorCommand(60));

        cargoIntakeButton = new JoystickButton(joystick, 6);
        cargoIntakeButton.whileHeld(new CargoIntakeCommand());

        cargoSpitButton = new JoystickButton(joystick, 4);
        cargoSpitButton.whileHeld(new CargoSpitCommand());

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

    public Joystick getJoystick(){
        return joystick;
    }
}
