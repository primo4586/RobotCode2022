// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class ManualShoot extends CommandBase {
  /** Creates a new ManualShoot. */

  private Feeder feeder;
  private Shooter shooter;

  private double feederSpeed;
  private double shooterSpeed;


  public ManualShoot(Feeder feeder,Shooter shooter,double feederSpeed, double shooterSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.feeder = feeder;
    this.shooter = shooter;

    this.feederSpeed = feederSpeed;
    this. shooterSpeed = shooterSpeed;

    addRequirements(shooter);
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    feeder.setSpeed(feederSpeed);
    shooter.setSpeed(shooterSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setSpeed(0);
    feeder.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
