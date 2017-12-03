package org.usfirst.frc.team6488.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
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
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	RobotDrive chassis = new RobotDrive(0, 1);
	Joystick driver = new Joystick(0);
	Timer time;
	Talon climber = new Talon(2);
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	Accelerometer a = new BuiltInAccelerometer();
	boolean madeTurn = false;
	boolean hasAngle = false;
	boolean hasImpacted = false;
	double angleBeforeTurn;
	double originalHeading;
	double currentHeading;
	int itterations = 0;

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
		SmartDashboard.putNumber("Turn value in degrees", 32.5);
		SmartDashboard.getNumber("Turn value in degrees", 32.5);
		gyro.calibrate();
		originalHeading = gyro.getAngle();
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
		gyro.reset();
		itterations = 0;
		autoSelected = chooser.getSelected();
		System.out.println(autoSelected);
		madeTurn = false;
		hasAngle = false;
		hasImpacted = false;
		angleBeforeTurn = gyro.getAngle();
		originalHeading = gyro.getAngle();
		time = new Timer();
		time.reset();
		time.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// chassis.arcadeDrive(0.5,0);
		itterations++;
		// System.out.println(time.get());
		if (time.get() > 5 && !hasAngle) {
			// angleBeforeTurn = gyro.getAngle();
		}
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
		setAngle();
		double vel = driver.getRawAxis(1) * -1;
		double turn = driver.getRawAxis(2) * -1;
		chassis.arcadeDrive(vel, turn);
		if (driver.getRawButton(1)) {
			climber.setSpeed(1);
		} else {
			climber.setSpeed(0);
		}

		if (driver.getRawButton(5)) {
			chassis.arcadeDrive(0, turnToFace("left"));
		}
		if (driver.getRawButton(6)) {
			chassis.arcadeDrive(0, turnToFace("right"));
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}

	public void centerPegAuto() {
		if (!hasImpacted || time.get() < 5.5) { // Drive forward until impact
			chassis.arcadeDrive(0.6, (gyro.getAngle()-angleBeforeTurn)*0.03);
			if (itterations > 51) {
				impactCheck();
			}

		} else { // Stop
			chassis.arcadeDrive(0, 0);
		}
	}

	/**
	 * An autonomous program for crossing the line.
	 * 
	 * @author Brian Michell
	 */
	public void lineCrossAuto() {
		if (time.get() < 5) { // Drive forward 5 seconds
			chassis.arcadeDrive(0.5, 0);
		} else { // Stop
			chassis.arcadeDrive(0, 0.75);
		}
	}

	/**
	 * Untested autonomous program for putting a gear on the left side peg
	 * 
	 * @author Brian Michell
	 */
	public void leftPegAuto() {
		// System.out.println(angleBeforeTurn+" - "+ gyro.getAngle()+"
		// "+(angleBeforeTurn - gyro.getAngle() < -32.5));
		if (time.get() < 4.5) { // Drive forward 5 seconds
			chassis.arcadeDrive(0.6, 0);
		} else if (!madeTurn) { // Turn Left 32.5 degrees
			chassis.arcadeDrive(0, 0);
			if (angleBeforeTurn - gyro.getAngle() > 22.5) {
				chassis.arcadeDrive(0, -0.65);
				impactCheck();
			} else {
				madeTurn = true;
				time.reset();
			}
		} else if (time.get() < 4.5) { // Drive to peg until impact
			chassis.arcadeDrive(0.5, 0);
		} else { // Stop
			chassis.arcadeDrive(0, 0);
		}
	}

	/**
	 * Untested autonomous program for putting a gear on the right side peg
	 * 
	 * @author Brian Michell
	 */
	public void rightPegAuto() {
		if (time.get() < 4.5) { // Drive forward 5 seconds
			chassis.arcadeDrive(0.6, 0);
		} else if (!madeTurn) { // Turn right 32.5 degrees
			if (angleBeforeTurn - gyro.getAngle() < 22.5) {
				System.out.println(
						angleBeforeTurn + " - " + gyro.getAngle() + " " + (angleBeforeTurn - gyro.getAngle() < 32.5));
				chassis.arcadeDrive(0, 0.65);
			} else {
				madeTurn = true;
				time.reset();
			}
		} else if (time.get() < 4.5) { // Drive to peg until impact
			chassis.arcadeDrive(0.6, 0);
		} else { // Stop
			chassis.arcadeDrive(0, 0);
		}
	}

	public void impactCheck() {
		if (a.getX() > 1.2 || a.getY() > 1.2) {
			hasImpacted = true;
		}
	}

	public void setAngle() {
		currentHeading = gyro.getAngle();
		if (currentHeading < 0) {
			currentHeading = 360 - (gyro.getAngle() % 360);
		} else if (currentHeading > 360) {
			currentHeading = gyro.getAngle() % 360;
		} else {
			currentHeading = gyro.getAngle();
		}
		System.out.println(gyro.getAngle() + " " + currentHeading);
	}

	public double turnToFace(String dest) {
		if (dest.equals("left")) {
			if (currentHeading < 32.5 && currentHeading > 35) {
				return -0.5;
			}
		}
		if (dest.equals("right")) {
			if (currentHeading < 327.5 && currentHeading > 330) {
				return 0.5;
			}
		}
		return 0;
	}
}
