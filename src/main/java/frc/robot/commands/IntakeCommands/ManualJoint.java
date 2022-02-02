package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ManualJoint extends CommandBase {
  /** Creates a new ManualJoint. */
  private Intake intake;
  private boolean prevState; //for the change of state

  //to this command we need to add sensor for knowing if open or closed just in case

  public ManualJoint(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = intake;

    addRequirements(intake);
  }

  @Override
  public void initialize() {
    prevState = intake.isJointOpen();
  }

  @Override
  public void execute() {
    this.intake.setJointState(!prevState);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
