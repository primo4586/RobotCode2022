package PrimoLib.leds;

public class LEDColor {

    public static final LEDColor RED = new LEDColor(255, 0, 0);
    public static final LEDColor FLAME_ORANGE = new LEDColor(237, 163, 43);
    public static final LEDColor GREEN = new LEDColor(8, 242, 0);
    public static final LEDColor PRIMO_ORANGE = new LEDColor(252, 107, 3);
    public static final LEDColor PRIMO_BLUE = new LEDColor(43, 43, 252);



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


}
