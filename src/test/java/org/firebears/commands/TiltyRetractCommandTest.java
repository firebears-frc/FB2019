package org.firebears.commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.firebears.subsystems.Tilty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;

/** Testing a simple {@code SendableCommandBase} */
public class TiltyRetractCommandTest {

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
        Tilty tilty = mock(Tilty.class);
        TiltyRetractCommand command = new TiltyRetractCommand(tilty);

        // Act
        scheduler.schedule(command);
        while(scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(tilty, atLeastOnce()).retract();
        verify(tilty).freeze();
    }
}
