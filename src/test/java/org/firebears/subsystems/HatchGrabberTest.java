package org.firebears.subsystems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.junit.*;

@Ignore
public class HatchGrabberTest {

    ShuffleboardTab programmerTab = Shuffleboard.getTab("Programmers");

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
