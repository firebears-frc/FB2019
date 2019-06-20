package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.RobotState;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import org.firebears.commands.auto.routines.CenterAutoCommand;
import org.firebears.subsystems.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CenterAutoCommandTest {

    CommandScheduler scheduler = null;

    @Before
    public void setup() {
        RobotState robotState = mock(RobotState.class);
        when(robotState.isDisabled()).thenReturn(false);
        scheduler = new CommandScheduler(robotState) { };
    }

    @After
    public void cleanup() {
        scheduler.cancelAll();
    }

    @Test
    public void testWhenTargetIsVisible() {
        // Arrange
        Chassis chassis = mock(Chassis.class);
        when(chassis.inchesTraveled()).thenReturn(100.0)
                .thenReturn(130.0).thenReturn(130.0)
                .thenReturn(160.0).thenReturn(160.0)
                .thenReturn(170.0).thenReturn(170.0)
                .thenReturn(171.0);
        Tilty tilty = mock(Tilty.class);
        Elevator elevator = mock(Elevator.class);
        HatchGrabber hatchGrabber = mock(HatchGrabber.class);
        when(hatchGrabber.getRotationSensorValue()).thenReturn(false).thenReturn(false).thenReturn(true);
        Vision vision = mock(Vision.class);
        when(vision.getVisionTargetConfidence()).thenReturn(1.0);
        when(vision.getVisionTargetAngleX()).thenReturn(30.0);
        when(chassis.getAngle()).thenReturn(0.0).thenReturn(0.0).thenReturn(0.0)
                .thenReturn(10.0).thenReturn(10.0)
                .thenReturn(20.0).thenReturn(20.0)
                .thenReturn(30.0);

        CenterAutoCommand command = new CenterAutoCommand(chassis, tilty, elevator, hatchGrabber, vision);

        // Act
        scheduler.schedule(command);
        while(scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(chassis).resetNavX();
    }
}
