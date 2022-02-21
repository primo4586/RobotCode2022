// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.PIDConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  //create the shooter motor
  private WPI_TalonFX m_shooter;
  private PrimoTab tab;
  private PIDController shooterPidController;
  private double pidSetpoint;
  
  public Shooter() {
    this.m_shooter = new WPI_TalonFX(Constants.ShooterConstants.ShooterPort);
    this.m_shooter.setInverted(true);
    
    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Shooter");
    shooterPidController = ShooterConstants.SHOOTER_CONFIG.getController();
    this.pidSetpoint = 0;
  }

  public void s_control (double shooterSpeed){
    // give m_shooter speed
    m_shooter.set(shooterSpeed);
  }

  public void s_PIDControl(double setpointSpeed) {
    this.pidSetpoint = setpointSpeed;

    double outputSpeed = shooterPidController.calculate(m_shooter.getSelectedSensorVelocity(), setpointSpeed);

    m_shooter.set(ControlMode.Velocity, outputSpeed);
  }

  public boolean isReadyToShoot(double pidSetpoint) {
    return pidSetpoint != 0 && pidSetpoint - m_shooter.getSelectedSensorVelocity() <= ShooterConstants.READY_SPEED_TOLERANCE;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tab.addEntry("Shooter Velocity").setNumber(m_shooter.getSelectedSensorVelocity());
    tab.addEntry("Reached target velocity").setBoolean(isReadyToShoot(pidSetpoint));
  }
  
}