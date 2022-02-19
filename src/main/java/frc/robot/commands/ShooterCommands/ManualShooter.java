// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ManualShooter extends CommandBase {
  /** Creates a new ManualShooter. */
  private Shooter shooter;
  private double shooterSpeed;
  
  public ManualShooter(Shooter shooter, Double shooterSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
    this.shooterSpeed = shooterSpeed;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.s_PIDControl(shooterSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.s_PIDControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
