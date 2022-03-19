// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driver;
import vision.Limelight;

public class DriveUntilLimelightDistance extends CommandBase {

  private Driver driver;
  private Limelight limelight;

  private double speed, targetDistance, tolerance;

  

  /** Creates a new DriveUntilLimelightDistance. */
  public DriveUntilLimelightDistance(Driver driver, Limelight limelight, double speed, double distance, double tolerance) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driver = driver;
    this.limelight = limelight;
    this.speed = speed;
    this.targetDistance = distance;
    this.tolerance = tolerance;
    addRequirements(driver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driver.driveVelocity(0.5, 0.5);  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driver.d_control(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return limelight.isVisible() && limelight.getDistance() < targetDistance;
    // return Math.abs(targetDistance - limelight.getDistance()) <= tolerance;
  }
}
