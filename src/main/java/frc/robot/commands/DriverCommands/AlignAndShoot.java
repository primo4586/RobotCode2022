// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.CameraHandler;
import frc.robot.commands.ShooterCommands.AutoShooter;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AlignAndShoot extends ParallelCommandGroup {
  /** Creates a new AlignAndShoot. */
  public AlignAndShoot(Driver driver, Shooter shooter, Intake intake, Feeder feeder, PistonForFeeder piston,
      Limelight limelight, Joystick joystick, CameraHandler camHandler) {

    ParallelCommandGroup alignAndShoot = new ParallelCommandGroup(new AlignByVision(driver, limelight),
        new AutoShooter(shooter, piston, intake, feeder, limelight, camHandler,() -> Math.abs(limelight.getAngleX()) <= 2));
        
    addCommands(new ConditionalCommand(alignAndShoot, new RumbleJoystick(joystick,() -> 1), () -> shooter.isWithInRange(limelight.getDistance()) && limelight.isVisible()));    
  }

  public AlignAndShoot(Driver driver, Shooter shooter, Intake intake, Feeder feeder, PistonForFeeder piston, Limelight limelight) {

    ParallelCommandGroup alignAndShoot = new ParallelCommandGroup(new AlignByVision(driver, limelight),
        new AutoShooter(shooter, piston, intake, feeder, limelight, () -> Math.abs(limelight.getAngleX()) <= 2));

    addCommands(new ConditionalCommand(alignAndShoot, new InstantCommand(), () -> shooter.isWithInRange(limelight.getDistance()) && limelight.isVisible()));    
  }
}
