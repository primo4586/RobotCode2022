package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimbConstants;
import frc.robot.subsystems.Climb;

public class ManualLockClaw extends CommandBase {
  private Climb climb;
  private boolean isOk;
  private int numLevel; //the level the robot moving to 
  
  public ManualLockClaw(Climb climb, int numLevel) {
    this.climb = climb;
    this.numLevel = numLevel;

    addRequirements(climb);
  }

  @Override
  public void initialize() {
    if(numLevel ==2 || numLevel == 4)
      this.isOk = climb.isMotInSideA();
    else if(numLevel == 3) 
      this.isOk = climb.isMotInSideB();
  }

  @Override
  public void execute() {
    if(isOk && climb.isEnabled()){
        if(numLevel == 2 || numLevel == 4)
            climb.setSolenoidSideA(ClimbConstants.PISTON_LOCKED); 
        else if(numLevel == 3)
          climb.setSolenoidSideB(ClimbConstants.PISTON_LOCKED);
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
