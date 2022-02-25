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
  private boolean isOk, isStart, isMotIn;
  private DoubleSupplier speed;
  private int level;
  private boolean canSearch;

  
  public ManualRotateChain(Climb climb, DoubleSupplier speed, boolean isStars, boolean isMotIn) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climb = climb;
    this.speed= speed;
    this.isStart = isStars;
    this.isMotIn = isMotIn;
    this.canSearch = false;

    addRequirements(climb);
  }

  @Override
  public void initialize() {
    if(isStart)
      this.isOk = true;
    else
      this.isOk = climb.isHang();
          
    this.level = climb.getLevel();
    System.out.println("the level is: "+ this.level);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isOk){
      this.level = climb.getLevel();
      if(speed.getAsDouble() > 0.3) 
        this.climb.c_control(ClimbConstants.chainSpeed);
      else if(speed.getAsDouble() < -0.3)
        this.climb.c_control(-ClimbConstants.chainSpeed);
      else 
        this.climb.c_control(0);
      
      if(this.level == 2  && climb.isMot2or4In()){
        climb.setLevel(3);
        climb.setSolenoidLevel2or4(ClimbConstants.PISTON_LOCKED);
      }

      else if(this.level ==3 && climb.isMot3In()){
        climb.setLevel(4);
        climb.setSolenoidLevel3(ClimbConstants.PISTON_LOCKED);
      }
      
      else if(this.level==4 && climb.canSearch() && climb.isMot2or4In()){
        System.out.println("can search 2 : " + climb.canSearch());
        climb.setSolenoidLevel2or4(ClimbConstants.PISTON_LOCKED); 
      }
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
