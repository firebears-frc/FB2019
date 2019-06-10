package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.RobotState;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import org.firebears.commands.auto.DriveToVisionTargetCommand;
import org.firebears.subsystems.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/** Testing an {@code SequentialCommand}. */
public class DriveToVisionTargetCommandTest {

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
    public void testWhenTargetIsVisible() {
        // Arrange
        Vision vision = mock(Vision.class);
        when(vision.getVisionTargetConfidence()).thenReturn(1.0);
        when(vision.getVisionTargetAngleX()).thenReturn(30.0);
        Chassis chassis = mock(Chassis.class);
        when(chassis.getAngle()).thenReturn(0.0).thenReturn(0.0).thenReturn(0.0)
                .thenReturn(10.0).thenReturn(10.0)
                .thenReturn(20.0).thenReturn(20.0)
                .thenReturn(30.0);

        DriveToVisionTargetCommand command = new DriveToVisionTargetCommand(chassis, vision);

        // Act
        scheduler.schedule(command);
        while(scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(chassis).drive(0.0, 0.5);
        verify(chassis, times(2)).drive(0.0, 0.25);
        verify(chassis, times(2)).drive(0.0, 0.0);
    }

    @Test
    public void testWhenTargetIsNotVisible() {
        // Arrange
        Vision vision = mock(Vision.class);
        when(vision.getVisionTargetConfidence()).thenReturn(0.0);
        Chassis chassis = mock(Chassis.class);
        when(chassis.getLidarDistanceInches()).thenReturn(30.0).thenReturn(30.0).thenReturn(25.0).thenReturn(19.0);

        DriveToVisionTargetCommand command = new DriveToVisionTargetCommand(chassis, vision);

        // Act
        scheduler.schedule(command);
        while(scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(chassis, times(2)).drive(0.4, 0.0);
        verify(chassis).drive(0.0, 0.0);
    }
}
