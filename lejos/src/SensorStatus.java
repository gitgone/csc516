import lejos.nxt.LCD;
import lejos.util.Delay;

public class SensorStatus implements Runnable{
	public void run(){
		while(true){
			PapaMain.lightLeftValue = PapaMain.lightFront_left.readValue();
			LCD.drawInt(PapaMain.lightLeftValue,0,0);
			if(PapaMain.lightLeftValue >= PapaMain.lightLeftThreshold){
				PapaMain.lightFrontLeftDetect = true;
			}
			else {
				PapaMain.lightFrontLeftDetect = false;
			}
			PapaMain.lightRightValue = PapaMain.lightFront_right.readValue();
			LCD.drawInt(PapaMain.lightRightValue,0,1);
			if(PapaMain.lightRightValue >= PapaMain.lightRightThreshold){
				PapaMain.lightFrontRightDetect = true;
			}
			else {
				PapaMain.lightFrontRightDetect = false;
			}
			if(PapaMain.leftTouch.isPressed()){
				LCD.drawString("Left touch", 0, 2);
				PapaMain.leftTouched = true;
			}
			else {
				LCD.clear(2);
				PapaMain.leftTouched = false;
			}
			if(PapaMain.rightTouch.isPressed()){
				LCD.drawString("Right touch", 0, 3);
				PapaMain.rightTouched = true;
			}
			else {
				LCD.clear(3);
				PapaMain.rightTouched = false;
			}
			if(PapaMain.lightFrontLeftDetect == true){
				LCD.drawString("Light Front Left", 0, 4);
			}
			else {
				LCD.clear(4);
			}
			if(PapaMain.lightFrontRightDetect == true){
				LCD.drawString("Light Front Right", 0, 5);
			}
			else {
				LCD.clear(5);
			}
			
			// Delay to allow rest of system to run...
			//Delay.msDelay(10);
		}
	}
}
