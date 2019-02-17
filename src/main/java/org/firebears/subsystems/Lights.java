package org.firebears.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Control the light strip animations.
 */
public class Lights extends Subsystem {

	public static final int MAX_ANIMATIONS = 7;
	public static final int MAX_PIXELSTRIPS = 3;

	public static final int TIPPINGLIGHT_ANIMATION = 0;
	public static final int RED_ANIMATION = 1;
	public static final int BLUE_ANIMATION = 2;
	public static final int CARGOHATCHPANEL = 3;
	public static final int RAINBOW_ANIMATION = 4;
	public static final int PULSE_ANIMATION = 5;
	public static final int ROCKET_ANIMATION = 6;

	public static final int ELEVATOR_STRIP = 0;
	public static final int SUPPORT_STRIP = 1;
	public static final int AFRAME_STRIP = 2;

	private final I2C i2c;
	private final DriverStation driverstation;
	private final boolean DEBUG;
	private byte[] dataOut = new byte[2];
	private byte[] dataBack = new byte[0];
	private int[] currentAnimation = new int[MAX_PIXELSTRIPS];
	private int[] nextAnimation = new int[MAX_PIXELSTRIPS];

	public Lights() {
		final Preferences config = Preferences.getInstance();
		i2c = new I2C(Port.kOnboard, config.getInt("lights.i2cAddress", 4));
		driverstation = DriverStation.getInstance();
		DEBUG = config.getBoolean("debug", false);
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Find all changed animations and push the changes out to the Arduino.
	 */
	private synchronized boolean sendAllAnimations() {
		boolean messagesSent = false;
		for (int s = 0; s < MAX_PIXELSTRIPS; s++) {
			if (nextAnimation[s] != currentAnimation[s]) {
				int a = nextAnimation[s];
				dataOut[0] = (byte) (s + '0');
				dataOut[1] = (byte) (a + '0');
				i2c.transaction(dataOut, dataOut.length, dataBack, dataBack.length);
				currentAnimation[s] = a;
				if (DEBUG) {
					System.out.printf("Lights: setAnimation(%d, %d)%n", s, a);
				}
				messagesSent = true;
			}
		}
		return messagesSent;
	}

	/**
	 * Reset all strips to their startup animations.
	 */
	public void reset() {
		for (int s = 0; s < MAX_PIXELSTRIPS; s++) {
			currentAnimation[s] = -1;
			nextAnimation[s] = currentAnimation[s];
		}
		setAnimation(ELEVATOR_STRIP, RED_ANIMATION);
		setAnimation(SUPPORT_STRIP, RED_ANIMATION);
		setAnimation(AFRAME_STRIP, RED_ANIMATION);
	}

	/**
	 * Change one strip's animation.
	 * 
	 * @param s Strip number.
	 * @param a Animation number.
	 */
	private synchronized void setAnimation(int s, int a) {
		nextAnimation[s] = a;
	}

	/**
	 * Change the lights animations based on changes within the robot.
	 */
	@Override
	public void periodic() {

		// TODO: Put lights orchestration here

		sendAllAnimations();
	}
}
