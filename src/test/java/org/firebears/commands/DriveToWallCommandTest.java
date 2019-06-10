package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.RobotState;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import org.firebears.subsystems.Chassis;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Testing a {@code SendableCommandBase}
 */
public class DriveToWallCommandTest {

    CommandScheduler scheduler = null;

    @Before
    public void setup() {
        RobotState robotState = mock(RobotState.class);
        when(robotState.isDisabled()).thenReturn(false);
        scheduler = new CommandScheduler(robotState) {
        };
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
