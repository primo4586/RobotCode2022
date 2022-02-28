package PrimoLib.leds;

import PrimoLib.leds.LEDEffects.LEDEffect;
import PrimoLib.leds.LEDEffects.StaticColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDs {

    private LEDEffect currentEffect;
    private AddressableLED leds;
    private AddressableLEDBuffer buffer;

    public static LEDs instance;
    public static final int LEDS_PORT = 0;
    public static final int LEDS_LENGTH = 100;

    public LEDs() {
        // Commented until LEDs are installed
        // currentEffect = new StaticColor(LEDColor.PRIMO_BLUE);
        // leds = new AddressableLED(LEDS_PORT);

        // this.buffer = new AddressableLEDBuffer(LEDS_LENGTH);
    }

    public static LEDs getInstance() {
        if (instance == null) {
            instance = new LEDs();
        }
        return instance;
    }

    public void update() {
        // currentEffect.run(buffer);
        // leds.setData(buffer);
    }

    public void setEffect(LEDEffect effect) {
        this.currentEffect = effect;
    }

}
