package org.firebears;

import org.firebears.commands.*;
import org.firebears.commands.Auto.Routines.RightRocketAutoCommand;
import org.firebears.recording.PlayRecordingCommand;
import org.firebears.recording.RecordingFactory;
import org.firebears.recording.StartRecordingCommand;
import org.firebears.recording.StopRecordingCommand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public XboxController controller1;
    private JoystickButton buttonB;
    private JoystickButton buttonA;
    private JoystickButton buttonY;
    private JoystickButton buttonX;
    private JoystickButton buttonRB;
    private JoystickButton buttonLB;
    private JoystickButton buttonStart;
    private JoystickButton buttonBack;
    public final RecordingFactory recordingFactory;

    public OI() {

        recordingFactory = new RecordingFactory();
        recordingFactory.add(Robot.chassis.pidFrontLeft, "leftMotors");
        recordingFactory.add(Robot.chassis.pidFrontRight, "rightMotors");
        
        controller1 = new XboxController(0);

        buttonB = new JoystickButton(controller1, 2);  
        buttonB.whenPressed(new LineFollowCommand());

        buttonA = new JoystickButton(controller1, 1);  
        buttonA.whenPressed(new PIDrelativeAngleCommand(90));

        buttonY = new JoystickButton(controller1, 4);   
        buttonY.whenPressed(new ResetNavXCommand());

        buttonRB = new JoystickButton(controller1, 6);
        buttonRB.whenPressed(new PlayRecordingCommand(recordingFactory));

        buttonLB = new JoystickButton(controller1, 5);
        buttonLB.whenPressed(new RotateToAngleCommand(90));
        
        buttonX = new JoystickButton(controller1, 3);   
        buttonX.whenPressed(new RightRocketAutoCommand());

        buttonStart = new JoystickButton(controller1, 8);
        buttonStart.whenPressed(new StartRecordingCommand(recordingFactory));
        
        buttonBack = new JoystickButton(controller1, 7);
        buttonBack.whenPressed(new StopRecordingCommand(recordingFactory));
    }

    public XboxController getController1() {
        return controller1;
    }
}
