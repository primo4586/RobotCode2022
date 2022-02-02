// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private WPI_TalonSRX m_roller;
  private Solenoid p_joint;


  public Intake() {
  this.m_roller = new WPI_TalonSRX(0);
  this.p_joint = new Solenoid(0, null, 0); //?

  }

  public double getRollerSpeed(){
    return this.m_roller.get();
  }

  public void setRollerSpeed(double m_rollerSpeed ){
    this.m_roller.set(m_rollerSpeed); 
  }
  
  public boolean isJointOpen(){
    return this.p_joint.get();
  }

  public void setJointState(boolean open)
  {
      this.p_joint.set(open);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}