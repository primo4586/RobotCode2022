// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import PrimoLib.PrimoTab;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimbCommands.ManualClawA;
import frc.robot.commands.ClimbCommands.ManualClawB;
import frc.robot.commands.ClimbCommands.ManualRotateChain;
import frc.robot.commands.ClimbCommands.ReleaseClaw;
import frc.robot.commands.DriverCommands.ArcadeDrive;
import frc.robot.commands.IntakeCommands.JointAndRoller;
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
  private JoystickButton RB_Driver; // rolling while pressed;
  private JoystickButton A_Driver; // open and close feeder piston
  private JoystickButton X_Driver;
  private JoystickButton B_Driver; // shooter

  private JoystickButton LB_Driver; // change direction

  // operator buttons:
  private JoystickButton B_Operator; // claw A;
  private JoystickButton A_Operator; // claw B;
  private JoystickButton X_Operator; // claw B;
  private JoystickButton Y_Operator; // claw B;
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

  private Command climbSequent;
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

    configureButtonBindings();

    buildCameras();

  }

  private void buildButtons() {
    this.RB_Driver = new JoystickButton(d_joystick, XboxController.Button.kRightBumper.value);
    this.A_Driver = new JoystickButton(d_joystick, XboxController.Button.kA.value);
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
    // driver:
    driver.setDefaultCommand(new ArcadeDrive(driver, () -> d_joystick.getRawAxis(XboxController.Axis.kLeftY.value),
        () -> d_joystick.getRawAxis(XboxController.Axis.kRightX.value),
        () -> d_joystick.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.2,
        () -> d_joystick.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.2));

    this.LB_Driver.whenPressed(new InstantCommand(() -> {
      driver.changeDirection();
      camHandler.switchCamera();
    }));

    // shooter:
    this.X_Driver.whenPressed(new InstantCommand(()-> pistonForFeeder.solenoidControll() , feeder));
    this.B_Driver.whileHeld(new ManualShooter(shooter));
    this.A_Driver.whileHeld(new ManualFeeder(feeder));

    // intake
    RB_Driver.whileHeld(new JointAndRoller(intake));

    // climb:

    //for testing and operating without sensors
    
     RB_Operator.whenPressed(new ManualClawA(climb));
     LB_Operator.whenPressed(new ManualClawB(climb));
     

    //  A_Operator.whenPressed(new LockClaw(climb, 2));// close level 2 or 4
    //  B_Operator.whenPressed(new LockClaw(climb, 3)); //close level 3
     
    A_Operator.whenPressed(new ReleaseClaw(climb, 2)); //open level 2 or
     B_Operator.whenPressed(new ReleaseClaw(climb, 3)); //open level 3

     
    climb.setDefaultCommand(new ManualRotateChain(climb, () ->
     o_joystick.getRawAxis(XboxController.Axis.kRightY.value),true));

    //

    

    
     
    //START_Operator.whenPressed(new InstantCommand(() -> climb.setEnabled(!climb.isEnabled()), climb));
  }


  private void buildCameras() {

    this.forward = CameraServer.startAutomaticCapture("Forward", 0);
    this.backward = CameraServer.startAutomaticCapture("Backward", 1);

    this.camHandler = new CameraHandler(forward,backward);
  }

}
