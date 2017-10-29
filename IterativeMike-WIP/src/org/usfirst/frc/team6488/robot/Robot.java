package org.usfirst.frc.team6488.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the recreation of the program running on FRC team 6488 RoboRams 2017 robot Mike.
 * @version 1.0.0
 * @author Brian
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	RobotDrive chassis;
	Solenoid door;
	Solenoid lift;
	Compressor comp;
	static VictorSP climbL;
	VictorSP climbR;
	VictorSP ballHop;
	SerialPort arduino;
	CenterPegAuto center;
	//ArrayList<> visionList;
	SmartDashboard dash = new SmartDashboard();
	
	
	Timer time=new Timer();
	static VictorSP testMotor; //A motor used for testing on the test bed. Delete this and all instances of it when done!

	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		chassis = new RobotDrive(0,1);
		door=new Solenoid(0);
		lift=new Solenoid(1);
		comp=new Compressor(0);
		climbL=new VictorSP(2);
		climbR=new VictorSP(3);
		climbR.setInverted(true); //Invert one motor so they spin the same way.
		ballHop=new VictorSP(4);
		//arduino=new SerialPort(9600, SerialPort.Port.kUSB1); //Sets up the Arduino to function at 9600 baud in USB port 1
		CameraServer.getInstance().startAutomaticCapture(0); //Starts the USB camera in USB port 0
		
		
		
		testMotor=new VictorSP(9);//Test motor for electronics board.
		testMotor.setSafetyEnabled(false);//TODO delete me before uploading to Mike!
		
	}

	/**
	 * This method is used to start acquiring data from the Arduino.
	 * Arduino data is passed as a formatted string of 3 seperate floats
	 * The data passed is degrees per second of turn, current direction, and distance from the back of the ball hopper to a wall.
	 * @author Brian Michell
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
		
		center=new CenterPegAuto("Blue");
		time.start();
		/* This has been commented out to allow for testing of a simple auto without the arduino.
		 *TODO  Add this back in before running on Mike!
		while(arduino.readString().length()<5){
			System.out.println("Waiting for arduino to start sending values");
		}
		System.out.println("Valid data being sent!");
		*/
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		if(time.get()<5){
			chassis.setLeftRightMotorOutputs(1, 1);
			//center.CenterPegExecute(testMotor);
		} else {
			testMotor.setSpeed(0);
		}
		
		/*
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
		*/
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

