// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.InterpolateUtil;
import vision.Limelight;

public class AutoShooter extends CommandBase {
  /** Creates a new AutoShooter. */
  private Shooter shooter;
  private PistonForFeeder piston;
  private Intake intake;
  private Feeder feeder;
  private Limelight limelight;
  private double speed;

  public AutoShooter(Shooter shooter, PistonForFeeder pistonForFeeder, Intake intake, Feeder feeder,
      Limelight limelight) {
    this.shooter = shooter;
    this.piston = pistonForFeeder;
    this.limelight = limelight;
    this.intake = intake;
    this.feeder = feeder;

    addRequirements(shooter);
    addRequirements(intake);
    addRequirements(feeder);
    addRequirements(piston);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.speed = InterpolateUtil.interpolate(ShooterConstants.SHOOTER_VISION_MAP, limelight.getAverageDistance());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    feeder.setVoltage(ShooterConstants.FeederVoltage);

    shooter.setVelocity(speed);
    if (shooter.isReadyToShoot()) {
      piston.setSolenoid(true);
      intake.r_control(0.3);
    } 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    piston.setSolenoid(false);
    shooter.s_control(0);
    intake.r_control(0);
    feeder.f_control(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
