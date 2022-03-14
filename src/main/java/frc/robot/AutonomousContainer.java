// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.Map;


import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.CommandSelector;
import autonomous.PathHandler;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.AutoCommands.OneBallAuto;
import frc.robot.commands.DriverCommands.DriveByTime;
import frc.robot.commands.DriverCommands.FollowPath;
import frc.robot.commands.DriverCommands.FollowPathVoltage;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

/** Add your docs here. */
public class AutonomousContainer {

    // Configuration & Command Selection
    private CommandSelector autoSelector;
    private PrimoTab competitionTab;

    // Paths/Commands for autos
    private Map<String, Command> autoPaths;

    public AutonomousContainer(Driver driver,Shooter shooter, Feeder feeder, Intake intake, Climb climb, PistonForFeeder piston, Limelight limelight) {
        this.competitionTab = PrimoShuffleboard.getInstance().getPrimoTab("Competition Dashboard");
        this.autoPaths = new HashMap<String,Command>();

        // Loading the autonomous trajectories
        Trajectory oneBallPath = PathHandler.getInstance().loadPath("output/oneBallAuto.wpilib.json"); 
        Trajectory oneMeter = PathHandler.getInstance().loadPath("output/oneMeter.wpilib.json");


        // Creating & adding the commands to the selector
        InstantCommand testCmd = new InstantCommand(() -> System.out.println("Test Auto"));
        OneBallAuto oneBallAuto = new OneBallAuto(driver, shooter, piston,feeder);

        DriveByTime time = new DriveByTime(driver,5);

        autoPaths.put("Test", testCmd);
        autoPaths.put("One Ball Auto", oneBallAuto);
        autoPaths.put("Time drive", time);
        autoPaths.put("Test Follow", new FollowPath(driver, oneBallPath, true));
        autoPaths.put("Test Curved", new FollowPath(driver, oneMeter, true));
        this.autoSelector = new CommandSelector(autoPaths, competitionTab.getTab());
    }

    public Command getSelectedCommand() {
        return this.autoSelector.getCommand();
    }

    // public FollowPathVoltage getCommandForTrajectory(Driver driver, Trajectory trajectory, boolean setOdometry) {
    //     return new FollowPathVoltage(trajectory, 
    //         () -> driver.getPrimoOdometry().getPose(),
    //         new RamseteController(AutoConstants.RAMSETE_B, AutoConstants.RAMSETE_ZETA),
    //         AutoConstants.KINEMATICS,
    //         driver::driveVelocity,
    //         driver,
    //         setOdometry
    //     );
    // }

}

