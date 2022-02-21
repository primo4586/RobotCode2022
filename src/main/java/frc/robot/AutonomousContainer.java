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
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriverCommands.FollowPath;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** Add your docs here. */
public class AutonomousContainer {

    // Configuration & Command Selection
    private CommandSelector autoSelector;
    private PrimoTab competitionTab;

    // Paths/Commands for autos
    private Map<String, Command> autoPaths;

    public AutonomousContainer(Driver driver,Shooter shooter, Feeder feeder, Intake intake, Climb climb) {
        this.competitionTab = PrimoShuffleboard.getInstance().getPrimoTab("Competition Dashboard");
        this.autoPaths = new HashMap<String,Command>();

        // Loading the autonomous trajectories
        // TODO: Change to an actual auto path we create in the future
        // Trajectory oneBallPath = PathHandler.getInstance().loadPath("output/oneballAuto.wpilib.json"); 
        
        // Creating & adding the commands to the selector
        InstantCommand testCmd = new InstantCommand(() -> System.out.println("Test Auto"));
        // FollowPath oneBallAuto = new FollowPath(driver, oneBallPath, true);

        autoPaths.put("Test", testCmd);
        // autoPaths.put("One Ball Auto", oneBallAuto);

        this.autoSelector = new CommandSelector(autoPaths, competitionTab.getTab());
    }

    public Command getSelectedCommand() {
        return this.autoSelector.getCommand();
    }

}
