package org.firebears.commands;

import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Frogger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class FroggerDriveCommand extends SendableCommandBase {

  private final double CHASSIS_SPEED = 0.5;
  private Timer timer = new Timer();
  private final Frogger frogger;
  protected final Chassis chassis;
  
  public FroggerDriveCommand(final Frogger frogger, final Chassis chassis) {
    this.frogger = frogger;
    this.chassis = chassis;
    addRequirements(frogger);
    addRequirements(chassis);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    // Robot.frogger.enable();
  }

  @Override
  public void execute() {
    frogger.footDown();
    frogger.driveForward();
    chassis.drive(CHASSIS_SPEED, -0.3);
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
    frogger.stopDrive();
    frogger.footStop();
  }

}
