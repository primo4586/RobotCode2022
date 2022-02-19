// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  
  private WPI_TalonSRX m_roller;
  private Solenoid p_joint;
  
  private PrimoTab tab;

  public Intake() {
    this.m_roller = new WPI_TalonSRX(Constants.IntakeConstants.rollerPort);
    this.p_joint = new Solenoid(8, PneumaticsModuleType.CTREPCM, 5); //Commented
    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Feeder");
  }

  public double getRollerSpeed() {
    return this.m_roller.get();
  }

  public void r_control(double m_rollerSpeed ){
    /*
      Gets speed and set data to motor
    */
    this.m_roller.set(m_rollerSpeed); 
  }
  
  public boolean isJointOpen() {
    return this.p_joint.get();
  }

  public void setJointState(boolean open) {
    this.p_joint.set(open);
  }

  @Override
  public void periodic() {

    tab.addEntry("Roller Speed").setNumber(getRollerSpeed());
    tab.addEntry("Joint State").setBoolean(isJointOpen());
    // This method will be called once per scheduler run
  }
}