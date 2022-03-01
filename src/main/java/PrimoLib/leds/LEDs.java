package PrimoLib.leds;

import javax.sound.midi.Soundbank;

import PrimoLib.leds.LEDEffects.LEDEffect;
import PrimoLib.leds.LEDEffects.StaticColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDs {

    private LEDEffect currentClimbBarsEffect;
    private LEDEffect currentShooterSideEffect;

    private AddressableLED[] climbLEDs; // PORTS: 
    private AddressableLED[] shooterLEDs;


    private AddressableLEDBuffer climbBuffer;
    private AddressableLEDBuffer shooterBuffer;

    public static LEDs instance;

    public LEDs() {
        currentClimbBarsEffect = new StaticColor(LEDColor.PRIMO_BLUE);
        // currentShooterSideEffect = new StaticColor(LEDColor.PRIMO_ORANGE);
        this.climbLEDs = new AddressableLED[4];

        climbLEDs[0] = new AddressableLED(5);
        climbLEDs[0].setLength(40);
        // int port = 5;
        // for(int i = 0; i < 4; i++) {
        //     climbLEDs[i] = new AddressableLED(port);
        //     climbLEDs[i].setLength(40);
        //     port++;
        // }
        // for(int i = 0; i < 2; i++) {
        //     shooterLEDs[i] = new AddressableLED(port);
        //     port++;
        // }

        climbLEDs[0].start();
        this.climbBuffer = new AddressableLEDBuffer(40);
        // this.shooterBuffer = new AddressableLEDBuffer(20);
    }

    public static LEDs getInstance() {
        if (instance == null) {
            instance = new LEDs();
        }
        return instance;
    }

    public void update() {
        // currentShooterSideEffect.run(shooterBuffer);
        // currentClimbBarsEffect.run(climbBuffer);
        // System.out.println("test");
        for (int i = 0; i < climbBuffer.getLength(); i++) {
            climbBuffer.setRGB(i, 255, 0, 0);
        }
        climbLEDs[0].setData(climbBuffer);

        // for(int i = 0; i < 4; i++) {
        //     climbLEDs[0].setData(climbBuffer);
        // }
    

        // for(int i = 0; i < 2; i++) {
        //     shooterLEDs[i].setData(shooterBuffer);
        // }
    }

    public void setClimbBarsEffect(LEDEffect effect) {
        this.currentClimbBarsEffect = effect;
    }

    public void setShooterSideEffect(LEDEffect effect) {
        this.currentShooterSideEffect = effect;
    }

}
