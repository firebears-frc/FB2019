package org.firebears.util;

import edu.wpi.first.wpilibj.SpeedController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

/**
 * Wrapper around {@code CANSparkMax} allowing us to do closed-loop driving.
 */
public class PIDSparkMotor implements SpeedController {

	public static final double MAX_RPM = 5700.0;
	public static final double ENCODER_TICKS_PER_INCH = 0.4449;

	private final CANSparkMax motor;
	private final CANPIDController pidController;
	private final CANEncoder encoder;
	private boolean closedLoop = false;
	private double currentSpeed = 0.0;

	public PIDSparkMotor(CANSparkMax m, double kP, double kI, double kD) {
		motor = m;
		pidController = motor.getPIDController();
		pidController.setP(kP);
		pidController.setI(kI);
		pidController.setD(kD);
		pidController.setOutputRange(-1.0, 1.0);
		encoder = motor.getEncoder();
	}

	public void setClosedLoop(boolean b) {
		closedLoop = b;
	}

	public boolean isClosedLoop() {
		return closedLoop;
	}

	public double inchesTraveled() {
		return encoder.getPosition() / ENCODER_TICKS_PER_INCH;
	}

	public void driveToPosition(double inches)  {
		double setPointPosition = inches * ENCODER_TICKS_PER_INCH;
		if (pidController.setReference(setPointPosition, ControlType.kPosition) != CANError.kOK) {
			System.out.println("ERROR: Failed to set Setpoint on " + this);
		}
	}

	/**
	 * Common interface for setting the speed of a speed controller.
	 *
	 * @param speed The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {
		currentSpeed = speed;
		if (closedLoop) {
			double setPointVelocity = speed * MAX_RPM;
			if (pidController.setReference(setPointVelocity, ControlType.kVelocity) != CANError.kOK) {
				System.out.println("ERROR: Failed to set setpoint on " + this);
			}
		} else {
			motor.set(speed);
		}
	}

	/**
	 * Common interface for getting the current set speed of a speed controller.
	 *
	 * @return The current set speed. Value is between -1.0 and 1.0.
	 */
	@Override
	public double get() {
		if (closedLoop) {
			return currentSpeed;
		} else {
			return motor.get();
		}
	}

	/**
	 * Common interface for inverting direction of a speed controller.
	 *
	 * @param isInverted The state of inversion true is inverted.
	 */
	@Override
	public void setInverted(boolean isInverted) {
		motor.setInverted(isInverted);
	}

	/**
	 * Common interface for returning if a speed controller is in the inverted state
	 * or not.
	 *
	 * @return isInverted The state of the inversion true is inverted.
	 */
	@Override
	public boolean getInverted() {
		return motor.getInverted();
	}

	/**
	 * Disable the speed controller.
	 */
	@Override
	public void disable() {
		motor.disable();
	}

	/**
	 * Stops motor movement. Motor can be moved again by calling set without having
	 * to re-enable the motor.
	 */
	@Override
	public void stopMotor() {
		motor.stopMotor();
	}

	@Override
	public void pidWrite(double output) {

	}

	@Override
	public String toString() {
		return "PIDSparkMotor(" + motor.getDeviceId() + ")";
	}

}