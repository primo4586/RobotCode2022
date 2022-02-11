// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

<<<<<<< HEAD
=======
import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
>>>>>>> 637f9b787003b8af8163b40b34151a9b1dfd93bf
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private WPI_TalonSRX m_roller;
  private Solenoid p_joint;
<<<<<<< HEAD

  public Intake() {
    this.m_roller = new WPI_TalonSRX(Constants.IntakeConstants.rollerPort);
    // this.p_joint = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0); //Commented because solenoids aren't installed yet
=======
  private PrimoTab tab;

  public Intake() {
    this.m_roller = new WPI_TalonSRX(0);
    // this.p_joint = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0); //Commented
    // because solenoids aren't installed yet
    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Feeder");
>>>>>>> 637f9b787003b8af8163b40b34151a9b1dfd93bf
  }

  public double getRollerSpeed() {
    return this.m_roller.get();
  }

<<<<<<< HEAD
  public void r_control(double m_rollerSpeed ){
    /*
      Gets speed and set data to motor
    */
    this.m_roller.set(m_rollerSpeed); 
=======
  public void setRollerSpeed(double m_rollerSpeed) {
    this.m_roller.set(m_rollerSpeed);
>>>>>>> 637f9b787003b8af8163b40b34151a9b1dfd93bf
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