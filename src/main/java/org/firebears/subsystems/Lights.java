package org.firebears.subsystems;

import org.firebears.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Control the light strip animations.
 */
public class Lights extends Subsystem {


	

	public static final int MAX_ANIMATIONS = 9;
	public static final int MAX_PIXELSTRIPS = 3;

	public static final int TIPPINGLIGHT_ANIMATION = 0;
	public static final int RED_ANIMATION = 1;
	public static final int BLUE_ANIMATION = 2;
	public static final int CARGOANIMATION = 3;
	public static final int RAINBOW_ANIMATION = 4;
	public static final int PULSE_ANIMATION = 5;
	public static final int ROCKET_ANIMATION = 6;
	public static final int ISEEYOU_ANIMATION = 7;
	public static final int HATCHPANELANIMATION = 8;  

	public static final int ELEVATOR_STRIP = 0;
	public static final int SUPPORT_STRIP = 1;
	public static final int AFRAME_STRIP = 2;

	private final I2C i2c;
	private final DriverStation driverstation;
	private final boolean DEBUG;
	private long celebrateTimeout = -1;
	private long cargoaniamtionTimeout = -1;
	private long hatchpanelanimationTimeout = -1;
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
		int defaultAnimation = driverstation.getAlliance().equals(Alliance.Blue) ? BLUE_ANIMATION : RED_ANIMATION;
		setAnimation(ELEVATOR_STRIP, ROCKET_ANIMATION);
		setAnimation(SUPPORT_STRIP, defaultAnimation);
		setAnimation(AFRAME_STRIP, defaultAnimation);
	}

	public void setCelebrateMode(boolean celebrate) {

		if (celebrate) {
			celebrateTimeout = System.currentTimeMillis() + 3 * 1000L;
		} else {
			celebrateTimeout = -1;
		}
	}

	public boolean isCelebrating() {
		if (celebrateTimeout < 0 || System.currentTimeMillis() > celebrateTimeout) {
			celebrateTimeout = -1;
			return false;
		} else {
			return true;
		}
	}
	public void setCargoMode(boolean cargoanimation) {
		if (cargoanimation) {
			cargoaniamtionTimeout = System.currentTimeMillis() + 3 * 1000L;
		} else {
			cargoaniamtionTimeout = -1;
		}
	}

	/**
	 * indecaits we want a cargo piece.
	 */
	public boolean isCargoMode() {
		if (cargoaniamtionTimeout < 0 || System.currentTimeMillis() > cargoaniamtionTimeout) {
			cargoaniamtionTimeout = -1;
			return false;
		} else {
			return true;
		}
	}/**
	* indecaits we want a hatch panel.
	*/
	
	public void setHatchPanelMode(boolean hatchpanelanimation) {
		if (hatchpanelanimation) {
			hatchpanelanimationTimeout = System.currentTimeMillis() + 3 * 1000L;
		} else {
			hatchpanelanimationTimeout = -1;
		}
	}

	/**
	 * indecaits we want a cargo piece
	 */
	public boolean isHatchPanelMode() {
		if (hatchpanelanimationTimeout < 0 || System.currentTimeMillis() > cargoaniamtionTimeout) {
			hatchpanelanimationTimeout = -1;
			return false;
		} else {
			return true;
		}
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
		int defaultAnimation = driverstation.getAlliance().equals(Alliance.Blue) ? BLUE_ANIMATION : RED_ANIMATION;

		if (Robot.chassis.isTipping()) {
			setAnimation(ELEVATOR_STRIP, TIPPINGLIGHT_ANIMATION);
			setAnimation(SUPPORT_STRIP, TIPPINGLIGHT_ANIMATION);
			setAnimation(AFRAME_STRIP, TIPPINGLIGHT_ANIMATION);
		} else if (isCelebrating()) {
			setAnimation(ELEVATOR_STRIP, RAINBOW_ANIMATION);
			setAnimation(SUPPORT_STRIP, RAINBOW_ANIMATION);
			setAnimation(AFRAME_STRIP, RAINBOW_ANIMATION);
		} else {
			setAnimation(ELEVATOR_STRIP, defaultAnimation);
			setAnimation(SUPPORT_STRIP, defaultAnimation);
			setAnimation(AFRAME_STRIP, defaultAnimation);
			// TODO : add lots more lights orchestration here
		}  if (Robot.vision.getVisionTargetConfidence() > 0.8){
			setAnimation(ELEVATOR_STRIP, ISEEYOU_ANIMATION);
			setAnimation(SUPPORT_STRIP, ISEEYOU_ANIMATION);
			setAnimation(AFRAME_STRIP, ISEEYOU_ANIMATION);
		}  else {
			setAnimation(ELEVATOR_STRIP, defaultAnimation);
			setAnimation(SUPPORT_STRIP, defaultAnimation);
			setAnimation(AFRAME_STRIP, defaultAnimation);
		}   if (driverstation.getAlliance() == DriverStation.Alliance.Blue) {
			setAnimation(ELEVATOR_STRIP, BLUE_ANIMATION);
			setAnimation(SUPPORT_STRIP, BLUE_ANIMATION);
			setAnimation(AFRAME_STRIP, BLUE_ANIMATION);
		} else if (driverstation.getAlliance() == DriverStation.Alliance.Red) {
			setAnimation(ELEVATOR_STRIP, RED_ANIMATION);
			setAnimation(SUPPORT_STRIP, RED_ANIMATION);
			setAnimation(AFRAME_STRIP, RED_ANIMATION);
		
		}   if (isCargoMode()) {
			setAnimation(ELEVATOR_STRIP, CARGOANIMATION);
			setAnimation(SUPPORT_STRIP, CARGOANIMATION);
			setAnimation(AFRAME_STRIP, CARGOANIMATION);
		}   else if (isHatchPanelMode()) {
			setAnimation(ELEVATOR_STRIP, HATCHPANELANIMATION);
			setAnimation(SUPPORT_STRIP, HATCHPANELANIMATION);
			setAnimation(AFRAME_STRIP, HATCHPANELANIMATION);
		}   if (driverstation.isDisabled()){
			setAnimation(ELEVATOR_STRIP, ROCKET_ANIMATION);
			setAnimation(SUPPORT_STRIP, PULSE_ANIMATION);
			setAnimation(AFRAME_STRIP, PULSE_ANIMATION);
		} else {
			setAnimation(ELEVATOR_STRIP, defaultAnimation);
			setAnimation(SUPPORT_STRIP, defaultAnimation);
			setAnimation(AFRAME_STRIP, defaultAnimation);
		}
		sendAllAnimations();
	}
}
