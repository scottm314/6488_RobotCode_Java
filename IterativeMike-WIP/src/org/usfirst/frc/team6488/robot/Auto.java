package org.usfirst.frc.team6488.robot;

/**
 * This interface is used to help set up field dimensions.
 * @author Brian
 *@version 1.0.0
 */

public interface Auto {
	
	public double getTargetDistance();
	public double getTurnAngle();
	public String getAlliance();
	public void setAlliance(String alliance);

}
