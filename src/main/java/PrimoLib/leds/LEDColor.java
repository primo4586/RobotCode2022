package PrimoLib.leds;


import edu.wpi.first.wpilibj.util.Color;

public class LEDColor {

    public static final LEDColor RED = new LEDColor(255, 0, 0);
    public static final LEDColor FLAME_ORANGE = new LEDColor(237, 163, 43);
    public static final LEDColor FLAME_RED = new LEDColor(237, 24, 17);
    public static final LEDColor FLAME_YELLOW = new LEDColor(255, 251, 15);

    public static final LEDColor PRIMO_ORANGE = new LEDColor(252, 107, 3);
    public static final LEDColor PRIMO_BLUE = new LEDColor(43, 43, 252);


    public static final LEDColor[] DISCO_COLORS = { new LEDColor(251, 15, 255), PRIMO_ORANGE, PRIMO_BLUE, FLAME_YELLOW, FLAME_RED, new LEDColor(166, 8, 155)};

 


    private int red;
    private int blue;
    private int green;

    public LEDColor(int red, int green, int blue) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getRed() {
        return red;
    }

    public Color getAsWPIColor() {
        return new Color(red, green, blue);
    }
}
