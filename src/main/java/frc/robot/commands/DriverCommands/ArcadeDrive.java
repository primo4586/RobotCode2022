// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driver;

public class ArcadeDrive extends CommandBase {
  /** Creates a new ArcadeDrive. */
  private Driver driver;
  private DoubleSupplier speed,rotation;
  private double speedAsDouble, rotationAsDouble;

  public ArcadeDrive(Driver driver, DoubleSupplier speed, DoubleSupplier rotation) {
    this.driver = driver;
    this.speed = speed;
    this.rotation = rotation;

    addRequirements(driver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.speedAsDouble = this.speed.getAsDouble() *0.5;
    this.rotationAsDouble = this.rotation.getAsDouble() *0.7;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.speedAsDouble = this.speed.getAsDouble() *0.5;
    this.rotationAsDouble = this.rotation.getAsDouble() *0.7;

    if(!driver.isDirectionForward()){
      System.out.println("INNNNNNNNNNNNN");
      speedAsDouble *= -1.0;
      rotationAsDouble *= 1.0;
    }
    
    System.out.println("Direction forward: " + driver.isDirectionForward());

    driver.d_control(speedAsDouble, rotationAsDouble);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
