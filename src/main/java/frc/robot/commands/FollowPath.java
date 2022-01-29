// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Driver;

public class FollowPath extends CommandBase {
  private Driver driver;
  private Trajectory trajectory;
  private Timer timer;
  private RamseteController controller;
  private boolean setOdometry;

  /**
   * Moves the robot to follow a specified path.
   * @param driver Driver's subsystem
   * @param trajectory Path to follow, genereated trajectory from the {@link PathHandler} class
   * @param setOdometry If the path starts from a specific point that isn't 0,0
   */
  public FollowPath(Driver driver, Trajectory trajectory, boolean setOdometry) {
    addRequirements(driver);
    this.driver = driver;
    this.trajectory = trajectory;
    this.setOdometry = setOdometry;
    timer = new Timer();
    this.controller = new RamseteController(Constants.AutoConstants.RAMSETE_B,Constants.AutoConstants.RAMSETE_ZETA);
  }


  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(setOdometry) {
      driver.getOdometry().resetOdmetry(trajectory.getInitialPose());
    } 
    timer.reset();
    timer.start();
    controller.setEnabled(true);
    // By how much the position can be off the target position
    controller.setTolerance(Constants.AutoConstants.OFFSET_TOLERANCE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Trajectory.State goal = trajectory.sample(timer.get()); // Gets the state we want to get to in a specific point of time
    
    // Calculates the linear and angular speeds needed to get from the current robot position, to the position we want to get to
    ChassisSpeeds speeds = controller.calculate(driver.getPose2d(), goal); 

    // Translates the speeds to left/right motor speeds
    DifferentialDriveWheelSpeeds wheelSpeeds = Constants.AutoConstants.KINEMATICS.toWheelSpeeds(speeds);
    driver.driveVelocity(wheelSpeeds.rightMetersPerSecond, wheelSpeeds.leftMetersPerSecond);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Fin");
    controller.setEnabled(false);
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(trajectory.getTotalTimeSeconds()); // Reached the end time of the trajectory - should be on the end position
  }
}
