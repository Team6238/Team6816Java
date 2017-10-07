package org.usfirst.frc.team6816.robot;

import com.ctre.CANTalon;


public class Climber {
	
	CANTalon talon;
	
	public Climber(CANTalon newTalon){
		talon = newTalon;
	}
	
	public void Climb(){
		talon.set(0.5);
	}

}