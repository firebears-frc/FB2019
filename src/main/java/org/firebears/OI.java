package org.firebears;

import org.firebears.commands.*;
import org.firebears.commands.auto.*;
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
    private JoystickButton buttonB; // b 2
    private JoystickButton buttonA; // a 1
    private JoystickButton buttonY; // y 4 free button
    private JoystickButton buttonX; // x 3
    private JoystickButton rightBumper; // right bumper 6
    private JoystickButton leftBumper; // left bumper 5
   
   // private JoystickButton buttonElevator24; //start button
   // private JoystickButton buttonClimb; //back button
    private Joystick joystick;
    private JoystickButton hatch1Button;//7
    private JoystickButton hatch2Button;//6
    private JoystickButton hatch3Button;//5
    private JoystickButton cargo1Button;//2
    private JoystickButton cargo2Button;//3
    private JoystickButton cargo3Button;//4
    private JoystickButton groundCargoButton;//1
    private JoystickButton climbButton;//11
    private JoystickButton tiltyButton;//8
    public final RecordingFactory recordingFactory;


    public OI() {

        recordingFactory = new RecordingFactory();
        recordingFactory.add(Robot.chassis.pidFrontLeft, "leftMotors");
        recordingFactory.add(Robot.chassis.pidFrontRight, "rightMotors");

        xboxController = new XboxController(0);
        joystick = new Joystick(1);

        // xbox
        buttonX = new JoystickButton(xboxController, 3);
        buttonX.whenPressed(new PIDrelativeAngleCommand(45));

        buttonA = new JoystickButton(xboxController, 1);
        buttonA.whenPressed(new ElevatorNudgeCommand(-2));

        buttonY = new JoystickButton(xboxController, 4);
        buttonY.whenPressed(new ElevatorNudgeCommand(2));

        buttonB = new JoystickButton(xboxController, 2);
        buttonB.whenPressed(new PIDrelativeAngleCommand(180));

       // buttonElevator24 = new JoystickButton(xboxController, 8);
       // buttonElevator24.whenPressed(new ElevatorWithBrakeCommand(21));
    

       // buttonClimb = new JoystickButton(xboxController, 7);
       // buttonClimb.whenPressed(new FroggerClimbCommand());


        // Removed because they use an axis in CargoGrabber.periodic
       
        leftBumper = new JoystickButton(xboxController, 5);
        leftBumper.whenPressed(new HatchHoldCommand());

        rightBumper = new JoystickButton(xboxController, 6);
        rightBumper.whenPressed(new HatchReleaseCommand());
        // joystick/button box

        groundCargoButton = new JoystickButton(joystick, 1);
        groundCargoButton.whenPressed(new ElevatorGroundCommand());
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
        hatch1Button = new JoystickButton(joystick, 7);
        hatch1Button.whenPressed(new ElevatorCommand(2));
        hatch2Button = new JoystickButton(joystick, 6);
        hatch2Button.whenPressed(new ElevatorCommand(29));
        hatch3Button = new JoystickButton(joystick, 5);
        hatch3Button.whenPressed(new ElevatorCommand(57));
        cargo1Button = new JoystickButton(joystick, 2);
        cargo1Button.whenPressed(new ElevatorCommand(23));
        cargo2Button = new JoystickButton(joystick, 3);
        cargo2Button.whenPressed(new ElevatorCommand(50));
       // cargo3Button = new JoystickButton(joystick, 4);
        //cargo3Button.whenPressed(new ElevatorCommand(73));

      //  tiltyButton = new JoystickButton(joystick, 8);
        //tiltyButton.whenPressed(new TiltyToggleConditionalCommand());

        tiltyButton = new JoystickButton(joystick, 8);
        tiltyButton.whenPressed(new StartingConfigurationLeaveCommand());

        climbButton = new JoystickButton(joystick, 11);
        climbButton.whenPressed(new FroggerClimbCommand());


        SmartDashboard.putData(new ResetElevatorEncoderCommand());
        SmartDashboard.putData(new DriveToVisionTargetCommand());
        SmartDashboard.putData(new FroggerLowerCommand());
        SmartDashboard.putData(new FroggerRaiseCommand());
        SmartDashboard.putData(new FroggerDriveCommand());
        SmartDashboard.putData("FroggerClimbCommand", new FroggerClimbCommand());
        SmartDashboard.putData(new FroggerElevatorClimbCommand());

        SmartDashboard.putData(new DriveToVisionTargetDistanceCommand());

        SmartDashboard.putData(new TiltyRetractCommand());
        SmartDashboard.putData(new TiltyExtendCommand());
        SmartDashboard.putData(new StartingConfigurationEnterCommand());
        SmartDashboard.putData(new StartingConfigurationLeaveCommand());
        
        SmartDashboard.putData(new DriveToWallCommand(20));

        SmartDashboard.putData("Brake 24", new ElevatorCommand(24));
        SmartDashboard.putData("Brake 36", new ElevatorCommand(36));

        SmartDashboard.putData("DriveToVisionDistance", new DriveToVisionTargetDistanceCommand());
        SmartDashboard.putData("RotateToVision", new RotateToVisionTargetCommand());
        SmartDashboard.putData("FroggerClimbSync", new FroggerClimbSyncCommand());
        //SmartDashboard.putData("Elevator24Inches", new ElevatorCommand(24));
 
        // SmartDashboard.putData("Brake on", new ElevatorCommand(true));
        // SmartDashboard.putData("Brake off", new ElevatorCommand(false));
    }

    public XboxController getXboxController() {
        return xboxController;
    }

    public Joystick getJoystick() {
        return joystick;
    }
}
