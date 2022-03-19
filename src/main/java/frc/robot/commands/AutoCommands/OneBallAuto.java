// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriverCommands.AlignAndShoot;
import frc.robot.commands.DriverCommands.DriveByTime;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OneBallAuto extends SequentialCommandGroup {
  /** Creates a new OneBallAuto. */
  public OneBallAuto(Driver driver, Shooter shooter, PistonForFeeder piston, Feeder feeder, Intake intake,
  Limelight limelight) {
    
    
    AlignAndShoot alignAndShoot = new AlignAndShoot(driver, shooter, intake, feeder, piston, limelight);
    DriveByTime outOfTarmac = new DriveByTime(driver, 4, 0.5);

    addCommands(alignAndShoot.withTimeout(3), outOfTarmac);
  }
}
