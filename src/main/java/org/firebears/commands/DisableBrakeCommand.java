package org.firebears.commands;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class DisableBrakeCommand extends SendableCommandBase {

  private Timer timer = new Timer();

  public DisableBrakeCommand() {
    addRequirements(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Robot.elevator.setBrake(false);
    Robot.elevator.disable();
    timer.reset();
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    Robot.elevator.setSpeed(0.42);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    if (timer.get() > 0.1){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    Robot.elevator.setSpeed(0.0);
  }


}
