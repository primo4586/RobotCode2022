// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package PrimoLib.leds;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;

/** Add your docs here. */
public class LEDEffects {

  public interface LEDEffect {

    void run(AddressableLEDBuffer... buffers);

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
    public void run(AddressableLEDBuffer... buffers) {
      for (int i = 0; i < buffers[0].getLength(); i++) {
        buffers[0].setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
      }
    }
  }

  public static class FlashColor implements LEDEffect {

    private LEDColor color;
    private double duration;
    private double frequency;
    private Timer timer;
    private boolean timerStart = false;

    public FlashColor(LEDColor color, double frequency) {
      this.timer = new Timer();
      // this.duration = duration;
      this.frequency = frequency;
      this.color = color;
    }

    @Override
    public void run(AddressableLEDBuffer... buffers) {
      if (!timerStart) {
        timer.start();
        timerStart = true;
      }

      if (timer.get() % frequency == 0) {
        for (int i = 0; i < buffers[0].getLength(); i++) {
          buffers[0].setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
        }
      } else {
        for (int i = 0; i < buffers[0].getLength(); i++) {
          buffers[0].setRGB(i, 0, 0, 0);
        }
      }
    }

    // @Override
    // public boolean isFinished() {
    //   if(duration == -1)
    //     return false;
    //   return timer.hasElapsed(duration);
    // }

  }

  public static final class GradientEffect implements LEDEffect {

    private LEDColor startColor;
    private LEDColor endColor;
    private int percent = 0;
    private boolean increasing = true;
    

    public GradientEffect(LEDColor startColor, LEDColor endColor) {
      this.startColor = startColor;
      this.endColor = endColor;
    }


    @Override
    public void run(AddressableLEDBuffer... buffers) {
      
      int red = startColor.getRed() + percent * (startColor.getRed()- endColor.getRed());
      int green = startColor.getGreen() + percent * (startColor.getGreen()- endColor.getGreen());
      int blue = startColor.getBlue() + percent * (startColor.getBlue()- endColor.getBlue());

      LEDColor newColor = new LEDColor(red, green, blue);
      for(int j = 0; j < buffers.length; j++)
      for (int i = 0; i < buffers[0].getLength(); i++) {
        buffers[0].setRGB(i, newColor.getRed(), newColor.getGreen(), newColor.getBlue());
      }
      
      if(percent < 0)
      {
        percent = 0;
        increasing = true;
      } 
      else if(percent >= 100) {
          percent = 100;
          increasing = false;
      }
      percent += increasing ? 5 : -5;     
    }

    


  }
}
