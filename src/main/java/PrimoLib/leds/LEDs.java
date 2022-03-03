package PrimoLib.leds;

import java.awt.Color;

import javax.sound.midi.Soundbank;

import PrimoLib.leds.LEDEffects.FlashColor;
import PrimoLib.leds.LEDEffects.LEDEffect;
import PrimoLib.leds.LEDEffects.StaticColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDs {

    private LEDEffect currentClimbBarsEffect;
    private AddressableLED climbLEDs; 
    private AddressableLEDBuffer[] climbBuffers;
    private AddressableLEDBuffer mainBuffer;

    public static final int LENGTH = 180;
    public static final int PORT = 7;

    public static LEDs instance;

    public LEDs() {
        currentClimbBarsEffect = new StaticColor(LEDColor.PRIMO_ORANGE);
        climbLEDs = new AddressableLED(PORT);
        climbLEDs.setLength(LENGTH);
        climbLEDs.start();

        this.climbBuffers = new AddressableLEDBuffer[4];
        this.mainBuffer = new AddressableLEDBuffer(LENGTH);

    }

    public static LEDs getInstance() {
        if (instance == null) {
            instance = new LEDs();
        }
        return instance;   
     }

    public void update() {
        
        currentClimbBarsEffect.run(climbBuffers);

        
        
        // switchLEDs(0);
        
    

        // for(int i = 0; i < 2; i++) {
        //     shooterLEDs[i].setData(shooterBuffer);
        // }
    }


    public void setClimbBarsEffect(LEDEffect effect) {
        this.currentClimbBarsEffect = effect;
    }


}
