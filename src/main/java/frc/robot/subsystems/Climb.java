package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ClimbConstants;
import frc.robot.Constants.Pneumatics;

public class Climb extends SubsystemBase {

  private WPI_TalonFX m_climbRight;
  private WPI_TalonFX m_climbleft;

  private Solenoid solenoidA; // Side A
  private Solenoid solenoidB; // Side B

  // piston sensors:
  private DigitalInput sPistonA; // Right A
  private DigitalInput sPistonB; // Left B


  private PrimoTab tab;
  private boolean isEnabled;

  public Climb() {
    this.m_climbRight = new WPI_TalonFX(ClimbConstants.rightMotorPort);
    this.m_climbleft = new WPI_TalonFX(ClimbConstants.leftMotorPort);

    this.m_climbleft.setInverted(true);

    // this.compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

    this.solenoidA = new Solenoid(Constants.Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM,
        Constants.Pneumatics.climbSolenoidA);
    this.solenoidB = new Solenoid(Constants.Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM,
        Constants.Pneumatics.climbSolenoidB);

    // this.brakeSolenoid = new Solenoid(Pneumatics.pcmPort, PneumaticsModuleType.CTREPCM, 0);


    this.sPistonA = new DigitalInput(ClimbConstants.sPistonAport);
    this.sPistonB = new DigitalInput(ClimbConstants.sPistonBport);


    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Climb");

    this.isEnabled = false;

    // this.brake = brakeSolenoid.get();
    // this.m_climbleft.follow(this.m_climbRight);
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
    this.setSolenoidLevel2or4(ClimbConstants.PISTON_RELEASE);
    this.setSolenoidLevel3(ClimbConstants.PISTON_RELEASE);

    PrimoShuffleboard.getInstance().selectTab("Climb");
    this.isEnabled = true;
  }

  public void setSolenoidLevel2or4(boolean state) {
    /*
     * sets the first to climb piston state
     */
    this.solenoidB.set(state);
  }

  public void setSolenoidLevel3(boolean state) {
    /*
     * sets the second to climb piston state
     */
    this.solenoidA.set(state);
  }

  /* TO-DO: explain logic in comments, why do you need three functions? */

  public boolean isClawLockOn2or4() {
    return !this.sPistonB.get();
  }

  public boolean isClawLockOn3() {
    return !this.sPistonA.get();
  }

  public boolean islevel2or4Secure() {
    // return true if the claw close on the mot and its ok to move on
    // System.out.println("is mot 2 or 4 in:"+ isMot2or4In());
    // System.out.println("is claw lock "+ isClawLockOn2or4());
    return isClawLockOn2or4();
  }

  public boolean islevel3Secure() {
    // return true if the claw close on the mot and its ok to move on
    // System.out.println("Level 3 Mot: " + isMot3In());
    // System.out.println("Level 3 Claw: " + isClawLockOn3());
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
    if(isEnabled())
      enableClimb();
  }


  @Override
  public void periodic() {
    
  }

}
