package org.firebears.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import org.firebears.subsystems.Elevator;

public class DisableBrakeCommand extends SendableCommandBase {

  private Timer timer = new Timer();
  private final Elevator elevator;

  public DisableBrakeCommand(final Elevator elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    elevator.setBrake(false);
    elevator.disable();
    timer.reset();
    timer.start();
    System.out.println("INITIALIZE: " + this);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    elevator.setSpeed(0.42);
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
    elevator.setSpeed(0.0);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
