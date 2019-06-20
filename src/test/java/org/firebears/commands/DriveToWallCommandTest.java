package org.firebears.commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.firebears.subsystems.Chassis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;

/**
 * Testing a {@code SendableCommandBase}
 */
public class DriveToWallCommandTest {

    CommandScheduler scheduler = null;

    @Before
    public void setup() {
        scheduler = CommandScheduler.getInstance();
    }

    @After
    public void cleanup() {
        scheduler.cancelAll();
    }

    @Test
    public void testCommand() {
        // Arrange
        Chassis chassis = mock(Chassis.class);
        when(chassis.getLidarDistanceInches()).thenReturn(12.0).thenReturn(8.0).thenReturn(5.0);
        DriveToWallCommand command = new DriveToWallCommand(6.0, chassis);

        // Act
        scheduler.schedule(command);
        while (scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(chassis, atLeastOnce()).drive(0.4, 0.0);
        verify(chassis).drive(0.0, 0.0);
    }
}
