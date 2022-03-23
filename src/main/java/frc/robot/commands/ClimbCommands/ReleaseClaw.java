// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climb;

public class ReleaseClaw extends CommandBase {
  private Climb climb;
  private int numLevel; // the level the robot moving to

  public ReleaseClaw(Climb climb, int numLevel) {
    this.climb = climb;
    this.numLevel = numLevel;

    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (climb.isEnabled()) {
      if (this.numLevel == 2 && climb.islevel3Secure()) {
        climb.setSolenoidLevel2or4(Constants.ClimbConstants.PISTON_RELEASE);
      }
      if (this.numLevel == 3 && climb.islevel2or4Secure())
        climb.setSolenoidLevel3(Constants.ClimbConstants.PISTON_RELEASE);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
