// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RumbleJoystick extends CommandBase {

  private Joystick driverController;
  /** Creates a new RumbleJoystick. */
  public RumbleJoystick(Joystick driverController) {
    this.driverController = driverController;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driverController.setRumble(RumbleType.kLeftRumble, 1);
    driverController.setRumble(RumbleType.kRightRumble, 1);
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
