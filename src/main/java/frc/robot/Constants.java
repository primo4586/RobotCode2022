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
import edu.wpi.first.math.util.Units;
import frc.robot.commands.DriverCommands.FollowPath;
import vision.InterpolationMap;

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

    public static final class Pneumatics {
        public static final int pcmPort = 8;
        public static final int intakeJointPort = 5;
        public static final int climbSolenoidA = 6;
        public static final int climbSolenoidB = 7;
        public static final int feederPort = 0; // 4
    }

    public static final class ShooterConstants {
        public static final int ShooterPort = 15;
        public static final int FeederPort = 6;
        public static final double FeederVoltage = 5;
        public static final double FeederSpeed = 0.8;
        public static final double ShooterSpeed = 12800; // 13500

        // public static final PIDConfig FEEDER_CONFIG = new PIDConfig(1, 0, 0, 0);
        public static final PIDConfig SHOOTER_CONFIG = new PIDConfig(0.22, 0, 0.2, 0.055);
        
        public static final double READY_SPEED_TOLERANCE = 400;

        public static final double MIN_SHOOTING_RANGE = 1;
        public static final double MAX_SHOOTING_RANGE = 2;
        

        public static final InterpolationMap SHOOTER_VISION_MAP = new InterpolationMap()
                .put(1, 14100)
                .put(1.2, 13800)
                .put(1.4, 12800)
                .put(1.6, 12800)
                .put(1.8, 12900)
                .put(2, 13200)
                .put(2.1, 13300);


        public static final InterpolationMap FEEDER_VISION_MAP = new InterpolationMap();   

    }

    public static final class AlignConstants {
        
        public static final PIDConfig PID = new PIDConfig(0.05, 0, 0.0005, 0);
    }

    public static final class DriverConstants {
        // motor ports:
        public static final int leftLeaderPort = 2;
        public static final int rightLeaderPort = 0;
        public static final int leftFollowerPort = 1;
        public static final int rightFollowerPort = 3;

        // pid?
        public static final double METER_PER_TICK = 0.0001558524480;
        public static final PIDConfig L_CONFIG = new PIDConfig(1, 0, 0, 0);
        public static final PIDConfig R_CONFIG = new PIDConfig(1, 0, 0, 0);

        // Sensors:
        public static final int gyroPorts = 7;

        // Limiter for arcade drive.
        public static final double SPEED_LIMITER = 0.6;
        public static final double ROTATION_LIMITER = 0.6;
        public static final double SLOW = 0.7;
        public static final double BOOST = 1.2;
    }

    public static final class ClimbConstants {
        public static final int rightMotorPort = 10;
        public static final int leftMotorPort = 9;

        // Piston CLOSED - (Claw is locked onto the Bar)
        public static final boolean PISTON_LOCKED = false;
        // Piston OPEN - (Claw is unlocked, not attaching to the bar)
        public static final boolean PISTON_RELEASE = true;

        // Limit is in TICKS.
        public static final int FORWARD_LIMIT = 0;
        public static final int BACKWARD_LIMIT = 0;
        public static final double chainVoltage = 0.5 * 13;//0.7

        public static int sPistonAport = 1;
        public static int sPistonBport = 0;
    }

    public static final class IntakeConstants {
        public static final double rollerSpeed = 0.8
        ; // 0.6
        public static final int rollerPort = 5;
    }

    public static final class AutoConstants {

        // Koter in meters.
        public static final double DIAMETER = Units.inchesToMeters(6);

        // Encoder ticks (Integrated TalonFX encoders have 2048 ticks)
        public static final double TICKS = 2048;

        // Gear ratio between the motor to the wheels.
        public static final double GEAR_RATIO = 7.5;

        // Converts encoder value to speed
        public static final double METER_PER_TICK = (DIAMETER * Math.PI) / (TICKS * GEAR_RATIO);

        // the dictans between two wheels
        public static final double TRACKWIDTH = 0.57;

        /**
         * Constants for Robot Characterization:
         * DIAMETER * PI = units per rotation
         */

        // Translates speeds from the RamseteController to left/right side speeds.
        public static final DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

        public static final double MAX_SPEED_METERS_PER_SECOND = 1.5;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQ = 1;

        /**
         * Translates speeds from the {@link DifferentialDriveKinematics} to voltages
         * 
         * @see https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/feedforward.html
         */
        public static final SimpleMotorFeedforward FEEDFORWARD_RIGHT = new SimpleMotorFeedforward(0.6831, 1.6503,
                0.37373);
        public static final SimpleMotorFeedforward FEEDFORWARD_LEFT = new SimpleMotorFeedforward(0.6831, 1.6503,
                0.37373);

        public static final PIDConfig RIGHT_CONFIG = new PIDConfig(0.12146, 0, 0, 0);
        public static final PIDConfig LEFT_CONFIG = new PIDConfig(0.12146, 0, 0, 0);

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

    public class VisionConstants {
        public static final double alignByVisionTolerance = 1;
    }

    public static final class pathJson {
        public static final String shoot2ball = "";
        public static final String test = "paths\\PathWeaver\\pathweaver.json";

    }

}
