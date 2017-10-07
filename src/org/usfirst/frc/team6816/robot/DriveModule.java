package org.usfirst.frc.team6816.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;

public class DriveModule implements SpeedController {
	
	CANTalon front, mid, back;
	
	public DriveModule (CANTalon f, CANTalon m, CANTalon b) {
		front = f;
		mid = m;
		back = b;
	}

	@Override
	public void pidWrite(double output) {
		front.pidWrite(output);
		mid.pidWrite(output);
		back.pidWrite(output);
		
	}

	@Override
	public double get() {
		return front.get();
	}

	@Override
	public void set(double speed) {
		front.set(speed);
		mid.set(speed);
		back.set(speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		front.setInverted(isInverted);
		mid.setInverted(isInverted);
		back.setInverted(isInverted);
		
	}

	@Override
	public boolean getInverted() {
		return front.getInverted();
	}

	@Override
	public void disable() {
		front.disable();
		mid.disable();
		back.disable();
		
	}

	@Override
	public void stopMotor() {
		front.stopMotor();
		mid.stopMotor();
		back.stopMotor();
		
	}
	
}