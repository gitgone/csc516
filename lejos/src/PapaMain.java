import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.util.Delay;
import lejos.nxt.*;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import java.io.*;

public class PapaMain {
	static RegulatedMotor kick = Motor.A;
	static RegulatedMotor left = Motor.B;
	static RegulatedMotor right = Motor.C;
	static LightSensor lightFront_left = new LightSensor(SensorPort.S2);
	static LightSensor lightFront_right = new LightSensor(SensorPort.S4);
	static TouchSensor leftTouch = new TouchSensor(SensorPort.S3);
	static TouchSensor rightTouch = new TouchSensor(SensorPort.S1);
	
	//Global Values
	static int kickValue = 50;
	static int lightLeftValue;
	static int lightRightValue;
	static int lightLeftThreshold = 43;
	static int lightRightThreshold = 43;
	static int turnDelay90Degrees = 650;
	static int backupDelay = 500;
	static int obstacleAvoidAngle = 45;
	static int moveDelay10Inches = 1000;
	static int intDivisionOffset = 100;
	
	//set true to connect on initialize
	static boolean connect = false;
	
	// Motor speed settings
	static int highSpeed = 110;
	static int lowSpeed = 60;
	static boolean leftTouched = false;
	static boolean rightTouched = false;
	static boolean lightFrontLeftDetect = false;
	static boolean lightFrontRightDetect = false;
	
	// Initialization...
	
	public static void initialize(boolean connect){
		
		if (connect){
			// Create and communications relay thread
			Thread CommRelay = new Thread(new CommRelay());
			CommRelay.start();		
		}
		
		// Create and start sensor thread
		Thread sensorStatus = new Thread(new SensorStatus());
		sensorStatus.start();
		
		// Create and start system monitor thread
		Thread systemMonitor = new Thread(new SystemMonitor());
		systemMonitor.start();
		
		// Create and start kick handler thread
		Thread kickHandler = new Thread(new KickHandler());
		kickHandler.start();
		
		// Initialize motors
		kick.setSpeed((int)kick.getMaxSpeed());
		left.setSpeed((int)left.getMaxSpeed());
		right.setSpeed((int)right.getMaxSpeed());
	}
	
	// Kicking...
	
	public static void kick(int angle) {
		kick.rotate(-angle);
		Delay.msDelay(100);
		kick.rotate(angle);
	}
	
	// Turning...
	
	public static void turn_left() {
		left.forward();
		right.backward();		
	}
	
	public static void turn_right() {
		left.backward();
		right.forward();		
	}
	
	public static void turn_stop() {
		left.stop(true);
		right.stop(true);
		Delay.msDelay(100);
	}	
	
	public static void turn_left(int rotation) {
		
		int turnDelay = ((((rotation * intDivisionOffset) / 90) * turnDelay90Degrees) / intDivisionOffset);
		
		turn_left();
		Delay.msDelay(turnDelay);
		turn_stop();			
	}
	
	public static void turn_right(int rotation) {
		
		int turnDelay = ((((rotation * intDivisionOffset) / 90) * turnDelay90Degrees) / intDivisionOffset);

		turn_right();
		Delay.msDelay(turnDelay);
		turn_stop();			
	}
	
	// Movement...
	
	public static void move_forward() {
		left.backward();
		right.backward();
	}
	
	public static void move_backward() {
		left.forward();
		right.forward();
	}	
	
	public static void move_stop() {
		left.stop(true);
		right.stop(true);
	}
	
	public static void move_forward(int distance) {
		
		int moveDelay = ((((distance * intDivisionOffset) / 10) * moveDelay10Inches) / intDivisionOffset);		
		
		move_forward();
		Delay.msDelay(moveDelay);
		move_stop();		
	}
	
	public static void move_backward(int distance) {
		
		int moveDelay = ((((distance * intDivisionOffset) / 10) * moveDelay10Inches) / intDivisionOffset);		
		
		move_backward();
		Delay.msDelay(moveDelay);
		move_stop();		
	}	
	
	// High-level behaviors...
	
	public static void search_for_ball(){
		while(lightFrontLeftDetect == false && lightFrontRightDetect == false) {
			if (leftTouched == true) {
				move_stop();
				move_backward(10);
				turn_right(obstacleAvoidAngle);
			}
			
			if (rightTouched == true) {
				move_stop();
				move_backward(10);
				turn_left(obstacleAvoidAngle);
			}
			move_forward();
		}
		if(!lightFrontRightDetect) {
			int temp = left.getSpeed();
			left.setSpeed(temp/2);
			Delay.msDelay(200);
			left.setSpeed(temp);
			
			if (leftTouched == true) {
				move_stop();
				move_backward(10);
				turn_right(obstacleAvoidAngle);
			}
			
			if (rightTouched == true) {
				move_stop();
				move_backward(10);
				turn_left(obstacleAvoidAngle);
			}
			move_forward();
		}
		if(!lightFrontLeftDetect){
			int temp = right.getSpeed();
			right.setSpeed(temp/2);
			Delay.msDelay(200);
			right.setSpeed(temp);
			
			if (leftTouched == true) {
				move_stop();
				move_backward(10);
				turn_right(obstacleAvoidAngle);
			}
			
			if (rightTouched == true) {
				move_stop();
				move_backward(10);
				turn_left(obstacleAvoidAngle);
			}
			move_forward();
		}
		if(!lightFrontLeftDetect && !lightFrontRightDetect){
			if (leftTouched == true) {
				move_stop();
				move_backward(10);
				turn_right(obstacleAvoidAngle);
			}
			
			if (rightTouched == true) {
				move_stop();
				move_backward(10);
				turn_left(obstacleAvoidAngle);
			}
			move_forward();
		}
	}
	
	public static void kick_ball_at_goal(){
		Delay.msDelay(200);
		kick(kickValue);
	}
	
	// Main entry function...
	
	public static void main(String[] args) throws Exception {
		initialize(connect);
		while(true){
			search_for_ball();
		}
	}
	
}
