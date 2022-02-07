// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.raw.RawFrame;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimbCommands.ManualMoveNextLevel;
import frc.robot.commands.ClimbCommands.ManualRotateChain;
import frc.robot.commands.DriverCommands.ArcadeDrive;
import frc.robot.commands.IntakeCommands.JointAndRoller;
import frc.robot.commands.IntakeCommands.ManualJoint;
import frc.robot.commands.IntakeCommands.ManualRoller;
import frc.robot.subsystems.Climb;
import frc.robot.commands.ShooterCommands.ManualFeeder;
import frc.robot.commands.ShooterCommands.ManualShooter;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //joystick setup
  private Joystick d_joystick; 
  private Joystick o_joystick;

  //driver buttons:
  private JoystickButton RB_Driver; //open joint and rolling while pressed;
  private JoystickButton A_Driver; //feeder
  private JoystickButton B_Driver; //shooter
  //private JoystickButton RT_Driver; //boost
  //private JoystickButton LT_Driver; //slow


  //operator buttons:
  private JoystickButton B_Operator; //claw level 2;
  private JoystickButton X_Operator; //claw level 3;


  //subsystem
  private Climb climb;
  private Shooter shooter;
  private Intake intake;
  private Driver driver;


  public RobotContainer() {
    this.d_joystick = new Joystick(0);
    this.o_joystick = new Joystick(1);

    buildSubsystems();
    
    buildButtons();
    
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    
    this.A_Driver.whileHeld(new ManualFeeder(shooter,Constants.ShooterConstants.FeederSpeed));
    this.B_Driver.whileHeld(new ManualShooter(shooter,Constants.ShooterConstants.ShooterSpeed));
    B_Operator.whenPressed(new ManualMoveNextLevel(climb, 3));
    X_Operator.whenPressed(new ManualMoveNextLevel(climb, 2));
    
    RB_Driver.whileHeld(new JointAndRoller(intake));
    climb.setDefaultCommand(new ManualRotateChain(climb, () -> o_joystick.getRawAxis(XboxController.Axis.kRightX.value)));
    driver.setDefaultCommand(new ArcadeDrive(driver, () -> d_joystick.getRawAxis(4) ,() -> d_joystick.getRawAxis(1)));
  
  }

  private void buildSubsystems(){
    this.climb = new Climb(); 
    this.intake = new Intake();
    this.shooter = new Shooter();
    this.driver = new Driver();
  }

  private void buildButtons(){
    this.RB_Driver = new JoystickButton(d_joystick, XboxController.Button.kRightBumper.value);
    this.A_Driver = new JoystickButton(d_joystick, XboxController.Button.kA.value);
    this.B_Driver = new JoystickButton(d_joystick, XboxController.Button.kB.value);
    
    this.B_Operator = new JoystickButton(o_joystick, 1);
    this.X_Operator = new JoystickButton(o_joystick, 1); 
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
