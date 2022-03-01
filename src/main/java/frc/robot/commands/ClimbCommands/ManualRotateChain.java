// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimbCommands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimbConstants;
import frc.robot.subsystems.Climb;

public class ManualRotateChain extends CommandBase {
  /** Creates a new ManualRotateChain. */
  private Climb climb;
  private boolean isOk, isStart;
  private DoubleSupplier speed;
  private int level;

  
  public ManualRotateChain(Climb climb, DoubleSupplier speed, boolean isStars) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climb = climb;
    this.speed= speed;
    this.isStart = isStars;
    

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
    if(isOk && climb.isEnabled()){
      // System.out.println("Climb moment");
      if(speed.getAsDouble() > 0.3) {
       this.climb.c_control(ClimbConstants.chainSpeed);
      }
      else if(speed.getAsDouble() < -0.3)
        this.climb.c_control(-ClimbConstants.chainSpeed);
      else 
        this.climb.c_control(0);
      
      if(climb.isMot2or4In()){
        if(climb.getCanSearch2or4()){
          climb.setSolenoidLevel2or4(ClimbConstants.PISTON_LOCKED);
          climb.setCanSearch2or4(false);
          climb.setLevel(climb.getLevel() + 1);
        }
      }
      else
        climb.setCanSearch2or4(true);
        

      if(climb.isMot3In()){
        if(climb.getCanSearch3()){
          climb.setSolenoidLevel3(ClimbConstants.PISTON_LOCKED);
          climb.setCanSearch3(false);
          climb.setLevel(climb.getLevel() + 1);
        }
      }
      else
      climb.setCanSearch3(true);
  
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
