import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.util.Delay;


public class CommRelay implements Runnable{

	// Communications settings
	static int numOfConnections = 3;
	
	// Main thread entry function
	public void run(){
		
		// Initialize BlueTooth (BT) communications
		try
		{
			BTConnection[] connection = new BTConnection[3]; 
			for(int i = 0; i < numOfConnections; i++){		
				connection[i] = Bluetooth.waitForConnection();
				if (connection[i] == null) {
					throw new IOException("Connect fail" + " " + i);
				}
				LCD.drawString("Connected" + " " + i, 1, 0);
				
			}
			DataInputStream[] input = new DataInputStream[3];
			DataOutputStream[] output = new DataOutputStream[3];
			
			for(int i = 0; i < 3; i++){
				input[i] = connection[i].openDataInputStream();
				output[i] = connection[i].openDataOutputStream();
				
			}
			
			int answer1 = input[0].readInt();
			LCD.drawString("1st = " + answer1, 2, 0);
			int answer2 = input[0].readInt();
			LCD.drawString("2nd = " + answer2, 3, 0);
			output[0].writeInt(0);
			output[0].flush();
			LCD.drawString("Sent data", 4, 0);
			input[0].close();
			output[0].close();
			connection[0].close();
			LCD.drawString("Bye ...", 5, 0);
		}
		catch (Exception ioe)
		{
			LCD.clear();
			LCD.drawString("ERROR", 0, 0);
			LCD.drawString(ioe.getMessage(), 2, 0);
			LCD.refresh();
		}

		Delay.msDelay(4000);
		LCD.clear();

		
		// Main processing loop
		while (true){
			
			// Perform communications...
			
			// Delay to allow rest of system to run...
			Delay.msDelay(10);
		}
	}
}
