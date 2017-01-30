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
		
		boolean frozen=false;
	    
	    public GpioManager(){
	    	btn1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01,BTN1,PinPullResistance.PULL_DOWN);
	        btn1.addListener(this);
	        btn1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,LED1,PinState.LOW);
	        led1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led1.low();
	        
	        btn2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,BTN2,PinPullResistance.PULL_DOWN);
	        btn2.addListener(this);
	        btn2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,LED2,PinState.LOW);
	        led2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led2.low();
	        
	        btn3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03,BTN3,PinPullResistance.PULL_DOWN);
	        btn3.addListener(this);
	        btn3.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,LED3,PinState.LOW);
	        led3.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led3.low();
	        
	        btn4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04,BTN4,PinPullResistance.PULL_DOWN);
	        btn4.addListener(this);
	        btn4.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08,LED4,PinState.LOW);
	        led4.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	        led4.low();
	    }
	    
	    @Override
	    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	        GpioPin pin = event.getPin();
	        PinState state = event.getState();
	        System.out.println("STATE CHANGE: " + event.getPin() + " = "+ event.getState());
	        toggle(pin,state);
	    }
	    
	    private synchronized void toggle(GpioPin pin,PinState state){
	    	if(state.isLow()) return;
	    	if(!frozen){
		    	switch(pin.getName()){
	    			case BTN1:led1.high();break;
	    			case BTN2:led2.high();break;
	    			case BTN3:led3.high();break;
	    			default:
		    	}
		    	frozen=true;
	    	}else if(pin.getName().equals(BTN4)){
	    		frozen=false;
	    		led1.low();
	    		led2.low();
	    		led3.low();
	    		led4.low();
	    	}
	    }
}