import java.io.*;

import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.util.Delay;
import lejos.nxt.*;

public class PapaMain {
	static RegulatedMotor kick = Motor.A;
	static RegulatedMotor left = Motor.B;
	static RegulatedMotor right = Motor.C;
	static LightSensor lightFront = new LightSensor(SensorPort.S2);
	static LightSensor lightRear = new LightSensor(SensorPort.S4);
	static TouchSensor leftTouch = new TouchSensor(SensorPort.S3);
	static TouchSensor rightTouch = new TouchSensor(SensorPort.S1);
	
	//Global Values
	static int kickValue = 75;
	static int lightFrontValue;
	static int lightRearValue;
	static int lightFrontThreshold = 45;
	static int lightRearThreshold = 75;
	//Motor speed settings
	static int highSpeed = 110;
	static int lowSpeed = 60;
	static boolean leftTouched = false;
	static boolean rightTouched = false;
	static boolean lightFrontDetect = false;
	static boolean lightRearDetect = false;
	
	public static void main(String[] args) throws Exception {
		initialize();
		//control loop
		while(true){
			if(lightFrontDetect){
				kick(kickValue);
			}
			if(Button.ESCAPE.isDown()){
				System.exit(1);
			}
		}
	}
	
	public static void initialize(){
		Thread sensorStatus = new Thread(new SensorStatus());
		sensorStatus.start();
		kick.setSpeed((int)kick.getMaxSpeed());
		left.setSpeed((int)left.getMaxSpeed());
		right.setSpeed((int)right.getMaxSpeed());
	}
	
	public static void kick(int angle) {
		kick.rotate(-angle);
		Delay.msDelay(100);
		kick.rotate(angle);
	}
}
