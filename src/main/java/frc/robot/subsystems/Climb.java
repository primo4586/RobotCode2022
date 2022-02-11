// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

<<<<<<< HEAD
=======
import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
>>>>>>> 637f9b787003b8af8163b40b34151a9b1dfd93bf
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climb;
  private Solenoid p_level2;
  private Solenoid p_level3;
  private PrimoTab tab;

  /* 
   TO-DO: we need to add 4 sensors for knowing when the claw is closed and
    it is ok to move to the next level
    in addition, we need to handle the solenoids
  */
  
  public Climb() 
  {
<<<<<<< HEAD
    this.m_climb = new WPI_TalonFX(Constants.ClimbConstants.climbPort);
  
    //Commented because solenoids aren't installed yet:
=======
    this.m_climb = new WPI_TalonFX(0);
    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Climb");
>>>>>>> 637f9b787003b8af8163b40b34151a9b1dfd93bf
    // this.p_level2 = new Solenoid(0,PneumaticsModuleType.CTREPCM,1);
    // this.p_level3 = new Solenoid(0,PneumaticsModuleType.CTREPCM, 1);
  }

  public void c_control(double Speed)
  {
    /*
      Gets speed and set data to motor
    */
    m_climb.set(Speed);
  }

  public double getSpeed()
  {
    return m_climb.get();
  }

  public void setSolenoidLevel2State(boolean state)
  {
    /*
      sets the first to climb piston state 
    */
    this.p_level2.set(state);
  }

  public void setSolenoidLevel3State(boolean state)
  {
    /*
      sets the second to climb piston state 
    */
    this.p_level3.set(state);
  }

  public boolean isSolenoidLevel2Open(){
    /*
      gets the first to climb piston state 
    */
    return this.p_level2.get();
  }
  
  public boolean isSolenoidLevel3Open(){
    /*
      gets the first to climb piston state 
    */
    return this.p_level3.get();
  }

  /* TO-DO: explain logic in comments, why do you need three functions? */
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

    tab.addEntry("Is Hang").setBoolean(isHang());
    tab.addEntry("Level 2 Secure").setBoolean(islevel2Secure());
    tab.addEntry("Level 3 Secure").setBoolean(islevel3Secure());
    tab.addEntry("Level 4 Secure").setBoolean(islevel4Secure());
    tab.addEntry("Climb Speed").setNumber(getSpeed());
    tab.addEntry("Solenoid Lv2").setBoolean(isSolenoidLevel2Open());
    tab.addEntry("Solenoid Lv3").setBoolean(isSolenoidLevel3Open());
    

    // This method will be called once per scheduler run
  }
}