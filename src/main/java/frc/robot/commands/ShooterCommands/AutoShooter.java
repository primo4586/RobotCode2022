// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.CameraHandler;
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
  private PrimoTab shooterTab;
  private CameraHandler camHandler;
  private int prevCamIndex;

  public AutoShooter(Shooter shooter, PistonForFeeder pistonForFeeder, Intake intake, Feeder feeder,
  Limelight limelight, CameraHandler camHandler) {
    this.shooter = shooter;
    this.piston = pistonForFeeder;
    this.limelight = limelight;
    this.intake = intake;
    this.feeder = feeder;
    this.shooterTab = PrimoShuffleboard.getInstance().getPrimoTab("Shooter");

    addRequirements(shooter);
    addRequirements(intake);
    addRequirements(feeder);
    addRequirements(piston);
    this.camHandler = camHandler;
    this.prevCamIndex = camHandler.getIndex();
  }

  public AutoShooter(Shooter shooter, PistonForFeeder pistonForFeeder, Intake intake, Feeder feeder,
      Limelight limelight) {
    this.shooter = shooter;
    this.piston = pistonForFeeder;
    this.limelight = limelight;
    this.intake = intake;
    this.feeder = feeder;
    this.shooterTab = PrimoShuffleboard.getInstance().getPrimoTab("Shooter");

    addRequirements(shooter);
    addRequirements(intake);
    addRequirements(feeder);
    addRequirements(piston);
    // Use addRequirements() here to declare subsystem dependencies.
  }

   public AutoShooter(Shooter shooter, PistonForFeeder pistonForFeeder, Intake intake, Feeder feeder,
      double speed) {
    this.shooter = shooter;
    this.piston = pistonForFeeder;
    this.intake = intake;
    this.feeder = feeder;
    this.limelight = null;
    this.speed = speed;
    this.shooterTab = PrimoShuffleboard.getInstance().getPrimoTab("Shooter");

    addRequirements(shooter);
    addRequirements(intake);
    addRequirements(feeder);
    addRequirements(piston);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   
    if(limelight != null)
        this.speed = InterpolateUtil.interpolate(ShooterConstants.SHOOTER_VISION_MAP, limelight.getAverageDistance());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    feeder.setVoltage(ShooterConstants.FeederVoltage);

    shooter.setVelocity(speed);
    shooterTab.addEntry("Shooter Velocity").forceSetNumber(shooter.getShooterVelocity());
    if (shooter.isReadyToShoot()) {
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
    if(camHandler != null)
      camHandler.setCamera(prevCamIndex);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
