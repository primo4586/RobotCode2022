package frc.robot.commands.ShooterCommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;


public class ManualShooter extends CommandBase {
  Shooter shooter;
  // NetworkTableEntry Kp, Ki, Kd, Kf;
  NetworkTableEntry speed;


  private DoubleSupplier shooterSpeed;

  public ManualShooter(Shooter shooter, DoubleSupplier shooterSpeed) {
    this.shooter = shooter;
    this.shooterSpeed = shooterSpeed;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }
  

  @Override
  public void execute() {
    this.shooter.setVelocity(shooterSpeed.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setVelocity(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
