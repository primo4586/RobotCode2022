// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ManualFeeder extends CommandBase {
  /** Creates a new ManualFeeder. */
  private Shooter shooter;
  private double feederSpeed;
  public ManualFeeder(Shooter shooter, double feederSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
    this.feederSpeed = feederSpeed;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.shooter.setFeederSpeed(this.feederSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.shooter.setFeederSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}