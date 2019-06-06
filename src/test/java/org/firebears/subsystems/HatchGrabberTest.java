package org.firebears.subsystems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import org.junit.*;

public class HatchGrabberTest {

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
        WPI_TalonSRX hatchMotor = mock(WPI_TalonSRX.class);
        DigitalInput hatchRotationSensor = mock(DigitalInput.class);
        DigitalInput hatchCapturedSensor = mock(DigitalInput.class);

        when(hatchRotationSensor.get()).thenReturn(true);
        when(hatchCapturedSensor.get()).thenReturn(false);

        // Act
        HatchGrabber hatchGrabber = new HatchGrabber(hatchMotor, hatchRotationSensor, hatchCapturedSensor, programmerTab);

        // Assert
        assertEquals(true, hatchGrabber.getRotationSensorValue());
        assertEquals(false, hatchGrabber.getCapturedSensorValue());
    }

    @Test
    public void testRotate() {
        // Arrange
        WPI_TalonSRX hatchMotor = mock(WPI_TalonSRX.class);
        DigitalInput hatchRotationSensor = mock(DigitalInput.class);
        DigitalInput hatchCapturedSensor = mock(DigitalInput.class);

        HatchGrabber hatchGrabber = new HatchGrabber(hatchMotor, hatchRotationSensor, hatchCapturedSensor, programmerTab);

        // Act
        hatchGrabber.rotate();

        // Assert
        verify(hatchMotor).set(hatchGrabber.MOTOR_SPEED);
    }

    @Test
    public void testStopRotate() {
        // Arrange
        WPI_TalonSRX hatchMotor = mock(WPI_TalonSRX.class);
        DigitalInput hatchRotationSensor = mock(DigitalInput.class);
        DigitalInput hatchCapturedSensor = mock(DigitalInput.class);

        HatchGrabber hatchGrabber = new HatchGrabber(hatchMotor, hatchRotationSensor, hatchCapturedSensor, programmerTab);

        // Act
        hatchGrabber.stopRotate();

        // Assert
        verify(hatchMotor).set(0.0);
    }
}
