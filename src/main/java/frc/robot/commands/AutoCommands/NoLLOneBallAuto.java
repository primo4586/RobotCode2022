// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DriverCommands.DriveByTime;
import frc.robot.commands.ShooterCommands.AutoShooter;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class NoLLOneBallAuto extends SequentialCommandGroup {
  /** Represents a OneBallAuto without using the Limelight for estimating shooting distance */
  public NoLLOneBallAuto(Driver driver, Shooter shooter, PistonForFeeder piston, Intake intake, Feeder feeder) {
    
    addCommands(new AutoShooter(shooter, piston, intake, feeder, () -> ShooterConstants.ShooterSpeed).withTimeout(3));
    addCommands(new ParallelCommandGroup(new InstantCommand(() -> piston.setSolenoid(false)),new DriveByTime(driver, 5, 0.5)));
  }
}
