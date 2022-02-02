// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climb;
  private DoubleSolenoid p_right;
  private DoubleSolenoid p_left;
  
  public Climb() 
  {
    this.m_climb = new WPI_TalonFX(0);

    this.p_right = new DoubleSolenoid(null, 1,2);
    this.p_left = new DoubleSolenoid(null, 1,2);
  }

  public void setSpeed(double Speed)
  {
    m_climb.set(Speed);
  }

  public double getSpeed()
  {
    return m_climb.get();
  }

  public void setRightPistonValue(int pValue)
  {
    //0: k0ff -1:kReverse 1:kForward

    switch(pValue){
      case 0:
        p_right.set(Value.kOff);
        break;
      case -1:
        p_right.set(Value.kReverse);
        break;
      case 1:
        p_right.set(Value.kForward);
        break;
    }
  }

  public void setLeftPistonValue(int pValue)
  {
    //0: k0ff -1:kReverse 1:kForward

    switch(pValue){
      case 0:
        p_left.set(Value.kOff);
        break;
      case -1:
        p_left.set(Value.kReverse);
        break;
      case 1:
        p_left.set(Value.kForward);
        break;
    }
  }

  public int getRightPistonValue()
  {
   //0: k0ff -1:kReverse 1:kForward

    Value value = p_right.get();

    switch(value){
      case kReverse:
        return -1;
      case kOff:
        return 0;
    }

    return 1;
  }

  public int getLeftPistonValue()
  {
   //0: k0ff -1:kReverse 1:kForward

    Value value = p_left.get();

    switch(value){
      case kReverse:
        return -1;
      case kOff:
        return 0;
    }

    return 1;
  }




  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}