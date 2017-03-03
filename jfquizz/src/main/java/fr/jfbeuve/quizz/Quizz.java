package fr.jfbeuve.quizz;

import java.io.IOException;

public class Quizz {

	public static void main(String[] args) throws InterruptedException, IOException {
		if(args.length>0){
			if(args[0].equals("test")){
				Test.main(args);
			}else if(args[0].equals("sound")){
				SoundManager.main(args);
			}
			System.exit(0);
		}else{
			GpioManager io = new GpioManager();
			try{
				io.init();
				while(true) Thread.sleep(10000);
			}finally{
				io.shutdown();
			}
		}
	}

}
