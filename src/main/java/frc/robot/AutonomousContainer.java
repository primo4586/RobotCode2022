// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.Map;


import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.CommandSelector;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.AutoCommands.NoLLOneBallAuto;
import frc.robot.commands.AutoCommands.OneBallAuto;
import frc.robot.commands.AutoCommands.TwoBallAuto;
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
        this.competitionTab = PrimoShuffleboard.getInstance().getCompetitonBoard();
        this.autoPaths = new HashMap<String,Command>();

        // Creating & adding the commands to the selector
        InstantCommand testCmd = new InstantCommand(() -> System.out.println("Test Auto"));
        NoLLOneBallAuto oneBallAutoNoLimelight = new NoLLOneBallAuto(driver, shooter, piston,intake,feeder);
        TwoBallAuto twoBallAuto = new TwoBallAuto(driver, shooter, piston, feeder, intake, limelight);
        OneBallAuto oneBallAuto = new OneBallAuto(driver, shooter, piston, feeder, intake, limelight);


        autoPaths.put("Test", testCmd);
        autoPaths.put("One Ball Auto", oneBallAuto);
        autoPaths.put("Two Ball Auto", twoBallAuto);
        autoPaths.put("No Limelight One Ball Auto", oneBallAutoNoLimelight);
        this.autoSelector = new CommandSelector(autoPaths, competitionTab.getTab());
    }

    public Command getSelectedCommand() {
        return this.autoSelector.getCommand();
    }

}

