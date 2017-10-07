package org.usfirst.frc.team6816.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;

public class Robot extends IterativeRobot {

	// all our constants
	private static final double Auto_Speed = 0.5;
	private static final int Intake_Button = 0;
	private static final int Eject_Button = 1;
	private static final int Manual_Motor_Forward = 2;
	private static final int Manual_Motor_Backward = 3;
	private static final int Manual_Solenoid = 4;
	private static final int Climber_Button = 3; //arbitrary

	// all our state variables
	private boolean intakeCurrentlyPressed = false;
	private boolean ejectCurrentlyPressed = false;
	private boolean manualSolenoidCurrentlyPressed = false;

	// all our objects
	CANTalon talon11, talon12, talon13, talon14, talon15, talon16, talon17, talon18;
	Solenoid solenoid0;
	Timer timer;
	Joystick leftStick, rightStick, xboxStick;
	RobotDrive myRobot;
	DriveModule leftDriveModule, rightDriveModule;
	RollerGearPickup gearPickup;
	Compressor compressor;
	Climber climber;

	@Override

	public void robotInit() {
		// all native objects
		solenoid0 = new Solenoid(0);
		talon11 = new CANTalon(11);
		talon12 = new CANTalon(12);
		talon13 = new CANTalon(13);
		talon14 = new CANTalon(14);
		talon15 = new CANTalon(15);
		talon16 = new CANTalon(16);
		talon17 = new CANTalon(17);
		talon18 = new CANTalon(18);
		compressor = new Compressor(19);
		compressor.setClosedLoopControl(true);
		timer = new Timer();
		rightStick = new Joystick(0);
		leftStick = new Joystick(1);
		xboxStick = new Joystick(2);

		// all custom objects
		leftDriveModule = new DriveModule(talon11, talon12, talon13);
		rightDriveModule = new DriveModule(talon14, talon15, talon16);
		myRobot = new RobotDrive(leftDriveModule, rightDriveModule);
		gearPickup = new RollerGearPickup(solenoid0, talon17);
		climber = new Climber(talon18);

		// all initialization
		myRobot.setSafetyEnabled(false);
	}

	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	@Override
	public void autonomousPeriodic() {
		if (timer.get() < 2) {
			myRobot.drive(0.5, 0);
		}
		else {
			myRobot.drive(0, 0);
		}
	}

	public void teleopInit() {

	}

	@Override
	public void teleopPeriodic() {
		// drive train
		myRobot.tankDrive(leftStick, rightStick);

		// intake
		if (rightStick.getRawButton(Intake_Button) && !intakeCurrentlyPressed) {
			gearPickup.Intake();
			intakeCurrentlyPressed = true;
		} else if (!rightStick.getRawButton(Intake_Button)) {
			intakeCurrentlyPressed = false;
		}

		// eject
		if (rightStick.getRawButton(Eject_Button) && !ejectCurrentlyPressed) {
			gearPickup.Eject();
			ejectCurrentlyPressed = true;
		} else if (!rightStick.getRawButton(Eject_Button)) {
			ejectCurrentlyPressed = false;
		}

		// gear pickup
		gearPickup.CheckPeriodic();
		
		
		//manual overrides
		if (rightStick.getRawButton(Manual_Motor_Forward)) {
			gearPickup.ManualMotorForward();
		}
		
		if (rightStick.getRawButton(Manual_Motor_Backward)) {
			gearPickup.ManualMotorBackward();
		}
		
		if (rightStick.getRawButton(Manual_Solenoid) && !manualSolenoidCurrentlyPressed) {
			gearPickup.ManualSolenoid();
			manualSolenoidCurrentlyPressed = true;
		}
		else if (!rightStick.getRawButton(Manual_Solenoid)) {
			manualSolenoidCurrentlyPressed = false;
		}

		// climber
		if (xboxStick.getRawButton(Climber_Button)) {
			climber.Climb();
		}
	}

	@Override
	public void testPeriodic() {
	}
}