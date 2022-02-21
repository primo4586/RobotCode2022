// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimbCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climb;

public class ManualRotateChain extends CommandBase {
  /** Creates a new ManualRotateChain. */
  private Climb climb;
  private boolean isOk, isStart, isMotIn;
  private DoubleSupplier speed;

  
  public ManualRotateChain(Climb climb, DoubleSupplier speed, boolean isStars, boolean isMotIn) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climb = climb;
    this.speed= speed;
    this.isStart = isStars;
    this.isMotIn = isMotIn;

    addRequirements(climb);
  }

  @Override
  public void initialize() {
    if(isStart)
      this.isOk = true;
    else
      this.isOk = climb.isHang();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isOk && Math.abs(speed.getAsDouble())>0.2) 
      this.climb.c_control(Constants.ClimbConstants.chainSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.climb.c_control(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.isMotIn;
  }
}
