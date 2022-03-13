// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.leds.LEDs;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  // Containers
  private RobotContainer robotContainer;
  private AutonomousContainer autoContainer;
  
  // Vision
  private Limelight limelight;

  // Subsystems
  private Intake intake;
  private Shooter shooter;
  private Feeder feeder;
  private Driver driver;
  private Climb climb;
  private PistonForFeeder pistonForFeeder;
  
  private boolean compFlash = false;
  private Timer flashTimer;

  // private AddressableLED leds;
  // private AddressableLEDBuffer ledBuffer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    this.intake = new Intake();
    this.shooter = new Shooter();
    this.feeder = new Feeder();
    this.driver = new Driver();
    this.climb = new Climb();
    this.pistonForFeeder = new PistonForFeeder();

    this.flashTimer = new Timer();

    limelight = new Limelight();

    robotContainer = new RobotContainer(driver,shooter,feeder,intake,climb, pistonForFeeder,limelight);
    autoContainer = new AutonomousContainer(driver, shooter, feeder, intake, climb, pistonForFeeder,limelight);

    // leds = new AddressableLED(0);
    // ledBuffer = new AddressableLEDBuffer(10);
    // leds.setLength(ledBuffer.getLength());
    // for(int i = 0; i < ledBuffer.getLength(); i++) {
    //   ledBuffer.setRGB(i, 255, 0, 0);
    // }
    // leds.setData(ledBuffer);
    // leds.setData(ledBuffer);
    
    // leds.start();
    // LiveWindow.disableAllTelemetry();
 
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    Shuffleboard.update();
    limelight.update();
 
    // LEDs.getInstance().update();
  }


  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = autoContainer.getSelectedCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    // LEDs.getInstance().setClimbBarsEffect(new FlashColor(LEDColor.RED,0.5));
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // LEDs.getInstance().setClimbBarsEffect(new StaticColor(LEDColor.PRIMO_BLUE));
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    flashTimer.start();
    PrimoShuffleboard.getInstance().getCompetitonBoard().addEntry("Climb Alert").forceSetBoolean(true);
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if(Timer.getMatchTime() <= 40 && Timer.getMatchTime() > 30) {
      if(flashTimer.hasElapsed(0.5)) {
        PrimoShuffleboard.getInstance().getCompetitonBoard().addEntry("Climb Alert").forceSetBoolean(compFlash);
        compFlash = !compFlash;
        flashTimer.reset();
      }
    } 
    else if(Timer.getMatchTime() <= 30)
       {
        PrimoShuffleboard.getInstance().getCompetitonBoard().addEntry("Climb Alert").forceSetBoolean(false);
       } 
        // LEDs.getInstance().setClimbBarsEffect(new FlashColor(LEDColor.FLAME_ORANGE,0.5));
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
