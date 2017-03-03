package fr.jfbeuve.quizz;

import java.io.IOException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Test {
	private static GpioPinDigitalOutput led1,led2,led3,led4,btn1,btn2,btn3,btn4;
	private static final String BTN1 = "BTN1", BTN2 = "BTN2", BTN3 = "BTN3", BTN4="BTN4";
	private static final String LED1 = "LED1", LED2="LED2", LED3="LED3", LED4="LED4";
	private static GpioController gpio;
	
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, Raspberry Pi!");
        gpio = GpioFactory.getInstance();
        init();
        System.out.println("LED1...");
        apply(true,false,false,false,false,false,false,false);
        System.in.read();
        System.out.println("LED2...");
        apply(false,true,false,false,false,false,false,false);
        System.in.read();
        System.out.println("LED3...");
        apply(false,false,true,false,false,false,false,false);
        System.in.read();
        System.out.println("LED4...");
        apply(false,false,false,true,false,false,false,false);
        System.in.read();
        System.out.println("BTN1...");
        apply(false,false,false,false,true,false,false,false);
        System.in.read();
        System.out.println("BTN2...");
        apply(false,false,false,false,false,true,false,false);
        System.in.read();
        System.out.println("BTN3...");
        apply(false,false,false,false,false,false,true,false);
        System.in.read();
        System.out.println("BTN4...");
        apply(false,false,false,false,false,false,false,true);
        System.in.read();
        apply(false,false,false,false,false,false,false,false);
        System.out.println("...END");
        gpio.shutdown();
    }
    private static void apply(boolean l1, boolean l2, boolean l3, boolean l4,
    		boolean b1, boolean b2, boolean b3, boolean b4){
    	if(l1) led1.high(); else led1.low();
    	if(l2) led2.high(); else led2.low();
    	if(l3) led3.high(); else led3.low();
    	if(l4) led4.high(); else led4.low();
    	
    	if(b1) btn1.high(); else btn1.low();
    	if(b2) btn2.high(); else btn2.low();
    	if(b3) btn3.high(); else btn3.low();
    	if(b4) btn4.high(); else btn4.low();
    }
    private static void init(){
    	btn1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,BTN1,PinState.LOW);
    	led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,LED1,PinState.LOW);
        
        btn2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,BTN2,PinState.LOW);
        led2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02,LED2,PinState.LOW);
        
        btn3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27,BTN3,PinState.LOW);
        led3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,LED3,PinState.LOW);
        
        btn4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25,BTN4,PinState.LOW);
        led4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,LED4,PinState.LOW);
    }
}
