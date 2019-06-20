/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.command.InstantCommand;
import org.firebears.Robot;

/**
 * turns on need hatchpanel lights.
 */
public class NeedHatchPanelCommand extends InstantCommand {

    public NeedHatchPanelCommand() {
        addRequirements(Robot.lights);
    }

    @Override
    public void initialize() {
        Robot.lights.setHatchPanelMode(true);
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }
}
