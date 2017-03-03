package fr.jfbeuve.quizz;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class GpioManager implements GpioPinListenerDigital{
	
		private static final GpioController gpio = GpioFactory.getInstance();
		private GpioPinDigitalInput btn1, btn2, btn3, btn4;
		private GpioPinDigitalOutput led1,led2,led3,led4;
		private static final String BTN1 = "BTN1", BTN2 = "BTN2", BTN3 = "BTN3", BTN4="BTN4";
		private static final String LED1 = "LED1", LED2="LED2", LED3="LED3", LED4="LED4";
		private static final SoundManager sound = new SoundManager();
		
		boolean frozen=false;
	    
	    public GpioManager(){
	    	btn1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05,BTN1,PinPullResistance.PULL_DOWN);
	        led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,LED1,PinState.LOW);
	        
	        btn2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06,BTN2,PinPullResistance.PULL_DOWN);
	        led2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02,LED2,PinState.LOW);
	        
	        btn3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27,BTN3,PinPullResistance.PULL_DOWN);
	        led3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,LED3,PinState.LOW);
	        
	        btn4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25,BTN4,PinPullResistance.PULL_DOWN);
	        led4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,LED4,PinState.LOW);
	    }
	    
	    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	        GpioPin pin = event.getPin();
	        PinState state = event.getState();
	        System.out.println("STATE CHANGE: " + event.getPin() + " = "+ event.getState());
	        toggle(pin,state);
	    }
	    public void init(){
	        btn1.addListener(this);
	        btn1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led1.low();
	        
	        btn2.addListener(this);
	        btn2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led2.low();
	        
	        btn3.addListener(this);
	        btn3.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led3.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led3.low();
	        
	        btn4.addListener(this);
	        btn4.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led4.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led4.low();
	    }
	    public void shutdown(){
	    	gpio.shutdown();
	    }
	    private synchronized void toggle(GpioPin pin,PinState state){
	    	String nm = pin.getName();
	    	System.out.println(nm+" = "+state.getName());
	    	if(state.isLow()) {
	    		return;
	    	}
	    	if(!frozen&&!nm.equals(BTN4)){
		    	if(nm.equals(BTN1)){
	    			led1.high();
	    			System.out.println(">> LED1");
		    	}else if(nm.equals(BTN2)){
	    			led2.high();
	    			System.out.println(">> LED2");
	    		}else if(nm.equals(BTN3)){
	    			led3.high();
	    			System.out.println(">> LED3");
	    		}
		    	sound.play(SoundManager.BUZZER);
	    		frozen=true;
		    	System.out.println(">> +FREEZE");
	    	}else if(frozen&&nm.equals(BTN4)){
	    		System.out.println(">> -FREEZE");
	    		frozen=false;
	    		led1.low();
	    		led2.low();
	    		led3.low();
	    	}
	    }

}