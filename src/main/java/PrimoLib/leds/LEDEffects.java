// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package PrimoLib.leds;

import java.text.CollationElementIterator;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/** Add your docs here. */
public class LEDEffects {

  public interface LEDEffect {

    void run(AddressableLEDBuffer buffer);

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

}
