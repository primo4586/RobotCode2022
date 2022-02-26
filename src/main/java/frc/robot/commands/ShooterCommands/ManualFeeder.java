// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class ManualFeeder extends CommandBase {
  /** Creates a new ManualFeeder. */
  private Feeder feeder;
  private NetworkTableEntry feederSpeed;
  
  public ManualFeeder(Feeder feeder, double feederSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.feeder = feeder;
    this.feederSpeed = this.feeder.getTab().addEntry("Feeder Speed");
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putNumber("Feeder Speed", 0);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.feeder.f_control(this.feederSpeed.getDouble(0));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.feeder.f_control(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}