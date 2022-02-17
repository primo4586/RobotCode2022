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
import frc.robot.commands.DriverCommands.FollowPath;

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
        public static final int ShooterPort = 15;  
        public static final int FeederPort = 6;  
        public static final double FeederSpeed = 0.5;
        public static final double ShooterSpeed = 0.5;
    }

    public static final class DriverConstants {
        //motor ports:
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
        public static final double ROTATION_LIMITER = 0.5;
        public static final double SLOW = 0.7;
        public static final double BOOST = 1.2;
    }

    public static final class ClimbConstants{
        public static final int climbPort = 0;
        public static final double motorSpeed = 0;
    }

    public static final class IntakeConstants{
        public static final double rollerSpeed = 0.7;
        public static final int rollerPort = 5;     
    }









///////////////////DONT LOOK DOWN BELOW/////////////////////////////////////////////////

































/////////////////////seriously DONT LOOK!!!!!!/////////////////////////////////////////////////////

























































/////////////////////////I SAID NO///////////////////////////////////////////////////





















































///////////////////BRO...//////////////////////////////////////////////////////////////////////////////




    public static final class AutoConstants {

        //Koter in meters.
        public static final double DIAMETER = 0.1524; 

        //Converts encoder value to speed
         public static final double METER_PER_TICK = (DIAMETER * 3.141592) / 2048;

         //the dictans between two wheels
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
        public static final SimpleMotorFeedforward FEEDFORWARD_RIGHT = new SimpleMotorFeedforward(0.6831, 1.6503, 0.37373);
        public static final SimpleMotorFeedforward FEEDFORWARD_LEFT = new SimpleMotorFeedforward(0.6831, 1.6503, 0.37373);

        public static final PIDConfig RIGHT_CONFIG = new PIDConfig(0.12146, 0, 0, 0);
        public static final PIDConfig LEFT_CONFIG = new PIDConfig(0.12146, 0, 0, 0);


        /**
         * Allowed offset from trajectory when path following.
         * 
         * {@link FollowPath} command
         */
        public static final Pose2d OFFSET_TOLERANCE = new Pose2d(new Translation2d(0.03, 0.03), Rotation2d.fromDegrees(5));

        /**
         * Ramsete Controller paramaters, used for tuning the controller
         * These are the default values from the documentation.
         * 
         * @see https://docs.wpilib.org/en/stable/docs/software/advanced-controls/trajectories/ramsete.html#constructing-the-ramsete-controller-object
         */
        public static final double RAMSETE_B = 2, RAMSETE_ZETA = 0.7;

    }


    public static final class pathJson{
        public static final String shoot2ball = "";
        public static final String test = "paths\\PathWeaver\\pathweaver.json";


    }

}