// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriverCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriverConstants;
import frc.robot.subsystems.Driver;

public class FollowPathVoltage extends CommandBase {

    private Timer timer = new Timer();
    private Driver driver;

    private Trajectory trajectory;
    private RamseteController controller;
    private PIDController leftController;
    private PIDController rightController;

    private DifferentialDriveWheelSpeeds m_prevSpeeds;
    private double m_prevTime;
    private boolean setOdometry;

    /**
     * Follows the given trajectory, modified version of {@link RamseteCommand}
     * 
     * @param driver      Driver's subsystem
     * @param trajectory  Path to follow, genereated trajectory from the
     *                    {@link PathHandler} class
     * @param setOdometry If the path starts from a specific point that isn't 0,0
     */
    public FollowPathVoltage(Trajectory trajectory, Driver driver, boolean setOdometry) {
        this.trajectory = trajectory;
        this.driver = driver;

        this.controller = new RamseteController(AutoConstants.RAMSETE_B, AutoConstants.RAMSETE_ZETA);

        this.leftController = DriverConstants.L_CONFIG.getController(0.02);
        this.rightController = DriverConstants.R_CONFIG.getController(0.02);

        this.setOdometry = setOdometry;
        addRequirements(driver);
    }

    @Override
    public void initialize() {
        m_prevTime = -1;
        if(setOdometry)
            driver.getPrimoOdometry().resetOdmetry(trajectory.getInitialPose());
            
        var initialState = trajectory.sample(0);
        m_prevSpeeds = AutoConstants.KINEMATICS.toWheelSpeeds(
                new ChassisSpeeds(
                        initialState.velocityMetersPerSecond,
                        0,
                        initialState.curvatureRadPerMeter * initialState.velocityMetersPerSecond));
        timer.reset();
        timer.start();
        leftController.reset();
        rightController.reset();
    }

    @Override
    public void execute() {
        double curTime = timer.get();
        double dt = curTime - m_prevTime;

        if (m_prevTime < 0) {
            driver.driveVoltage(0, 0);
            m_prevTime = curTime;
            return;
        }

        DifferentialDriveWheelSpeeds targetWheelSpeeds = AutoConstants.KINEMATICS.toWheelSpeeds(
                controller.calculate(driver.getPrimoOdometry().getPose(), trajectory.sample(curTime)));

        double leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
        double rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;

        double leftOutput;
        double rightOutput;

        double leftFeedforward = AutoConstants.FEEDFORWARD_LEFT.calculate(
                leftSpeedSetpoint, (leftSpeedSetpoint - m_prevSpeeds.leftMetersPerSecond) / dt);

        double rightFeedforward = AutoConstants.FEEDFORWARD_RIGHT.calculate(
                rightSpeedSetpoint, (rightSpeedSetpoint - m_prevSpeeds.rightMetersPerSecond) / dt);

        leftOutput = leftFeedforward
                + leftController.calculate(driver.getWheelSpeeds().leftMetersPerSecond, leftSpeedSetpoint);

        rightOutput = rightFeedforward
                + rightController.calculate(
                        driver.getWheelSpeeds().rightMetersPerSecond, rightSpeedSetpoint);
        leftOutput = leftSpeedSetpoint;
        rightOutput = rightSpeedSetpoint;

        driver.driveVoltage(leftOutput, rightOutput);
        m_prevSpeeds = targetWheelSpeeds;
        m_prevTime = curTime;
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();

        driver.driveVoltage(0, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }

}
