// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import java.util.concurrent.Delayed;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class JointAndRoller extends CommandBase {
  /** Creates a new JointAndRoller. */
  private Intake intake;
  private boolean state;
  private Timer timer;
  private BooleanSupplier shouldRoll;    

  public JointAndRoller(Intake intake) {
    this.intake = intake;
    this.timer = new Timer();
    // this.shouldRoll = shouldRoll;
    // this.isFinished = false;
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Start");
    // this.state = intake.isJointOpen();
    System.out.println("State: " + intake.isJointOpen());
    intake.setJoint(!intake.isJointOpen());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    intake.r_control(IntakeConstants.rollerSpeed);
    // if(intake.isJointOpen())  
    //   this.intake.r_control(Constants.IntakeConstants.rollerSpeed);
    // System.out.println("Command state: " + intake.isJointOpen());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("End state: " + intake.isJointOpen());
    intake.r_control(0);
    this.intake.setJoint(false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
