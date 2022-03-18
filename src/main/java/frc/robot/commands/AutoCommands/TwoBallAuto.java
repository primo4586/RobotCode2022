// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.DriverCommands.AlignAndShoot;
import frc.robot.commands.DriverCommands.DriveByTime;
import frc.robot.commands.IntakeCommands.ManualRoller;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TwoBallAuto extends SequentialCommandGroup {
  /** Creates a new TwoBallAuto. */
  public TwoBallAuto(Driver driver, Shooter shooter, PistonForFeeder piston, Feeder feeder, Intake intake,
      Limelight limelight) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    ParallelCommandGroup intakeBall = new ParallelCommandGroup(new DriveByTime(driver, 3, 0.5),
        new ManualRoller(intake, IntakeConstants.rollerSpeed).withTimeout(3));
    SequentialCommandGroup tarmacAndShoot = new SequentialCommandGroup(new DriveByTime(driver, 1.5, -0.5),
        new AlignAndShoot(driver, shooter, intake, feeder, piston, limelight));
    

    addCommands(intakeBall, tarmacAndShoot, new DriveByTime(driver, 5, 0.5));
  }
}
