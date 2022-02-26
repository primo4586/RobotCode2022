// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import autonomous.PIDConfig;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class ManualFeeder extends CommandBase {
  /** Creates a new ManualFeeder. */
  private Feeder feeder;
  NetworkTableEntry Kp, Ki, Kd, Kf, setPoint, speed;

  
  public ManualFeeder(Feeder feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.feeder = feeder;
    addRequirements(feeder);

    Kp = this.feeder.getTab().addEntry("Feeder P");
    Ki = this.feeder.getTab().addEntry("Feeder I");
    Kd = this.feeder.getTab().addEntry("Feeder D");
    Kf = this.feeder.getTab().addEntry("Feeder F");

    setPoint = this.feeder.getTab().addEntry("setPoint");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.feeder.setConfig(new PIDConfig(Kp.getDouble(0), Ki.getDouble(0), Kd.getDouble(0), Kf.getDouble(0)));
    this.feeder.pidControl(setPoint.getDouble(0));
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