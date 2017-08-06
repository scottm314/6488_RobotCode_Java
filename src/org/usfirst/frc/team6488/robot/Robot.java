package org.usfirst.frc.team6488.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive chassis;
	Joystick driver;
	Double driftPercentage;
	Timer time;
	SendableChooser<String> chooser = new SendableChooser<>();
	Thread getButtons;
	String autoSelected;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Center peg", "Center");
		chooser.addObject("Line Cross", "Line");
		chooser.addObject("**Left Peg**", "Left");
		chooser.addObject("**Right Peg**", "Right");
		SmartDashboard.putData("Auto choices (Don't use ** choices)", chooser);

		chassis = new RobotDrive(0, 1);
		driver = new Joystick(0);
		driftPercentage = 0.5;

		new Thread(() -> {
			while (true) {
				changeDrift();
				try {
					Thread.sleep(250);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		System.out.println(autoSelected);
		time = new Timer();
		time.reset();
		time.start();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		if (autoSelected.equals("Center")) {
			centerPegAuto();
		}
		if (autoSelected.equals("Line")) {
			lineCrossAuto();
		}
		if (autoSelected.equals("Left")) {
			leftPegAuto();
		}
		if (autoSelected.equals("Right")) {
			rightPegAuto();
		}

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Double vel = driver.getRawAxis(1);
		Double turn = driver.getRawAxis(2) * -1; // Inverted axis in software
		turn -= drift(vel); // Subtract power from the turn
		chassis.arcadeDrive(vel, turn);
		// System.out.println("Velocity "+vel);
		// System.out.println("Turn "+turn);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * A quadratic correction for drift on the robot. Not great to compensate
	 * for in software but all I can do right now.
	 * 
	 * TODO Find the hardware problem!
	 * 
	 * @author Brian
	 * @param velValue
	 *            The value of the forward/backward velocity
	 * @return A percentage of the velocity dependent on driftPercentage.
	 */
	public Double drift(Double velValue) {
		Double tmp = Math.abs(velValue);
		tmp *= driftPercentage;
		tmp = Math.pow(Math.abs(tmp), 1.24);
		if (velValue < 0) {
			tmp *= -1;
		}
		return tmp;
	}

	public void changeDrift() {
		if (driver.getRawButton(1) && driftPercentage <= 0.9) {
			driftPercentage += 0.05;
			System.out.println("Percentage now at: " + driftPercentage);
		} else if (driver.getRawButton(2) && driftPercentage >= 0.1) {
			driftPercentage -= 0.05;
			System.out.println("Percentage now at: " + driftPercentage);
		}
		

	}

	
	/**
	 * The autonomous program used for putting a gear on the center peg
	 * @author Brian Michell
	 */
	public void centerPegAuto() {
		if (time.get() < 5) { // Drive forward 5 seconds
			chassis.arcadeDrive(-0.5, drift(-0.5));
		} else { // Stop
			chassis.arcadeDrive(0, 0);
		}
	}

	/**
	 * An autonomous program for crossing the line.
	 * @author Brian Michell
	 */
	public void lineCrossAuto() {
		if (time.get() < 7) { // Drive forward 7 seconds
			chassis.arcadeDrive(-0.5, drift(-0.5));
		} else { // Stop
			chassis.arcadeDrive(0, 0);
			;
		}
	}

	/**
	 * Untested autonomous program for putting a gear on the left side peg
	 * @author Brian Michell
	 */
	public void leftPegAuto() {
		if (time.get() < 5) { // Drive forward 5 seconds
			chassis.arcadeDrive(-0.5, drift(-0.5));
		} else if (time.get() < 7.5 && time.get() > 5.01) { // Turn Left for 2.5 seconds
			chassis.arcadeDrive(0, 0.5);
		} else if (time.get() > 7.5 && time.get() < 10) { // Drive to peg for 2.5 seconds
			chassis.arcadeDrive(-0.5, drift(-0.5));
		} else { // Stop
			chassis.arcadeDrive(0, 0);
		}
	}

	/**
	 * Untested autonomous program for putting a gear on the right side peg
	 * @author Brian Michell
	 */
	public void rightPegAuto() {
		if (time.get() < 5) { // Drive forward 5 seconds
			chassis.arcadeDrive(-0.5, drift(-0.5));
		} else if (time.get() < 7.5 && time.get() > 5.01) { // Turn Right for 2.5 seconds
			chassis.arcadeDrive(0, -0.5);
		} else if (time.get() > 7.5 && time.get() < 10) { // Drive to peg for 2.5 seconds
			chassis.arcadeDrive(-0.5, drift(-0.5));
		} else { // Stop
			chassis.arcadeDrive(0, 0);
		}
	}

}
