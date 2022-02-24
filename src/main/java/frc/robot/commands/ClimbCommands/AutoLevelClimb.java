// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimbCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climb;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoLevelClimb extends SequentialCommandGroup {
  /** Creates a new AutoLevelClimb. */
  public AutoLevelClimb(Climb climb, DoubleSupplier speed) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new LockClaw(climb, 2),
      new ManualRotateChain(climb, speed, false, climb.isMot3In()),
      new LockClaw(climb, 3),
      new ReleaseClaw(climb, 2),
      new ManualRotateChain(climb, speed, false, climb.isMot2or4In()),
      new LockClaw(climb, 4),
      new ReleaseClaw(climb, 3)
    );
  }
}
