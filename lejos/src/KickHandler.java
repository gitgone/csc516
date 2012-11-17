import lejos.util.Delay;


public class KickHandler implements Runnable {
	public void run(){
		while(true){
			if(PapaMain.lightFrontLeftDetect || PapaMain.lightFrontRightDetect){
				for(int i = 0; i < 2; i++){
					PapaMain.kick_ball_at_goal();
					Delay.msDelay(100);
				}
				
			}
		}
	}
}
