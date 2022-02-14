// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
  // create the feeder motor
  private WPI_TalonSRX m_feeder;
  private PrimoTab tab;

  public Feeder() {
    this.m_feeder = new WPI_TalonSRX(Constants.ShooterConstants.FeederPort);
    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Feeder");
  }

  public void f_control (double feederSpeed){
    //give m_feeder speed
     m_feeder.set(feederSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tab.addEntry("Feeder Velocity").setNumber(m_feeder.getSelectedSensorVelocity());
  }

}
