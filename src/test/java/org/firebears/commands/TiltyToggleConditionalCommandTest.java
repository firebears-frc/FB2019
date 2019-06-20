package org.firebears.commands;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.firebears.subsystems.Tilty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

/** Testing a {@code ConditionalCommand} */
public class TiltyToggleConditionalCommandTest {
    ShuffleboardTab programmerTab = null;
    SimpleWidget widget = null;
    NetworkTableEntry networkTableEntry = null;
    CommandScheduler scheduler = null;

    @Before
    public void setup() {
        networkTableEntry = mock(NetworkTableEntry.class);
        widget = mock(SimpleWidget.class);
        when(widget.withPosition(any(Integer.class),any(Integer.class))).thenReturn(widget);
        when(widget.getEntry()).thenReturn(networkTableEntry);
        programmerTab = mock(ShuffleboardTab.class);
        when(programmerTab.add(any(String.class), any(Boolean.class))).thenReturn(widget);

        scheduler = CommandScheduler.getInstance();
    }

    @After
    public void cleanup() {
        scheduler.cancelAll();
    }

    /**
     * The Tilty is retracted, so extend it.
     */
    @Test
    public void testExtend() {
        // Arrange
        SensorCollection sensorCollection = mock(SensorCollection.class);
        when(sensorCollection.isFwdLimitSwitchClosed()).thenReturn(true);
        WPI_TalonSRX tiltyMotor = mock(WPI_TalonSRX.class);
        when(tiltyMotor.getSensorCollection()).thenReturn(sensorCollection);
        Tilty tilty = new Tilty(tiltyMotor, programmerTab);
        TiltyToggleConditionalCommand command = new TiltyToggleConditionalCommand(tilty);

        // Act
        scheduler.schedule(command);
        while(scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(tiltyMotor, atLeastOnce()).set(-1 * tilty.MOTOR_SPEED);
        verify(tiltyMotor).set(0.0);
    }

    /**
     * The Tilty is extended, so retract it.
     */
    @Test
    public void testRetract() {
        // Arrange
        SensorCollection sensorCollection = mock(SensorCollection.class);
        when(sensorCollection.isFwdLimitSwitchClosed()).thenReturn(false);
        WPI_TalonSRX tiltyMotor = mock(WPI_TalonSRX.class);
        when(tiltyMotor.getSensorCollection()).thenReturn(sensorCollection);
        Tilty tilty = new Tilty(tiltyMotor, programmerTab);
        TiltyToggleConditionalCommand command = new TiltyToggleConditionalCommand(tilty);

        // Act
        scheduler.schedule(command);
        while(scheduler.getScheduleSize() > 0) {
            scheduler.run();
        }

        // Assert
        assertEquals(0, scheduler.getScheduleSize());
        verify(tiltyMotor, atLeastOnce()).set(0.3);
        verify(tiltyMotor).set(0.0);
    }
}
