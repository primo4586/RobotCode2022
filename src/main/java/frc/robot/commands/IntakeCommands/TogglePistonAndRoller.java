// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.CameraHandler;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;

public class TogglePistonAndRoller extends CommandBase {
  /** Creates a new TogglePiston. */
  PistonForFeeder piston;
  Intake intake;
  CameraHandler camHandler;
  private int previousCam;

  public TogglePistonAndRoller(PistonForFeeder piston, Intake intake, CameraHandler camHandler) {
    this.piston = piston;
    this.camHandler = camHandler;
    this.intake = intake;
    addRequirements(piston);
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    previousCam = camHandler.getIndex();
    piston.setSolenoid(true);
    camHandler.setCamera(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.r_control(0.3);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    piston.setSolenoid(false);
    intake.r_control(0);
    camHandler.setCamera(previousCam);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
