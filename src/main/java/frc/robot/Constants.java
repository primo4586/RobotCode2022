// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import autonomous.PIDConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class ShooterConstants{
        public static final int ShooterPort = 0;
        public static final int FeederPort = 0;
        public static final double FeederSpeed = 20;
        public static final double ShooterSpeed = 20;
    }

    public static final class DriverConstants {
        public static final int leftLeaderPort = 0;
        public static final int rightLeaderPort = 0;
        public static final int leftFollowerPort = 0;
        public static final int rightFollowerPort = 0;
        public static final double METER_PER_TICK = 0.0001558524480;
        public static final PIDConfig L_CONFIG = new PIDConfig(1, 0, 0, 0);
        public static final PIDConfig R_CONFIG = new PIDConfig(1, 0, 0, 0);


        // Limiter for arcade drive.
        public static final double DRIVE_LIMITER = 0.7;
    }

    public static final class ClimbConstants{
        public static final double motorSpeed = 0;
    }

    public static final class IntakeConstants{
        public static final double rollerSpeed = 0;
    }





























































    public static final class AutoConstants {

        // In meters.
        public static final double DIAMETER = 0;
        /**
         * Converts motor controllers output to meters
         * Wheels are 8 inches in diameter, and most of our CTRE Encoders have 4096
         * ticks.
         * The calculation is: diameter ((in meters) * PI) / 4096
         */
        public static final double METER_PER_TICK = (DIAMETER * Math.PI) / 4096;

        // TODO: Measure the trackwidth via the robot characterization.
        public static final double TRACKWIDTH = 0;
        /**
         * Constants for Robot Characterization:
         * DIAMETER * PI = units per rotation
         */

        /**
         * Translates speeds from the {@link RamseteController} to left/right side
         * speeds.
         * 
         * @see https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/differential-drive-kinematics.html
         */
        public static final DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);
        public static final double MAX_SPEED_METERS_PER_SECOND = 1.5;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQ = 1;
        /**
         * Translates speeds from the {@link DifferentialDriveKinematics} to voltages
         * 
         * @see https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/feedforward.html
         */
        public static final SimpleMotorFeedforward FEEDFORWARD_RIGHT = new SimpleMotorFeedforward(0, 0);
        public static final SimpleMotorFeedforward FEEDFORWARD_LEFT = new SimpleMotorFeedforward(0, 0);

        /**
         * Allowed offset from trajectory when path following.
         * 
         * {@link FollowPath} command
         */
        public static final Pose2d OFFSET_TOLERANCE = new Pose2d(new Translation2d(0.03, 0.03),
                Rotation2d.fromDegrees(5));

        /**
         * Ramsete Controller paramaters, used for tuning the controller
         * These are the default values from the documentation.
         * 
         * @see https://docs.wpilib.org/en/stable/docs/software/advanced-controls/trajectories/ramsete.html#constructing-the-ramsete-controller-object
         */
        public static final double RAMSETE_B = 2, RAMSETE_ZETA = 0.7;
    }

}