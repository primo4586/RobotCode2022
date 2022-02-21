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
import frc.robot.Constants;
public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climbRight;
  private WPI_TalonFX m_climbleft;

  private Compressor compressor;

  private Solenoid solenoidA; // Side A
  private Solenoid solenoidB; // Side B


  //switch:
  private DigitalInput switchA;
  private DigitalInput switchB;

  //piston sensors:
  private DigitalInput sPistonA; // Right A
  private DigitalInput sPistonB; // Left B
  
  private PrimoTab tab;
  private boolean isEnabled;
  
  public Climb() 
  {
    this.m_climbRight = new WPI_TalonFX(Constants.ClimbConstants.rightMotorPort);
    this.m_climbleft = new WPI_TalonFX(Constants.ClimbConstants.leftMotorPort);

    this.m_climbleft.setInverted(true);

    //this.compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
        
    this.solenoidA = new Solenoid(Constants.Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM, Constants.Pneumatics.climbSolenoidA);
    this.solenoidB = new Solenoid(Constants.Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM, Constants.Pneumatics.climbSolenoidB);
    
    this.switchA = new DigitalInput(0);
    this.switchB = new DigitalInput(0);

    this.sPistonA = new DigitalInput(0);
    this.sPistonB = new DigitalInput(0);

    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Climb");
    
    //this.isEnabled = false;
    
    
  }

  public void c_control(double speed)
  {
    /*
      Gets speed and set data to motor
    */
    speed *= 0.7;
    m_climbRight.set(speed);
    m_climbleft.set(speed);
  }

  public double getAbsoluteSpeed()
  {
    return Math.abs(m_climbRight.get());
  }

  public void setSolenoidLevel2or4(boolean state)
  {
    /*
      sets the first to climb piston state 
    */
    this.solenoidA.set(state);
  }

  public void setSolenoidLevel3(boolean state)
  {
    /*
      sets the second to climb piston state 
    */
    this.solenoidB.set(state);
  }

  
  /* TO-DO: explain logic in comments, why do you need three functions? */
  
  public boolean isMot2or4In(){
    return this.switchA.get();
  }

  public boolean isMot3In(){
    return this.switchB.get();
  }

  public boolean isClawLockOn2or4(){
    return this.sPistonA.get();
  }

  public boolean isClawLockOn3(){
    return this.sPistonB.get();
  }

  public boolean islevel2or4Secure(){
    //return true if the claw close on the mot and its ok to move on
    return isMot2or4In() && isClawLockOn2or4();
  }

  public boolean islevel3Secure(){
    //return true if the claw close on the mot and its ok to move on
    return isMot3In() && isClawLockOn3();
  }


  public boolean isHang(){
    return islevel2or4Secure() || islevel3Secure();
  }

  public boolean isEnabled() {
      return true;
  }

  public void setEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
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