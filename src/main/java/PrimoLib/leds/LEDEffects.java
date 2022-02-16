// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package PrimoLib.leds;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;

/** Add your docs here. */
public class LEDEffects {

  public interface LEDEffect {

    void run(AddressableLEDBuffer buffer);

    default boolean isFinished() {
      return false;
    }
  }

  public static class StaticColor implements LEDEffect {

    private LEDColor color;

    public StaticColor(LEDColor color) {
      this.color = color;
    }

    @Override
    public void run(AddressableLEDBuffer buffer) {
      for (int i = 0; i < buffer.getLength(); i++) {
        buffer.setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
      }
    }
  }

  public static class FlashColor implements LEDEffect {

    private LEDColor color;
    private double duration;
    private double frequency;
    private Timer timer;
    private boolean timerStart = false;

    public FlashColor(LEDColor color, double frequency, double duration) {
      this.timer = new Timer();
      this.duration = duration;
      this.frequency = frequency;
      this.color = color;
    }

    @Override
    public void run(AddressableLEDBuffer buffer) {
      if (!timerStart) {
        timer.start();
        timerStart = true;
      }

      if (timer.get() % frequency == 0) {
        for (int i = 0; i < buffer.getLength(); i++) {
          buffer.setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
        }
      } else {
        for (int i = 0; i < buffer.getLength(); i++) {
          buffer.setRGB(i, 0, 0, 0);
        }
      }
    }

    @Override
    public boolean isFinished() {
      return timer.hasElapsed(duration);
    }

  }
}
