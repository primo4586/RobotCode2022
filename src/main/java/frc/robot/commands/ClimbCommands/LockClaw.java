package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimbConstants;
import frc.robot.subsystems.Climb;

public class LockClaw extends CommandBase {
  private Climb climb;
  private boolean isOk;
  private int numLevel; //the level the robot moving to 
  
  public LockClaw(Climb climb, int numLevel) {
    this.climb = climb;
    this.numLevel = numLevel;

    addRequirements(climb);
  }

  @Override
  public void initialize() {
    if(numLevel ==2 || numLevel == 4)
      this.isOk = climb.isMot2or4In();
    else if(numLevel == 3) 
      this.isOk = climb.isMot3In();
  }

  @Override
  public void execute() {
    if(isOk && climb.isEnabled()){
        if(numLevel == 2 || numLevel == 4)
            climb.setSolenoidLevel2or4(ClimbConstants.PISTON_LOCKED); 
        else if(numLevel == 3)
          climb.setSolenoidLevel3(ClimbConstants.PISTON_LOCKED);
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
