// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package PrimoLib.leds;

import java.util.Random;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.led.ColorFlowAnimation;

import org.opencv.video.DISOpticalFlow;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

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
      for(int j = 0; j < buffers.length; j++)
      for (int i = 0; i < buffers[j].getLength(); i++) {
        buffers[j].setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
      }
    }
  }

  public static class FlashColor implements LEDEffect {

    private LEDColor color;
    private double duration;
    private double frequency;
    private Timer timer;
    private boolean flash = false;
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
      if(timer.advanceIfElapsed(frequency))
          flash = !flash;

      if (flash) {
        for(int j = 0; j < buffers.length; j++)
          for (int i = 0; i < buffers[j].getLength(); i++) {
            buffers[j].setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
          }
      } else {
        for(int j = 0; j < buffers.length; j++)
          for (int i = 0; i < buffers[j].getLength(); i++) {
            buffers[j].setRGB(i, 0, 0, 0);
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
        for (int i = 0; i < buffers[j].getLength(); i++) {
          buffers[j].setRGB(i, newColor.getRed(), newColor.getGreen(), newColor.getBlue());
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

  public static final class FlameEffect implements LEDEffect  {

    private LEDColor startFlame = LEDColor.FLAME_YELLOW;
    private LEDColor middleFlame = LEDColor.FLAME_ORANGE;
    private LEDColor endFlame = LEDColor.FLAME_RED;

    private int startLength = 10;
    private int middleLength= 15;
    private int endLength = 15;
    private Random random = new Random();



    @Override
    public void run(AddressableLEDBuffer... buffers) {
      
      int flameHeight = random.nextInt(endLength) + 1;

      for(int i = 0; i < buffers.length; i++) {
        for(int j = 0; j < buffers[i].getLength(); j++)
        {
          Color color;
          if(j <= startLength)
              color = startFlame.getAsWPIColor();
          else if(j >= (startLength + middleLength) && j < (45 - flameHeight)) {
              color = middleFlame.getAsWPIColor();
          }  
          else if(j >= (45 - flameHeight)) {
            color = endFlame.getAsWPIColor();
          } 
          else 
            color = new Color(0,0,0);
          
          buffers[i].setLED(j, color);  
        }
      }

    }
  }

  public static final class DiscoEffect implements LEDEffect {

    private LEDColor[] colors;
    private Random random = new Random();

    public DiscoEffect() {
      colors = LEDColor.DISCO_COLORS;
    }

    @Override
    public void run(AddressableLEDBuffer... buffers) {
        for(int i = 0; i < buffers.length; i++) {
        for(int j = 0; j < buffers[i].getLength(); j++) {
          int color = random.nextInt(colors.length);
          buffers[i].setLED(j, getColor(color));
        }
      }
    }

    public Color getColor(int color) {
      return colors[color].getAsWPIColor();
    }

  }
}
