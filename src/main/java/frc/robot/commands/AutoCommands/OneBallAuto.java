// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DriverCommands.FollowPath;
import frc.robot.commands.ShooterCommands.ManualFeeder;
import frc.robot.commands.ShooterCommands.ManualShooter;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OneBallAuto extends SequentialCommandGroup {
  /** Creates a new OneBallAuto. */
  public OneBallAuto(Driver driver, Shooter shooter, PistonForFeeder piston, Feeder feeder, Trajectory tarmacPath) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    ParallelCommandGroup shooting = new ParallelCommandGroup(new ManualShooter(shooter, ShooterConstants.ShooterSpeed), new ManualFeeder(feeder));
    SequentialCommandGroup pistonDelay = new WaitCommand(4).andThen(new InstantCommand(() -> piston.solenoidControll(), piston));

    ParallelCommandGroup beforeBackwardsPath = new ParallelCommandGroup(shooting,pistonDelay);
    addCommands(beforeBackwardsPath);
    addCommands(new FollowPath(driver, tarmacPath, true));
  }
}
