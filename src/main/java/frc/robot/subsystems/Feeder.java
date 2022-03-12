// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.PIDConfig;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Pneumatics;

public class Feeder extends SubsystemBase {
  // create the feeder motor
  private WPI_TalonSRX m_feeder;
  private PrimoTab tab;

  public Feeder() {
    this.m_feeder = new WPI_TalonSRX(Constants.ShooterConstants.FeederPort);

    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Feeder");

    tab.addEntry("setPoint").setNumber(0);
    tab.addEntry("Voltage").setNumber(0);
  }

  public void f_control(double feederSpeed) {
    // give m_feeder speed
    m_feeder.set(feederSpeed);
  }

  public void setVoltage(double voltage) {
    m_feeder.setVoltage(voltage);
  }

  public void setConfig(PIDConfig config) {
    m_feeder.config_kP(0, config.getKp());
    m_feeder.config_kI(0, config.getKi());
    m_feeder.config_kD(0, config.getKd());
    m_feeder.config_kF(0, config.getKf());
  }

  public void pidControl(double setpoint) {
    m_feeder.set(ControlMode.Velocity, setpoint);
  }

  public PrimoTab getTab() {
      return tab;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
