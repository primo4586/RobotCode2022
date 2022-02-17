// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climbRight;
  private WPI_TalonFX m_climbleft;

  private Compressor compressor;

  private Solenoid p_level2;
  private Solenoid p_level3;

  /*
  there will be 4 switch that return if the mot is in side the claw
  and 4 piston sensors that return if the pinton is close or open
  */
  //switch:
  private DigitalInput switch_rightA;
  private DigitalInput switch_rightB;
  private DigitalInput switch_leftA;
  private DigitalInput switch_leftB;

  //piston sensors:
  private DigitalInput sPiston_rightA;
  private DigitalInput sPiston_rightB; 
  private DigitalInput sPiston_leftA; 
  private DigitalInput sPiston_leftB; 
  
  private PrimoTab tab;

  /* 
   TO-DO: we need to add 6 sensors for knowing when the claw is closed and
    it is ok to move to the next level
    in addition, we need to handle the solenoids
  */
  
  public Climb() 
  {
    this.m_climbRight = new WPI_TalonFX(0);
    this.m_climbleft = new WPI_TalonFX(0);

    this.compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
        
    this.p_level2 = new Solenoid(0, PneumaticsModuleType.CTREPCM,1);
    this.p_level3 = new Solenoid(0,PneumaticsModuleType.CTREPCM, 1);

    this.switch_leftA = new DigitalInput(0);
    this.switch_rightA = new DigitalInput(0);
    this.switch_leftB = new DigitalInput(0);
    this.switch_rightB = new DigitalInput(0);

    this.sPiston_leftA = new DigitalInput(0);
    this.sPiston_leftB = new DigitalInput(0);
    this.sPiston_rightA = new DigitalInput(0);
    this.sPiston_rightB = new DigitalInput(0);

    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Climb");
  }

  public void c_control(double Speed)
  {
    /*
      Gets speed and set data to motor
    */
    m_climbRight.set(Speed);
    m_climbleft.set(-Speed);
  }

  public double getAbsoluteSpeed()
  {
    return Math.abs(m_climbRight.get());
  }

  public double getPressure(){
    return compressor.getPressure();
  }

  public void setSolenoidLevel2or4State(boolean state)
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
  
  public boolean isMot2or4In(){
    return this.switch_leftA.get() && this.switch_rightA.get();
  }

  public boolean isMot3In(){
    return this.switch_leftB.get() && this.switch_rightB.get();
  }

  public boolean isClaw2or4close(){
    return this.sPiston_leftA.get() && this.sPiston_rightA.get();
  }

  public boolean isClaw3close(){
    return this.sPiston_leftB.get() && this.sPiston_rightB.get();
  }

  public boolean islevel2Secure(){
    //return true if the claw close on the mot and its ok to move on
    return isMot2or4In() && isClaw2or4close();
  }

  public boolean islevel3Secure(){
    //return true if the claw close on the mot and its ok to move on
    return isMot3In() && isClaw3close();
  }

  public boolean islevel4Secure(){
    //return true if the claw close on the mot and its ok to move on
    return isMot2or4In() && isClaw2or4close();
  }


  public boolean isHang(){
    return islevel2Secure() || islevel3Secure() ||islevel4Secure();
  }

  @Override
  public void periodic() {

    // TODO: Commented because this crashes the robot because solenoids aren't installed yet
    // tab.addEntry("Is Hang").setBoolean(isHang());
    // tab.addEntry("Level 2 Secure").setBoolean(islevel2Secure());
    // tab.addEntry("Level 3 Secure").setBoolean(islevel3Secure());
    // tab.addEntry("Level 4 Secure").setBoolean(islevel4Secure());
    // tab.addEntry("Climb Speed").setNumber(getSpeed());
    // tab.addEntry("Solenoid Lv2").setBoolean(isSolenoidLevel2Open());
    // tab.addEntry("Solenoid Lv3").setBoolean(isSolenoidLevel3Open());
    

    // This method will be called once per scheduler run
  }
}