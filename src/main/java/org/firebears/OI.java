package org.firebears;

import org.firebears.commands.DriveToWallCommand;
import org.firebears.commands.ElevatorCommand;
import org.firebears.commands.ElevatorGroundCommand;
import org.firebears.commands.ElevatorNudgeCommand;
import org.firebears.commands.FroggerClimbCommand;
import org.firebears.commands.FroggerClimbSyncCommand;
import org.firebears.commands.FroggerDriveCommand;
import org.firebears.commands.FroggerElevatorClimbCommand;
import org.firebears.commands.FroggerLowerCommand;
import org.firebears.commands.FroggerRaiseCommand;
import org.firebears.commands.HatchHoldCommand;
import org.firebears.commands.HatchReleaseCommand;
import org.firebears.commands.I2cWriteCommand;
import org.firebears.commands.ResetElevatorEncoderCommand;
import org.firebears.commands.StartingConfigurationEnterCommand;
import org.firebears.commands.StartingConfigurationLeaveCommand;
import org.firebears.commands.TiltyExtendCommand;
import org.firebears.commands.TiltyRetractCommand;
import org.firebears.commands.auto.DriveToVisionTargetCommand;
import org.firebears.commands.auto.DriveToVisionTargetDistanceCommand;
import org.firebears.commands.auto.PIDrelativeAngleCommand;
import org.firebears.commands.auto.RotateToVisionTargetCommand;
import org.firebears.recording.*;
import org.firebears.subsystems.Lights;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.experimental.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    public XboxController xboxController;
    private JoystickButton buttonB; // b 2
    private JoystickButton buttonA; // a 1
    private JoystickButton buttonY; // y 4 free button
    private JoystickButton buttonX; // x 3 
    private JoystickButton rightBumper; // right bumper 6
    private JoystickButton leftBumper; // left bumper 5
    private JoystickButton buttonStart; //
    private JoystickButton buttonBack;
   
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
        buttonA.whenPressed(new ElevatorNudgeCommand(-2, Robot.elevator));

        buttonY = new JoystickButton(xboxController, 4);
        buttonY.whenPressed(new ElevatorNudgeCommand(2, Robot.elevator));

        buttonB = new JoystickButton(xboxController, 2);
        buttonB.whenPressed(new StopRecordingCommand(recordingFactory));

        buttonBack = new JoystickButton(xboxController, 7);
        buttonBack.whenPressed(new StartRecordingCommand(recordingFactory));

        buttonStart = new JoystickButton(xboxController, 8);
        buttonStart.whenPressed(new PlayRecordingCommand(recordingFactory));

       // buttonElevator24 = new JoystickButton(xboxController, 8);
       // buttonElevator24.whenPressed(new ElevatorWithBrakeCommand(21));
    

       // buttonClimb = new JoystickButton(xboxController, 7);
       // buttonClimb.whenPressed(new FroggerClimbCommand());


        // Removed because they use an axis in CargoGrabber.periodic
       
        leftBumper = new JoystickButton(xboxController, 5);
        leftBumper.whenPressed(new HatchHoldCommand(Robot.hatchGrabber));

        rightBumper = new JoystickButton(xboxController, 6);
        rightBumper.whenPressed(new HatchReleaseCommand(Robot.hatchGrabber));
        // joystick/button box

        groundCargoButton = new JoystickButton(joystick, 1);
        groundCargoButton.whenPressed(new ElevatorGroundCommand(Robot.elevator));
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
        hatch1Button.whenPressed(new ElevatorCommand(2, Robot.elevator));
        hatch2Button = new JoystickButton(joystick, 6);
        hatch2Button.whenPressed(new ElevatorCommand(29, Robot.elevator));
        hatch3Button = new JoystickButton(joystick, 5);
        hatch3Button.whenPressed(new ElevatorCommand(57, Robot.elevator));
        cargo1Button = new JoystickButton(joystick, 2);
        cargo1Button.whenPressed(new ElevatorCommand(23, Robot.elevator));
        cargo2Button = new JoystickButton(joystick, 3);
        cargo2Button.whenPressed(new ElevatorCommand(50, Robot.elevator));
       // cargo3Button = new JoystickButton(joystick, 4);
        //cargo3Button.whenPressed(new ElevatorCommand(73));

      //  tiltyButton = new JoystickButton(joystick, 8);
        //tiltyButton.whenPressed(new TiltyToggleConditionalCommand());

        tiltyButton = new JoystickButton(joystick, 8);
        tiltyButton.whenPressed(new StartingConfigurationLeaveCommand(Robot.tilty, Robot.elevator));

        climbButton = new JoystickButton(joystick, 11);
        climbButton.whenPressed(new FroggerClimbCommand(Robot.frogger, Robot.elevator, Robot.chassis));


        SmartDashboard.putData(new ResetElevatorEncoderCommand(Robot.elevator));
        SmartDashboard.putData(new DriveToVisionTargetCommand(Robot.chassis, Robot.vision));
        SmartDashboard.putData(new FroggerLowerCommand(Robot.frogger));
        SmartDashboard.putData(new FroggerRaiseCommand(Robot.frogger));
        SmartDashboard.putData(new FroggerDriveCommand(Robot.frogger, Robot.chassis));
        SmartDashboard.putData("FroggerClimbCommand", new FroggerClimbCommand(Robot.frogger, Robot.elevator, Robot.chassis));
        SmartDashboard.putData(new FroggerElevatorClimbCommand(Robot.frogger, Robot.elevator));

        SmartDashboard.putData(new DriveToVisionTargetDistanceCommand(Robot.chassis, Robot.vision));

        SmartDashboard.putData(new TiltyRetractCommand(Robot.tilty));
        SmartDashboard.putData(new TiltyExtendCommand(Robot.tilty));
        SmartDashboard.putData(new StartingConfigurationEnterCommand(Robot.tilty, Robot.elevator, Robot.hatchGrabber));
        SmartDashboard.putData(new StartingConfigurationLeaveCommand(Robot.tilty, Robot.elevator));
        
        SmartDashboard.putData(new DriveToWallCommand(20, Robot.chassis));

        SmartDashboard.putData("Brake 24", new ElevatorCommand(24, Robot.elevator));
        SmartDashboard.putData("Brake 36", new ElevatorCommand(36, Robot.elevator));

        SmartDashboard.putData("DriveToVisionDistance", new DriveToVisionTargetDistanceCommand(Robot.chassis, Robot.vision));
        SmartDashboard.putData("RotateToVision", new RotateToVisionTargetCommand(Robot.chassis, Robot.vision));
        SmartDashboard.putData("FroggerClimbSync", new FroggerClimbSyncCommand());
        //SmartDashboard.putData("Elevator24Inches", new ElevatorCommand(24));
 
        // SmartDashboard.putData("Brake on", new ElevatorCommand(true));
        // SmartDashboard.putData("Brake off", new ElevatorCommand(false));

        SmartDashboard.putData("Red lights", new I2cWriteCommand(Robot.lights.i2c, new byte[] {Lights.ELEVATOR_STRIP, Lights.RED_ANIMATION}));
        SmartDashboard.putData("Blue lights", new I2cWriteCommand(Robot.lights.i2c, new byte[] {Lights.ELEVATOR_STRIP, Lights.BLUE_ANIMATION}));
    }

    public XboxController getXboxController() {
        return xboxController;
    }

    public Joystick getJoystick() {
        return joystick;
    }
}
