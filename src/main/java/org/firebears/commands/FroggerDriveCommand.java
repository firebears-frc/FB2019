package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class FroggerDriveCommand extends SendableCommandBase {

  private final double CHASSIS_SPEED = 0.5;
  private Timer timer = new Timer();
  
  public FroggerDriveCommand() {
    addRequirements(Robot.frogger);
    addRequirements(Robot.chassis);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    // Robot.frogger.enable();
  }

  @Override
  public void execute() {
    Robot.frogger.footDown();
    Robot.frogger.driveForward();
    Robot.chassis.drive(CHASSIS_SPEED, -0.3);
  }

  @Override
  public boolean isFinished() {
    if (timer.hasPeriodPassed(6.0)) {
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.frogger.stopDrive();
    Robot.frogger.footStop();
  }

}
