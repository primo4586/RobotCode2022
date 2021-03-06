// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import PrimoLib.PrimoShuffleboard;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.AlignConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.ClimbCommands.ManualClawA;
import frc.robot.commands.ClimbCommands.ManualClawB;
import frc.robot.commands.ClimbCommands.ManualRotateChain;
import frc.robot.commands.DriverCommands.ArcadeDrive;
import frc.robot.commands.DriverCommands.RumbleJoystick;
import frc.robot.commands.IntakeCommands.ManualJoint;
import frc.robot.commands.IntakeCommands.ManualRoller;
import frc.robot.commands.ShooterCommands.AngleAutoShooter;
import frc.robot.commands.ShooterCommands.AutoShooter;
import frc.robot.commands.ShooterCommands.ManualFeeder;
import frc.robot.commands.ShooterCommands.ManualShooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

public class RobotContainer {

  // Joystick Setup
  private Joystick d_joystick;
  private Joystick o_joystick;

  // Driver Buttons:
  private JoystickButton B_Driver; // Auto-shooting
  private JoystickButton Y_Driver; // Reverse roller 
  private JoystickButton RB_Driver; // Change drivetrain direction & change cameras
  private JoystickButton LB_Driver; // Open Intake Joint & Spin roller
  private JoystickButton X_Driver; // Auto-shooting only within range
  private JoystickButton A_Driver; // open piston and spin roller
  private Trigger LT_TRIGGER_Driver;

  // Opeartor Buttons:
  private JoystickButton START_Operator; // Enable/Disable Climb Control
  private JoystickButton B_Operator; // Release level 2
  private JoystickButton X_Operator; // Release level 3
  private JoystickButton RB_Operator; // Manual Control A side (2&4)
  private JoystickButton LB_Operator; // Manual Control B side (3)
  private JoystickButton Y_Operator; // Manual Shooter
  

  // subsystem
  private Climb climb;
  private Shooter shooter;
  private Feeder feeder;
  private Intake intake;
  private Driver driver;
  private PistonForFeeder pistonForFeeder;
  private Limelight limelight;

  private UsbCamera forward;
  private UsbCamera backward;
  private CameraHandler camHandler;

  public RobotContainer(Driver driver, Shooter shooter, Feeder feeder, Intake intake, Climb climb,
      PistonForFeeder pistonForFeeder, Limelight limelight) {
    this.d_joystick = new Joystick(0);
    this.o_joystick = new Joystick(1);

    this.driver = driver;
    this.climb = climb;
    this.shooter = shooter;
    this.feeder = feeder;
    this.intake = intake;
    this.pistonForFeeder = pistonForFeeder;
    this.limelight = limelight;

    buildButtons();

    buildCameras();

    configureButtonBindings();

    PrimoShuffleboard.getInstance().buildCompetitionTab();
  }

  private void buildButtons() {
    this.LB_Driver = new JoystickButton(d_joystick, XboxController.Button.kLeftBumper.value);
    this.Y_Driver = new JoystickButton(d_joystick, XboxController.Button.kY.value);
    this.RB_Driver = new JoystickButton(d_joystick, XboxController.Button.kRightBumper.value);
    this.LB_Driver = new JoystickButton(d_joystick, XboxController.Button.kLeftBumper.value);
    this.X_Driver = new JoystickButton(d_joystick, XboxController.Button.kX.value);
    this.A_Driver = new JoystickButton(d_joystick, XboxController.Button.kA.value);
    this.B_Driver = new JoystickButton(d_joystick, XboxController.Button.kB.value);
    this.LT_TRIGGER_Driver = new Trigger(() -> d_joystick.getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.3);

    this.START_Operator = new JoystickButton(o_joystick, XboxController.Button.kStart.value);
    this.B_Operator = new JoystickButton(o_joystick, XboxController.Button.kB.value);
    this.X_Operator = new JoystickButton(o_joystick, XboxController.Button.kX.value);
    this.Y_Operator = new JoystickButton(o_joystick, XboxController.Button.kY.value);
    this.RB_Operator = new JoystickButton(o_joystick, XboxController.Button.kRightBumper.value);
    this.LB_Operator = new JoystickButton(o_joystick, XboxController.Button.kLeftBumper.value);
  }

  private void configureButtonBindings() {
    
    /*
      Drivetrain Controls
    */
    driver.setDefaultCommand(new ArcadeDrive(driver, () -> d_joystick.getRawAxis(XboxController.Axis.kLeftY.value),
        () -> d_joystick.getRawAxis(XboxController.Axis.kRightX.value),
        () -> false,
        () -> d_joystick.getRawAxis(XboxController.Axis.kRightTrigger.value) > 0));

    this.RB_Driver.whenPressed(new InstantCommand(() -> {
      driver.changeDirection();
      camHandler.switchCamera();   
    }, driver));
    
    RumbleJoystick rumble = new RumbleJoystick(d_joystick, () -> limelight.getDistance() * 0.1);

    /*
      Shooter Controls
    */
    B_Driver.whileHeld(new AutoShooter(shooter, pistonForFeeder, intake,feeder,limelight,() -> true));
    A_Driver.whileHeld(new AutoShooter(shooter, pistonForFeeder, intake, feeder, () -> ShooterConstants.ShooterSpeed));
    X_Driver.whileHeld(new SequentialCommandGroup(rumble.until(() ->  shooter.isWithInRange(limelight.getDistance()) && limelight.isVisible()), 
        new AutoShooter(shooter, pistonForFeeder, intake, feeder, limelight, () -> true)));
    
 
    LT_TRIGGER_Driver.whileActiveOnce(new SequentialCommandGroup(rumble.until(() ->  shooter.isWithInRange(limelight.getDistance()) && limelight.isVisible()),
        new AngleAutoShooter(shooter, pistonForFeeder, intake, feeder, limelight, AlignConstants.ALIGN_TOLERANCE_ANGLE)));

    Y_Operator.whileHeld(new ParallelCommandGroup(new ManualShooter(shooter, () -> ShooterConstants.ShooterSpeed),new ManualFeeder(feeder)));


    /*
      Intake Controls
    */
    LB_Driver.whenPressed(new ManualJoint(intake));
    Y_Driver.whileHeld(new ManualRoller(intake, -Constants.IntakeConstants.rollerSpeed)); // outaking "plita" (spinning roller in reverse)
    this.intake.setDefaultCommand(new ManualRoller(intake, Constants.IntakeConstants.rollerSpeed));


    /*
      Climb Controls
    */
    START_Operator.whenPressed(new InstantCommand(() -> climb.setEnabled(!climb.isEnabled()), climb));
    RB_Operator.whenPressed(new ManualClawA(climb));
    LB_Operator.whenPressed(new ManualClawB(climb));

    B_Operator.whenPressed(new ManualClawA(climb));
    X_Operator.whenPressed(new ManualClawB(climb));
    climb.setDefaultCommand(new ManualRotateChain(climb, () -> o_joystick.getRawAxis(XboxController.Axis.kRightY.value)));
  }

  private void buildCameras() {
    this.forward = CameraServer.startAutomaticCapture("Forward", 0);
    this.backward = CameraServer.startAutomaticCapture("Backward", 1);

    this.camHandler = new CameraHandler(forward, backward);
    PrimoShuffleboard.getInstance().updateCameras(camHandler);
  }

  public CameraHandler getCamHandler() {
      return camHandler;
  }
}
