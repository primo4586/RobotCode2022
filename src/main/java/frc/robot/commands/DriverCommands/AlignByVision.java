// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.PIDConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.Driver;

public class AlignByVision extends CommandBase {

  private Driver driver;
  private PrimoTab tab;

  private PIDConfig config;
  private PIDController pidController;
  private DoubleSupplier visionAngle;

  private double angleError;
  private double initalSetpointAngle;
  
  /** Creates a new AlignByVision. */
  public AlignByVision(Driver driver, DoubleSupplier visionAngle, double tolerance) {
    addRequirements(driver);

    this.visionAngle = visionAngle;
    this.driver = driver;
    this.pidController = config.getController();
    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("AlignByVision");
    tab.getTab().add("PID", config);
    // Use addRequirements() here to declare subsystem dependencies.
 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.initalSetpointAngle = driver.getYaw() + visionAngle.getAsDouble();
    this.angleError = visionAngle.getAsDouble();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.angleError = initalSetpointAngle - driver.getYaw();
    double output = pidController.calculate(driver.getYaw(), initalSetpointAngle);

    tab.addEntry("PID Output").setNumber(output);
    tab.addEntry("Angle Error").setNumber(angleError);
    tab.addEntry("Limelight Angle").setNumber(visionAngle.getAsDouble());
    driver.driveVelocity(output, output);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driver.driveVelocity(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.angleError <= VisionConstants.alignByVisionTolerance;
  }
}
