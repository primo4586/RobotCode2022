package PrimoLib.leds;

public class LEDColor {

    private int red;
    private int blue;
    private int green;

    public LEDColor(int red, int blue, int green) {
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
