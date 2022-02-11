// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */

  //create the shooter motor
  private WPI_TalonFX m_shooter;
  
  public Shooter() {
      this.m_shooter = new WPI_TalonFX(Constants.ShooterConstants.ShooterPort);
      this.m_shooter.setInverted(true);
  }

  public void s_control (double shooterSpeed){
   //give m_shooter speed
    m_shooter.set(shooterSpeed);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}