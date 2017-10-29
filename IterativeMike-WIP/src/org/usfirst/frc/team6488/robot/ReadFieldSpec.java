package org.usfirst.frc.team6488.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Every field dimension should be measured to ensure a consistent auto.
 * All dimensions should then be saved to a text document for the scanner to read.
 * @author Brian
 * @version 1.0.0
 *
 */

public class ReadFieldSpec implements Auto{
	
	File specs = new File("C:\\Users\\scott\\Desktop\\FieldSpecs.txt");
	String alliance;
	
	
	public ReadFieldSpec(String al){
		System.out.println("Reading field specs now");
		alliance=al;
	}

	@Override
	
	/**
	 * Used to set the distance needed to travel in a straight line towards the peg.
	 */
	public double getTargetDistance() {
		
		try {
			Scanner scan = new Scanner(specs);
			while(scan.hasNextLine()){
				if(scan.nextLine().equals(alliance+"Distance")){
					System.out.println("Message: Data found for *Distance*!");
					return Double.parseDouble(scan.nextLine()); //Returns the number that is one line down from the label
				}
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
			e.printStackTrace();
		}
		
		System.out.println("Error: Value not found");
		return 120; //If it can't find the number then it will return a default number.
	}

	@Override
	
	 /**
	  * Used to instruct the robot how much to turn.
	  */
	public double getTurnAngle() {
		try {
			Scanner scan=new Scanner(specs);
			while(scan.hasNextLine()){
				if(scan.nextLine().equals(alliance+"Angle")){
					System.out.println("Message: Data found for *Angle*!");
					return Double.parseDouble(scan.nextLine());
				}
			scan.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
			e.printStackTrace();
		}
		System.out.println("Error: Value not found");
		return 32;
	}

	@Override
	
	/**
	 * Since fields aren't equal on both sides use this to know which data to read.
	 */
	public String getAlliance() {
		return alliance;
	}
	
	/**
	 * The method used to automatically set the alliance color.
	 * @param The color of the alliance. The first letter must be capital and the others must be lowercase. Either "Red" or "Blue".
	 */
	public void setAlliance(String al){
		alliance=al;
	}
	
	

}
