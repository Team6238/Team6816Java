package org.usfirst.frc.team6816.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;

public class RollerGearPickup {

	Solenoid solenoid;
	CANTalon talon;
	Timer timer;
	int machineState; // 0 = neutral, 1 = intake, 2 = eject
	DigitalInput limitSwitch;

	public RollerGearPickup(Solenoid newSolenoid, CANTalon newTalon) {
		solenoid = newSolenoid;
		talon = newTalon;
		limitSwitch = new DigitalInput(0); // gpio pin 0 = limit switch
		machineState = 0;
		solenoid.set(true);
	}

	// handle based on state
	public void CheckPeriodic() {
		if (machineState == 1) {
			IntakeHandler();
		}
		if (machineState == 2) {
			EjectHandler();
		}
	}

	// state check; lower solenoid
	public void Intake() {
		machineState = 1;
		solenoid.set(false);
	}

	// state check; reset timer
	public void Eject() {
		machineState = 2;
		timer.reset();
		timer.start();
	}

	// intake handler; run talon forward until limit switch activated, then raise
	// solenoid
	private void IntakeHandler() {
		talon.set(0.5);
		if (limitSwitch.get()) {
			talon.set(0);
			solenoid.set(true);
			machineState = 0;
		}
	}

	// eject handler; run talon backward for 5 seconds
	private void EjectHandler() {
		talon.set(-0.5);
		if (timer.get() > 5.0) { // motor runs backward for 5 seconds
			talon.set(0);
			timer.stop();
			machineState = 0;
		}
	}

}
