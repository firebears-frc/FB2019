package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.RobotState;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import org.firebears.subsystems.Elevator;
import org.firebears.subsystems.Tilty;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/** Testing an {@code InstantCommand}. */
public class ResetElevatorEncoderCommandTest {

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
        Elevator elevator = mock(Elevator.class);
        ResetElevatorEncoderCommand command = new ResetElevatorEncoderCommand(elevator);

        // Act
        scheduler.schedule(command);
        scheduler.run();

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(elevator).resetEncoder();
    }
}
