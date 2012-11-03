import lejos.nxt.Button;
import lejos.util.Delay;

public class SystemMonitor implements Runnable{
	
	// Main thread entry function
	public void run(){
		while(true){
			
			// Look for a user request to exit the program
			if(Button.ESCAPE.isDown()){
				System.exit(1);
			}
			
			// Delay to allow rest of system to run...
			Delay.msDelay(10);
		}
	}
}
