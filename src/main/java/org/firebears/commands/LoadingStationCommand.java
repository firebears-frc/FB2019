/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.subsystems.Chassis;
import org.firebears.subsystems.Vision;

public class LoadingStationCommand extends Command {

  private double rotation;
  private double x;

  long timeout;

  public LoadingStationCommand() {
    requires(Robot.chassis);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeout = System.currentTimeMillis() + 6000;
    x = Robot.vision.getVisionTargetAngleX();
    rotation = 0;
    Robot.chassis.drive(0, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    x = Robot.vision.getVisionTargetAngleX();

    if (x == 0) {
      rotation = 0;
    } else if (x > 0) {
      if (x < 15) {
        rotation = 0.1;
      } else if (x < 30) {
        rotation = 0.2;
      } else {
        rotation = 0.3;
      }
    } else if (x < 0) {
      if (x > -15) {
        rotation = -0.1;
      } else if (x > -30) {
        rotation = -0.2;
      } else {
        rotation = -0.3;
      }
    }
    // if the target is visable
      if (Robot.vision.getVisionTargetConfidence() > 0.9){
    Robot.chassis.drive(0.5, rotation);
      }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (System.currentTimeMillis() > timeout){
      return true;
    }
    if (Robot.vision.getVisionTargetDistance() < 5){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0, 0);
  }
}
