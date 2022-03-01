// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;

public class TogglePistonAndRoller extends CommandBase {
  /** Creates a new TogglePiston. */
  PistonForFeeder piston;
  Intake intake;
  public TogglePistonAndRoller(PistonForFeeder piston, Intake intake) {
    this.piston = piston;
    this.intake = intake;
    addRequirements(piston);
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.r_control(-IntakeConstants.rollerSpeed);
    System.out.println("PISTON");
    piston.solenoidControll();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    piston.solenoidControll();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
