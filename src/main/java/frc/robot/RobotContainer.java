// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.ClimbCommands.ManualClawA;
import frc.robot.commands.ClimbCommands.ManualClawB;
import frc.robot.commands.ClimbCommands.ManualRotateChain;
import frc.robot.commands.ClimbCommands.ReleaseClaw;
import frc.robot.commands.DriverCommands.ArcadeDrive;
import frc.robot.commands.IntakeCommands.JointAndRoller;
import frc.robot.commands.IntakeCommands.ManualJoint;
import frc.robot.commands.IntakeCommands.ManualRoller;
import frc.robot.commands.IntakeCommands.TogglePistonAndRoller;
import frc.robot.commands.ShooterCommands.ManualFeeder;
import frc.robot.commands.ShooterCommands.ManualShooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */

public class RobotContainer {
  // joystick setup
  private Joystick d_joystick;
  private Joystick o_joystick;

  // driver buttons:
  private JoystickButton B_Driver; // open and close feeder piston 
  private JoystickButton Y_Driver; // spin rollers backwards
  private JoystickButton X_Driver; //change state of intake joint
  private JoystickButton RB_Driver; // change direction
  private JoystickButton LB_Driver; //open and roolig roller

  // operator buttons:
  private JoystickButton A_Operator;
  private JoystickButton B_Operator; 
  private JoystickButton X_Operator; 
  private JoystickButton Y_Operator; 
  private JoystickButton RB_Operator;
  private JoystickButton LB_Operator;


  private JoystickButton START_Operator; // Enable/Disable Climb Control


  // subsystem
  private Climb climb;
  private Shooter shooter;
  private Feeder feeder;
  private Intake intake;
  private Driver driver;
  private PistonForFeeder pistonForFeeder;

  private PrimoTab tab;

  private UsbCamera forward;
  private UsbCamera backward;
  private CameraHandler camHandler;

  public RobotContainer(Driver driver, Shooter shooter, Feeder feeder, Intake intake, Climb climb, PistonForFeeder pistonForFeeder) {
    this.d_joystick = new Joystick(0);
    this.o_joystick = new Joystick(1);

    this.driver = driver;
    this.climb = climb;
    this.shooter = shooter;
    this.feeder = feeder;
    this.intake = intake;
    this.pistonForFeeder = pistonForFeeder;
    

    buildButtons();

    buildCameras();

    configureButtonBindings();

    PrimoShuffleboard.getInstance().buildCompetitionTab();
  }

  private void buildButtons() {
    
    this.LB_Driver = new JoystickButton(d_joystick, XboxController.Button.kLeftBumper.value);
    this.Y_Driver = new JoystickButton(d_joystick, XboxController.Button.kY.value);
    this.RB_Driver = new JoystickButton(d_joystick, XboxController.Button.kRightBumper.value);
    // this.A_Driver = new JoystickButton(d_joystick, XboxController.Button.kA.value);
    this.B_Driver = new JoystickButton(d_joystick, XboxController.Button.kB.value);
    this.X_Driver = new JoystickButton(d_joystick, XboxController.Button.kX.value);

    this.LB_Driver = new JoystickButton(d_joystick, XboxController.Button.kLeftBumper.value);
    this.START_Operator = new JoystickButton(o_joystick, XboxController.Button.kStart.value);
    this.A_Operator = new JoystickButton(o_joystick, XboxController.Button.kA.value);
    this.B_Operator = new JoystickButton(o_joystick, XboxController.Button.kB.value);
    this.X_Operator = new JoystickButton(o_joystick, XboxController.Button.kX.value);
    this.Y_Operator = new JoystickButton(o_joystick, XboxController.Button.kY.value);
    this.RB_Operator= new JoystickButton(o_joystick, XboxController.Button.kRightBumper.value);
    this.LB_Operator = new JoystickButton(o_joystick, XboxController.Button.kLeftBumper.value);
  }

  private void configureButtonBindings() {

    this.RB_Driver.whenPressed(new InstantCommand(() -> {
      driver.changeDirection();
      camHandler.switchCamera();
    }));

    // // intake


    // LB_Driver.whileHeld(new JointAndRoller(intake));
    LB_Driver.whenPressed(new ManualJoint(intake));
    this.intake.setDefaultCommand(new ManualRoller(intake, Constants.IntakeConstants.rollerSpeed));
    Y_Driver.whileHeld(new ManualRoller(intake, -Constants.IntakeConstants.rollerSpeed)); //plita
    // this.X_Driver.whenPressed(new ManualJoint(intake));

    //for testing and operating without sensors
     
    climb.setDefaultCommand(new ManualRotateChain(climb, () ->
     o_joystick.getRawAxis(XboxController.Axis.kRightY.value),true));

    //
    driver.setDefaultCommand(new ArcadeDrive(driver, () -> d_joystick.getRawAxis(XboxController.Axis.kLeftY.value),
    () -> d_joystick.getRawAxis(XboxController.Axis.kRightX.value),
    () -> d_joystick.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0,
    () -> d_joystick.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0));

// shooter:
  this.B_Driver.whileHeld(new TogglePistonAndRoller(pistonForFeeder,intake,camHandler));
  Y_Operator.whileHeld(new ParallelCommandGroup(new ManualShooter(shooter, ShooterConstants.ShooterSpeed),
  new ManualFeeder(feeder)));
// intake

// climb:

//for testing and operating without sensors
 
 RB_Operator.whenPressed(new ManualClawA(climb));
 LB_Operator.whenPressed(new ManualClawB(climb));
  
 B_Operator.whenPressed(
    new ReleaseClaw(climb, 2)); //open level 2 or
  X_Operator.whenPressed(new ReleaseClaw(climb, 3)); //open level 3
  A_Operator.whenPressed(new InstantCommand(() -> climb.setBrake(!climb.isBrake()), climb));
  //A_Operator.whenPressed(new ManualJoint(intake));
  START_Operator.whenPressed(new InstantCommand(() -> climb.setEnabled(!climb.isEnabled())));
  }


  private void buildCameras() {

    this.forward = CameraServer.startAutomaticCapture("Forward", 0);
    this.backward = CameraServer.startAutomaticCapture("Backward", 1);

    this.camHandler = new CameraHandler(forward,backward);
  }

}
