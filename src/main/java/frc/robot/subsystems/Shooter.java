// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.PIDConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  //create the shooter motor
  private WPI_TalonFX m_shooter;
  private PrimoTab tab;
  private double pidSetpoint;

  
  public Shooter() {
    this.m_shooter = new WPI_TalonFX(Constants.ShooterConstants.ShooterPort);

    this.m_shooter.setInverted(true);
    this.setConfig(ShooterConstants.SHOOTER_CONFIG);

    // this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Shooter");
    // tab.addEntry("Speed");
    this.pidSetpoint = 0;
  }

  public void s_control (double shooterSpeed){
    // give m_shooter speed
    this.m_shooter.set(Constants.ShooterConstants.ShooterSpeed);
  }

 

  public void setConfig(PIDConfig config) {
    this.m_shooter.config_kP(0, config.getKp());
    this.m_shooter.config_kI(0, config.getKi());
    this.m_shooter.config_kD(0, config.getKd());
    this.m_shooter.config_kF(0, config.getKf());
  }

  public void setVelocity(double velocity) {
    this.pidSetpoint = velocity;
    this.m_shooter.set(ControlMode.Velocity, velocity);

  }  
  
  public double getShooterVelocity(){
    return this.m_shooter.getSelectedSensorVelocity();
  }

  public double getPidSetpoint() {
      return pidSetpoint;
  }

  public PrimoTab getTab() {
      return tab;
  }

  public boolean isReadyToShoot() {
    return pidSetpoint != 0 && Math.abs(m_shooter.getSelectedSensorVelocity() - pidSetpoint) <= ShooterConstants.READY_SPEED_TOLERANCE;
  }
  
  
  public boolean isWithInRange(double distance) {
    return distance < ShooterConstants.MAX_SHOOTING_RANGE && distance >= ShooterConstants.MIN_SHOOTING_RANGE;
  }   
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
