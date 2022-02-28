// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import PrimoLib.PrimoShuffleboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Pneumatics;

public class PistonForFeeder extends SubsystemBase {
  /** Creates a new PistonForFeeder. */
  private Solenoid p_feeder;
  private boolean state;


  public PistonForFeeder() {
    this.p_feeder = new Solenoid(Pneumatics.pcmPort,  PneumaticsModuleType.CTREPCM, Constants.Pneumatics.feederPort);
    this.state = true;
  }
  
  public void solenoidControll(){
    this.state = !this.state;
    this.p_feeder.set(this.state);
  }

  @Override
  public void periodic() {
    PrimoShuffleboard.getInstance().getCompetitonBoard().addEntry("Feeder Piston").forceSetBoolean(state);
    // This method will be called once per scheduler run
  }
}