// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimbCommands.ManualMoveNextLevel;
import frc.robot.commands.ClimbCommands.ManualRotateChain;
import frc.robot.subsystems.Climb;
import frc.robot.commands.ShooterCommands.ManualFeeder;
import frc.robot.commands.ShooterCommands.ManualShooter;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //joystick setup
  private Joystick joystick; 
  private JoystickButton B_OPERATOR;
  private JoystickButton X_OPERATOR;

  //subsystem
  private Climb climb;
  private Shooter shooter;

  private JoystickButton aButton = new JoystickButton(joystick,XboxController.Button.kA.value);
  private JoystickButton bButton =  new JoystickButton(joystick, XboxController.Button.kB.value);
 
  public RobotContainer() {
    this.joystick = new Joystick(0);
    //build subsystems
    this.climb = new Climb(); 
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    this.aButton.whileHeld(new ManualFeeder(shooter,Constants.ShooterConstants.FeederSpeed));
    this.bButton.whileHeld(new ManualShooter(shooter,Constants.ShooterConstants.ShooterSpeed));
    B_OPERATOR.whenPressed(new ManualMoveNextLevel(climb, 3));
    X_OPERATOR.whenPressed(new ManualMoveNextLevel(climb, 2));
    climb.setDefaultCommand(new ManualRotateChain(climb, () -> joystick.getRawAxis(XboxController.Axis.kRightX.value)));
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
