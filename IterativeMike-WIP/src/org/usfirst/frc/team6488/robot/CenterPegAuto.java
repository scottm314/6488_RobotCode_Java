package org.usfirst.frc.team6488.robot;

import edu.wpi.first.wpilibj.VictorSP;

//import edu.wpi.first.wpilibj.VictorSP;

public class CenterPegAuto extends Robot {
	
	ReadFieldSpec specs;
	double TARGDISTANCE;
	double TARGANGLE;
	
	public CenterPegAuto(String alliance){
		specs=new ReadFieldSpec(alliance);
		TARGDISTANCE=specs.getTargetDistance();
		TARGANGLE=specs.getTurnAngle();
	}
	
	public void CenterPegExecute(VictorSP drive){
		if(TARGDISTANCE>=1){
			drive.setSpeed(0.5);
		}
	}

}
