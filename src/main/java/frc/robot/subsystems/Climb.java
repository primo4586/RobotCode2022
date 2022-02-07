// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climb;
  private Solenoid p_level2;
  private Solenoid p_level3;

  //we neeed to add 4 sensors for knowing when the claw is closed and
  // it is ok to move to the next level
  
  public Climb() 
  {
    this.m_climb = new WPI_TalonFX(0);

    // this.p_level2 = new Solenoid(0,PneumaticsModuleType.CTREPCM,1);
    // this.p_level3 = new Solenoid(0,PneumaticsModuleType.CTREPCM, 1);
  }

  public void setSpeed(double Speed)
  {
    m_climb.set(Speed);
  }

  public double getSpeed()
  {
    return m_climb.get();
  }

  public void setSolenoidLevel2State(boolean state)
  {
    this.p_level2.set(state);
  }

  public void setSolenoidLevel3State(boolean state)
  {
    this.p_level3.set(state);
  }

  public boolean isSolenoidLevel2Open(){
    return this.p_level2.get();
  }
  
  public boolean isSolenoidLevel3Open(){
    return this.p_level3.get();
  }

  public boolean islevel2Secure(){
    //just for now untill we will know better
    return true;
  }

  public boolean islevel3Secure(){
    //just for now untill we will know better
    return true;
  }

  public boolean islevel4Secure(){
    //just for now untill we will know better
    return true;
  }

  public boolean isHang(){
    return islevel2Secure() || islevel3Secure() ||islevel4Secure();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}