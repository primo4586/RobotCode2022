// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimbCommands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ClimbConstants;
import frc.robot.subsystems.Climb;

public class ManualRotateChain extends CommandBase {
  /** Creates a new ManualRotateChain. */
  private Climb climb;
  private boolean isOk;
  private DoubleSupplier speed;

  
  public ManualRotateChain(Climb climb, DoubleSupplier speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climb = climb;
    this.speed= speed;
    

    addRequirements(climb);
  }

  @Override
  public void initialize() {      
    this.isOk = climb.isEnabled();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(this.isOk){
      if(speed.getAsDouble() > 0.3) {
       this.climb.setVoltage(Constants.ClimbConstants.chainVoltage);
      }
      else if(speed.getAsDouble() < -0.3)
        this.climb.setVoltage(-Constants.ClimbConstants.chainVoltage);
      else 
        this.climb.c_control(0);
      
    }
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.climb.c_control(0);
    System.out.println("ROTATE CHAIN END!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return this.isMotIn;
    return false;
  }
}
