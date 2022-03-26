// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RumbleJoystick extends CommandBase {

  private Joystick driverController;
  private DoubleSupplier intensity;
  /** Creates a new RumbleJoystick. */
  public RumbleJoystick(Joystick driverController, DoubleSupplier intensity) {
    this.driverController = driverController;
    this.intensity = intensity;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driverController.setRumble(RumbleType.kLeftRumble, intensity.getAsDouble());
    driverController.setRumble(RumbleType.kRightRumble, intensity.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driverController.setRumble(RumbleType.kLeftRumble, 0);
    driverController.setRumble(RumbleType.kRightRumble, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
