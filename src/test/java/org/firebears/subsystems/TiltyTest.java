package org.firebears.subsystems;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TiltyTest {

    ShuffleboardTab programmerTab = null;
    SimpleWidget widget = null;
    NetworkTableEntry networkTableEntry = null;

    @Before
    public void setup() {
        networkTableEntry = mock(NetworkTableEntry.class);
        widget = mock(SimpleWidget.class);
        when(widget.withPosition(any(Integer.class),any(Integer.class))).thenReturn(widget);
        when(widget.getEntry()).thenReturn(networkTableEntry);
        programmerTab = mock(ShuffleboardTab.class);
        when(programmerTab.add(any(String.class), any(Boolean.class))).thenReturn(widget);
    }

    @Test
    public void testConstructor() {
        // Arrange
        SensorCollection sensorCollection = mock(SensorCollection.class);
        when(sensorCollection.isRevLimitSwitchClosed()).thenReturn(false);
        when(sensorCollection.isFwdLimitSwitchClosed()).thenReturn(true);
        WPI_TalonSRX tiltyMotor = mock(WPI_TalonSRX.class);
        when(tiltyMotor.getSensorCollection()).thenReturn(sensorCollection);

        // Act
        Tilty tilty = new Tilty(tiltyMotor, programmerTab);

        // Assert
        assertEquals(true, tilty.isRetracted());
        assertEquals(false, tilty.isExtended());
    }

    @Test
    public void testExtend() {
        // Arrange
        SensorCollection sensorCollection = mock(SensorCollection.class);
        when(sensorCollection.isRevLimitSwitchClosed()).thenReturn(false);
        when(sensorCollection.isFwdLimitSwitchClosed()).thenReturn(true);
        WPI_TalonSRX tiltyMotor = mock(WPI_TalonSRX.class);
        when(tiltyMotor.getSensorCollection()).thenReturn(sensorCollection);

        Tilty tilty = new Tilty(tiltyMotor, programmerTab);

        // Act
        tilty.extend();

        // Assert
        verify(tiltyMotor).set(tilty.MOTOR_SPEED * -1);
    }

    @Test
    public void testRetract() {
        // Arrange
        SensorCollection sensorCollection = mock(SensorCollection.class);
        when(sensorCollection.isRevLimitSwitchClosed()).thenReturn(false);
        when(sensorCollection.isFwdLimitSwitchClosed()).thenReturn(true);
        WPI_TalonSRX tiltyMotor = mock(WPI_TalonSRX.class);
        when(tiltyMotor.getSensorCollection()).thenReturn(sensorCollection);

        Tilty tilty = new Tilty(tiltyMotor, programmerTab);

        // Act
        tilty.retract();

        // Assert
        verify(tiltyMotor).set(0.3);
    }

    @Test
    public void testFreeze() {
        // Arrange
        SensorCollection sensorCollection = mock(SensorCollection.class);
        when(sensorCollection.isRevLimitSwitchClosed()).thenReturn(false);
        when(sensorCollection.isFwdLimitSwitchClosed()).thenReturn(true);
        WPI_TalonSRX tiltyMotor = mock(WPI_TalonSRX.class);
        when(tiltyMotor.getSensorCollection()).thenReturn(sensorCollection);

        Tilty tilty = new Tilty(tiltyMotor, programmerTab);

        // Act
        tilty.freeze();

        // Assert
        verify(tiltyMotor).set(0.0);
    }
}
