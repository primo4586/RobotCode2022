// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.InterpolateUtil;
import vision.Limelight;

public class AngleAutoShooter extends CommandBase {
  /** Creates a new AutoShooter. */
  private Shooter shooter;
  private PistonForFeeder piston;
  private Intake intake;
  private Feeder feeder;
  private Limelight limelight;

  private BooleanSupplier isAligned;
  private double speed;

  public AngleAutoShooter(Shooter shooter, PistonForFeeder pistonForFeeder, Intake intake, Feeder feeder,
      Limelight limelight, double angleTolerance) {
    this.shooter = shooter;
    this.piston = pistonForFeeder;
    this.limelight = limelight;
    this.intake = intake;
    this.feeder = feeder;

    this.isAligned = () -> Math.abs(limelight.getAngleX()) <= angleTolerance;

    addRequirements(shooter);
    addRequirements(intake);
    addRequirements(feeder);
    addRequirements(piston);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   
    if(limelight != null)
        this.speed = InterpolateUtil.interpolate(ShooterConstants.SHOOTER_VISION_MAP, limelight.getAverageDistance());
    else
      speed = ShooterConstants.ShooterSpeed; // Fallback speed if not using limelight      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(limelight != null)
        this.speed = InterpolateUtil.interpolate(ShooterConstants.SHOOTER_VISION_MAP, limelight.getAverageDistance());
    feeder.setVoltage(ShooterConstants.FeederVoltage);


    shooter.setVelocity(speed);
    if (shooter.isReadyToShoot() && isAligned.getAsBoolean()) {
      piston.setSolenoid(true);
      intake.r_control(0.3);
    } 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    piston.setSolenoid(false);
    shooter.setVelocity(0);
    intake.r_control(0);
    feeder.setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
