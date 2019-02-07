package org.firebears.util;

import edu.wpi.first.wpilibj.SpeedController;
import com.revrobotics.CANSparkMax;

public class PIDSparkMotor implements SpeedController {

	private CANSparkMax motor;
	private boolean closedLoop = false;

	public PIDSparkMotor(CANSparkMax m) {
		motor = m;
	}

	public void setClosedLoop(boolean b) {
		closedLoop = b;
	}

	/**
	 * Common interface for setting the speed of a speed controller.
	 *
	 * @param speed The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {
		motor.set(speed);
	}

	/**
	 * Common interface for getting the current set speed of a speed controller.
	 *
	 * @return The current set speed. Value is between -1.0 and 1.0.
	 */
	@Override
	public double get() {
		return motor.get();
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

}
