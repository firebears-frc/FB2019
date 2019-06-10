package org.firebears.commands;

import edu.wpi.first.wpilibj.experimental.RobotState;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import org.firebears.subsystems.Tilty;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/** Testing a simple {@code SendableCommandBase} */
public class TiltyRetractCommandTest {

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
