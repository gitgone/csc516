import lejos.nxt.LCD;
import lejos.util.Delay;

public class SensorStatus implements Runnable{
	public void run(){
		while(true){
			PapaMain.lightFrontValue = PapaMain.lightFront.readValue();
			LCD.drawInt(PapaMain.lightFrontValue,0,0);
			if(PapaMain.lightFrontValue >= PapaMain.lightFrontThreshold){
				PapaMain.lightFrontDetect = true;
			}
			else {
				PapaMain.lightFrontDetect = false;
			}
			PapaMain.lightRearValue = PapaMain.lightRear.readValue();
			LCD.drawInt(PapaMain.lightRearValue,0,1);
			if(PapaMain.lightRearValue >= PapaMain.lightRearThreshold){
				PapaMain.lightRearDetect = true;
			}
			else {
				PapaMain.lightRearDetect = false;
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
			
			// Delay to allow rest of system to run...
			Delay.msDelay(10);
		}
	}
}
