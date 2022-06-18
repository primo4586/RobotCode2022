package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climbRight;
  private WPI_TalonFX m_climbleft;

  private Solenoid solenoidA; // Side A
  private Solenoid solenoidB; // Side B

  private boolean aSideState;
  private boolean bSideState;

  // piston sensors:
  private DigitalInput sPistonA; // Right A
  private DigitalInput sPistonB; // Left B

  private boolean isEnabled;

  public Climb() {
    this.m_climbRight = new WPI_TalonFX(ClimbConstants.rightMotorPort);
    this.m_climbleft = new WPI_TalonFX(ClimbConstants.leftMotorPort);
    this.m_climbleft.setInverted(true);


    this.solenoidA = new Solenoid(Constants.Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM,
        Constants.Pneumatics.climbSolenoidA);
    this.solenoidB = new Solenoid(Constants.Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM,
        Constants.Pneumatics.climbSolenoidB);


    this.sPistonA = new DigitalInput(ClimbConstants.sPistonAport);
    this.sPistonB = new DigitalInput(ClimbConstants.sPistonBport);


    this.isEnabled = false;
    this.aSideState = ClimbConstants.PISTON_LOCKED;
    this.bSideState = ClimbConstants.PISTON_LOCKED;
  }

  public boolean getASideState() {
    return aSideState;
  }

  public boolean getBSideState() {
    return bSideState;
  }

  public void c_control(double speed) {
    /*
     * Gets speed and set data to motor
     */
  
    m_climbRight.set(speed);
    m_climbleft.set(speed);
  }

  public void setVoltage(double voltage){
    m_climbRight.setVoltage(voltage);
    m_climbleft.setVoltage(voltage);
  }


  public double getAbsoluteSpeed() {
    return Math.abs(m_climbRight.get());
  }

  public void enableClimb() {
    this.isEnabled = true;
    this.solenoidA.set(Constants.ClimbConstants.PISTON_LOCKED);
    this.solenoidA.set(Constants.ClimbConstants.PISTON_LOCKED);
  }

  public void setSolenoidLevel2or4(boolean state) {
    /*
     * sets the first to climb piston state
     */
    this.solenoidA.set(state);
    aSideState = state;
  }

  public void setSolenoidLevel3(boolean state) {
    /*
     * sets the second to climb piston state
     */
    this.solenoidB.set(state);
    bSideState = state;
  }


  public boolean isClawLockOn2or4() {
    return !this.sPistonA.get();
  }

  public boolean isClawLockOn3() {
    return !this.sPistonB.get();
  }

  public boolean islevel2or4Secure() {
    return isClawLockOn2or4();
  }

  public boolean islevel3Secure() {
    return isClawLockOn3();
  }

  public boolean isHang() {
    return islevel2or4Secure() || islevel3Secure();
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
    if(isEnabled)
      enableClimb();
  }


  @Override
  public void periodic() {
    
  }

}
