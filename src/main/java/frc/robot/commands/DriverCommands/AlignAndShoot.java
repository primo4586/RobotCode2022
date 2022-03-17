// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
public class AlignAndShoot extends SequentialCommandGroup {
  /** Creates a new AlignAndShoot. */
  public AlignAndShoot(Driver driver, Shooter shooter, Intake intake, Feeder feeder, PistonForFeeder piston,
      Limelight limelight) {

    if (limelight.isVisible() && shooter.isWithInRange(limelight.getAverageDistance())) {
      addCommands(new AlignByVision(driver, () -> -limelight.getAngleX()));
      addCommands(new AutoShooter(shooter, piston, intake, feeder, limelight));
    }
  }
}
