package org.firebears;

import org.firebears.commands.*;
import org.firebears.commands.Auto.Routines.RightRocketAutoCommand;
import org.firebears.recording.PlayRecordingCommand;
import org.firebears.recording.RecordingFactory;
import org.firebears.recording.StartRecordingCommand;
import org.firebears.recording.StopRecordingCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
        buttonX.whenPressed(new RightRocketAutoCommand());

        buttonX = new JoystickButton(xboxController, 3);   
        buttonX.whenPressed(new RightRocketAutoCommand());

        buttonStart = new JoystickButton(xboxController, 8);
        buttonStart.whenPressed(new LoadingStationCommand());

        // buttonA = new JoystickButton(controller1, 6);
        // buttonA.whenPressed(new PlayRecordingCommand(recordingFactory));

        // buttonStart = new JoystickButton(controller1, 8);
        // buttonStart.whenPressed(new StartRecordingCommand(recordingFactory));
        
        // buttonBack = new JoystickButton(controller1, 7);
        // buttonBack.whenPressed(new StopRecordingCommand(recordingFactory));
    }

    public XboxController getXboxController() {
        return xboxController;
    }

    public Joystick getJoystick(){
        return joystick;
    }
}
