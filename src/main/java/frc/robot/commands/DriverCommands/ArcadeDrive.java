// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Driver;

public class ArcadeDrive extends CommandBase {
  /** Creates a new ArcadeDrive. */
  private Driver driver;
  private DoubleSupplier speed, rotation;
  private BooleanSupplier isSlowing, isBoosting;
  private double speedAsDouble, rotationAsDouble;

  public ArcadeDrive(Driver driver, DoubleSupplier speed, DoubleSupplier rotation, BooleanSupplier isSlowing, BooleanSupplier isBoosting) {
    this.driver = driver;
    this.speed = speed;
    this.rotation = rotation;
    this.isSlowing = isSlowing;
    this.isBoosting = isBoosting;

    addRequirements(driver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.speedAsDouble = this.speed.getAsDouble() * Constants.DriverConstants.SPEED_LIMITER;
    this.rotationAsDouble = this.rotation.getAsDouble() * Constants.DriverConstants.ROTATION_LIMITER;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    this.speedAsDouble = this.speed.getAsDouble() * Constants.DriverConstants.SPEED_LIMITER;
    this.rotationAsDouble = this.rotation.getAsDouble() * Constants.DriverConstants.ROTATION_LIMITER;
    
    if(isSlowing.getAsBoolean())
    {
      this.speedAsDouble *= Constants.DriverConstants.SLOW;
      this.rotationAsDouble *= Constants.DriverConstants.SLOW;
    } 
    else if(isBoosting.getAsBoolean())
      this.speedAsDouble *= Constants.DriverConstants.BOOST;
    
    driver.d_control(speedAsDouble, rotationAsDouble);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
