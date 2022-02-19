// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import PrimoLib.PrimoTab;
import autonomous.CommandSelector;
import autonomous.PathHandler;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimbCommands.ManualRotateChain;
import frc.robot.commands.DriverCommands.ArcadeDrive;
import frc.robot.commands.IntakeCommands.ManualRoller;
import frc.robot.commands.ShooterCommands.ManualFeeder;
import frc.robot.commands.ShooterCommands.ManualShooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
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
  private JoystickButton A_Driver; // feeder
  private JoystickButton B_Driver; // shooter
  private JoystickButton LB_Driver; // change direction

  // operator buttons:
  private JoystickButton B_Operator; // claw level 2;
  private JoystickButton X_Operator; // claw level 3;
  private JoystickButton START_Operator; // Enable/Disable Climb Control

  // subsystem
  private Climb climb;
  private Shooter shooter;
  private Feeder feeder;
  private Intake intake;
  private Driver driver;

  private PrimoTab tab;

  private UsbCamera forward;
  private UsbCamera backward;
  private CameraHandler camHandler;

  public RobotContainer(Driver driver, Shooter shooter, Feeder feeder, Intake intake, Climb climb) {
    this.d_joystick = new Joystick(0);
    this.o_joystick = new Joystick(1);

    buildButtons();

    configureButtonBindings();

    buildCameras();

  }

  

  private void buildButtons() {
    this.RB_Driver = new JoystickButton(d_joystick, XboxController.Button.kRightBumper.value);
    this.A_Driver = new JoystickButton(d_joystick, XboxController.Button.kA.value);
    this.B_Driver = new JoystickButton(d_joystick, XboxController.Button.kB.value);
    this.LB_Driver = new JoystickButton(d_joystick, XboxController.Button.kLeftBumper.value);
    this.START_Operator = new JoystickButton(o_joystick, XboxController.Button.kStart.value);
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
    this.A_Driver.whileHeld(new ManualFeeder(feeder, Constants.ShooterConstants.FeederSpeed));
    this.B_Driver.whileHeld(new ManualShooter(shooter, Constants.ShooterConstants.ShooterSpeed));

    // intake
    RB_Driver.whileHeld(new ManualRoller(intake));

    // climb:
    /*
     * B_Operator.whenPressed(new ManualMoveNextLevel(climb, 3));
     * X_Operator.whenPressed(new ManualMoveNextLevel(climb, 2));
     * climb.setDefaultCommand(new ManualRotateChain(climb, () ->
     * o_joystick.getRawAxis(XboxController.Axis.kRightX.value)));
     */
    START_Operator.whenPressed(new InstantCommand(() -> climb.setEnabled(!climb.isEnabled()), climb));
  }


  private void buildCameras() {

    this.forward = new UsbCamera("Forward Camera", 0);
    this.backward = new UsbCamera("Backward Camera", 0);

    this.camHandler = new CameraHandler(forward, backward);
  }

}
