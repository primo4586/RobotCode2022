// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import autonomous.DifferentialDriveData;
import autonomous.PIDConfig;
import autonomous.PrimoDifferentialDriveOdometry;

public class Driver extends SubsystemBase {
  /** Creates a new Driver. */

  
  private WPI_TalonFX leftLeader, rightLeader;
  private WPI_TalonFX leftFollower, rightFollower;
  private DifferentialDrive diffDrive;
  private PigeonIMU gyro;
  private PrimoDifferentialDriveOdometry odometry;
  private DifferentialDriveData data;

  private Map<String,NetworkTableEntry> entryMap;
  private ShuffleboardTab tab;

  public Driver() {
    this.tab = Shuffleboard.getTab("Driver");
    this.entryMap = new HashMap<String,NetworkTableEntry>();

    this.leftLeader = new WPI_TalonFX(RobotMap.DriverPorts.LEFT_LEADER);
    this.rightLeader = new WPI_TalonFX(RobotMap.DriverPorts.RIGHT_LEADER);
    this.leftFollower = new  WPI_TalonFX(RobotMap.DriverPorts.LEFT_FOLLOWER);
    this.rightFollower = new WPI_TalonFX(RobotMap.DriverPorts.RIGHT_FOLLOWER);

    this.gyro = new PigeonIMU(new WPI_TalonSRX(RobotMap.DriverPorts.GYRO));
    
    configMotorControllers();
    
    MotorControllerGroup left = new MotorControllerGroup(leftLeader, leftFollower);
    MotorControllerGroup right = new MotorControllerGroup(rightLeader, rightFollower);

    diffDrive = new DifferentialDrive(left, right);

    // Kind of like a Supplier {@link Supplier}, gets the data needed to calculate position via the odometry
    data = new DifferentialDriveData() {

      @Override
      public double getHeading() {
        return -Math.toRadians(getYaw());
      }

      @Override
      public double getLeftDistance() {
        return getLeftPositionInMeters();
      }

      @Override
      public double getRightDistance() {
        return getRightPositionInMeters();
      }

      @Override
      public double getLeftVelocity() {
        return (leftLeader.getSelectedSensorVelocity() * 0.1) / Constants.AutoConstants.METER_PER_TICK;
      }

      @Override
      public double getRightVelocity() {
        return (rightLeader.getSelectedSensorVelocity() * 0.1) / Constants.AutoConstants.METER_PER_TICK;
      }

    };


    odometry = new PrimoDifferentialDriveOdometry(data, () -> {
      resetEncoderAndGyro();
    });
  }

  /**
   * Gets the robot's angle (yaw)
   * @see https://en.wikipedia.org/wiki/Aircraft_principal_axes
   * @return angle in degrees
   */
  public double getYaw() {
    double[] ypr = new double[3];
    gyro.getYawPitchRoll(ypr);
    return ypr[0];
  }

  /**
   * Configures the motor controllers as needed
   */
  public void configMotorControllers() {
   

    this.leftLeader.setNeutralMode(NeutralMode.Brake);
    this.leftFollower.setNeutralMode(NeutralMode.Brake);
    this.rightLeader.setNeutralMode(NeutralMode.Brake);
    this.rightFollower.setNeutralMode(NeutralMode.Brake);
    this.rightLeader.setInverted(true);
    this.rightFollower.setInverted(true);
    configLeftPID();
    configRightPID();

    this.leftFollower.follow(this.leftLeader);
    this.rightFollower.follow(this.rightLeader);

    this.leftLeader.configOpenloopRamp(0);
    this.rightLeader.configOpenloopRamp(0);

    this.leftLeader.configClosedloopRamp(0);
    this.rightLeader.configClosedloopRamp(0);

    this.leftLeader.setSensorPhase(true);
    this.rightLeader.setSensorPhase(true);

  }


  /**
   * Applies the PID values to the left motors
   */
  public void configRightPID() {
    PIDConfig config = Constants.DriverConstants.R_CONFIG;
    this.rightLeader.config_kP(0, config.getKp());
    this.rightLeader.config_kI(0, config.getKi());
    this.rightLeader.config_kD(0, config.getKd());
    this.rightLeader.config_kF(0, config.getKf());
  }

  /**
   * Applies the PID values to the right motors
   */
  public void configLeftPID() {
    PIDConfig config = Constants.DriverConstants.L_CONFIG;

    this.leftLeader.config_kP(0, config.getKp());
    this.leftLeader.config_kI(0, config.getKi());
    this.leftLeader.config_kD(0, config.getKd());
    this.leftLeader.config_kF(0, config.getKf());
  }

  @Override
  public void periodic() {
    odometry.update();

    // Debug Info
    addEntry("gyro").setNumber(getYaw());
    addEntry("Odometry X").setNumber(odometry.getPose().getX());
    addEntry("Odometry Y").setNumber(odometry.getPose().getY());
    addEntry("Left Position (meters)").setNumber(getLeftPositionInMeters());
    addEntry("Right Position (meters)").setNumber(getRightPositionInMeters());
    addEntry("Right Velocity").setNumber((this.rightLeader.getSelectedSensorVelocity() * 10) * Constants.AutoConstants.METER_PER_TICK);
    addEntry("Left Velocity").setNumber((this.leftLeader.getSelectedSensorVelocity() * 10) * Constants.AutoConstants.METER_PER_TICK);
  }
  

  /**
   * Gets the current speeds of the left & right motors
   * @return The speeds of the right & left motors, as a DifferentalDriveWheelSpeeds object.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        (this.leftLeader.getSelectedSensorVelocity() * 10) * Constants.AutoConstants.METER_PER_TICK,
        (this.rightLeader.getSelectedSensorVelocity() * 10) * Constants.AutoConstants.METER_PER_TICK);
  }
  
  /**
   * Self explanatory - Resets the gyro & encoders
   */
  public void resetEncoderAndGyro() {
    this.gyro.setYaw(0);
    resetEncoders();
  }

  /**
   * Moves the robot via arcade drive
   * @param speed - speed from joystick axis
   * @param rotation - rotation from joystick axis
   */
  public void arcadeDrive(double speed, double rotation) {
    diffDrive.arcadeDrive(speed, rotation);
  }

  /**
   * Adds/gets the entry with the specific name
   * @param name Name of the entry
   * @return The NetworkTable entry with the name specified
   */
  public NetworkTableEntry addEntry(String name) {
    if (entryMap.containsKey(name)) {
        NetworkTableEntry entry = entryMap.get(name);
        return entry;
    }
    NetworkTableEntry entry = tab.add(name, 0).getEntry();
    entryMap.put(name, entry);
    return entry;
  }

  /**
   * Drives the robot using 2 velocities, for left and right side - Used mostly on autonomous mode
   * @param rightMeterSpeedPerSecond - Right side's meter per second speed
   * @param leftMeterSpeedPerSecond - Left side's meter per second speed
   */
  public void driveVelocity(double rightMeterSpeedPerSecond, double leftMeterSpeedPerSecond) {
    // Translates the velocity to voltages to give the motors
    double leftFFVoltage = Constants.AutoConstants.FEEDFORWARD_LEFT.calculate(leftMeterSpeedPerSecond) / 12;
    double rightFFVoltage = Constants.AutoConstants.FEEDFORWARD_RIGHT.calculate(rightMeterSpeedPerSecond) / 12;

    leftFollower.follow(leftLeader,FollowerType.PercentOutput);
    rightFollower.follow(rightLeader,FollowerType.PercentOutput);

    // Tranlsates the speeds to be per 100ms (The motors update at a rate of 100hz)
    double rightSpeedsIn100ms = rightMeterSpeedPerSecond / 10;
    double leftSpeedsIn100ms = leftMeterSpeedPerSecond / 10;

    // Sets the motor's velocity setpoint to the velocity given, and uses the FeedForward voltages to make it more accurate
    leftLeader.set(ControlMode.Velocity, leftSpeedsIn100ms / Constants.AutoConstants.METER_PER_TICK, DemandType.ArbitraryFeedForward, leftFFVoltage);
    rightLeader.set(ControlMode.Velocity, rightSpeedsIn100ms / Constants.AutoConstants.METER_PER_TICK, DemandType.ArbitraryFeedForward, rightFFVoltage);
    diffDrive.feed();
  }

  public void resetEncoders() {
    this.leftLeader.setSelectedSensorPosition(0, 0, 10);
    this.rightLeader.setSelectedSensorPosition(0, 0, 10);
  }

  /**
   * Converts the encoder position to meters
   * @return - Left side's position, in meters
   */
  public double getLeftPositionInMeters() {
    return this.leftLeader.getSelectedSensorPosition() * Constants.AutoConstants.METER_PER_TICK;
  }

   /**
   * Converts the encoder position to meters
   * @return - Right side's position, in meters
   */
  public double getRightPositionInMeters() {
    return this.rightLeader.getSelectedSensorPosition() * Constants.AutoConstants.METER_PER_TICK;
  }

  public Pose2d getPose2d() {
    return odometry.getPose();
  }

  public PrimoDifferentialDriveOdometry getOdometry() {
    return odometry;
  }

  public void feedDiffDrive() {
    this.diffDrive.feed();
  }

  public void resetOdometry() {
    odometry.setOdometry(new Pose2d(), new Rotation2d());
  }

 
}
