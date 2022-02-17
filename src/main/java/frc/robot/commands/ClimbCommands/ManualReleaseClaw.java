// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ManualReleaseClaw extends CommandBase {
  private Climb climb;
  private boolean isOk;
  private int numLevel; //the level the robot moving to 
  
  public ManualReleaseClaw(Climb climb, int numLevel) {
    this.climb = climb;
    this.numLevel = numLevel;

    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(numLevel == 2)
      this.isOk = climb.islevel3Secure();
    
      else if(numLevel == 3)
        this.isOk = climb.islevel4Secure();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(this.isOk){
      if(numLevel==2)
        climb.setSolenoidLevel2or4State(false);

      else if(numLevel == 3)
        climb.setSolenoidLevel3State(false);
      
    }
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
