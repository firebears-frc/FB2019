package org.firebears.commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.firebears.subsystems.Elevator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;

/** Testing an {@code InstantCommand}. */
public class ResetElevatorEncoderCommandTest {

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
