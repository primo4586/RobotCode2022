package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ManualMoveNextLevel extends CommandBase {
  private Climb climb;
  private boolean isOk;
  private int numLevel; //the level the robot on 
  
  public ManualMoveNextLevel(Climb climb, int numLevel) {
    this.climb = climb;
    this.numLevel = numLevel;

    addRequirements(climb);
  }

  @Override
  public void initialize() {
    if(numLevel ==2)
      this.isOk = climb.islevel3Secure();
    else if(numLevel == 3) 
      this.isOk = climb.islevel4Secure();
  }

  @Override
  public void execute() {
    if(isOk){
        if(numLevel ==2)
          climb.setSolenoidLevel2State(true);
        else if(numLevel ==3)
          climb.setSolenoidLevel3State(true);
    }
    else{
      //shake controller
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
