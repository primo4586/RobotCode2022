// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.DifferentialDriveData;
import autonomous.PIDConfig;
import autonomous.PrimoDifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Driver extends SubsystemBase implements DifferentialDriveData{
    
    //Motors
    private WPI_TalonFX m_leftLeader;
    private WPI_TalonFX m_leftFollower;
    private WPI_TalonFX m_rightLeader;
    private WPI_TalonFX m_rightFollower;
    
    //for the controllers to work togther:
    private DifferentialDrive diffDrive;

    //sensors
    private PigeonIMU gyro;
    private WPI_TalonSRX gyroTalon;
    
    private PrimoTab tab;

    private boolean isForward; 
    
  
    private PrimoDifferentialDriveOdometry primoOdometry;


  public Driver() {
    /*
      Create new objects for motors, sensors
    */
    this.m_leftLeader = new WPI_TalonFX(Constants.DriverConstants.leftLeaderPort); 
    this.m_leftFollower = new WPI_TalonFX(Constants.DriverConstants.leftFollowerPort);
    this.m_rightLeader = new WPI_TalonFX(Constants.DriverConstants.rightLeaderPort);
    this.m_rightFollower = new WPI_TalonFX(Constants.DriverConstants.rightFollowerPort);
   
    // Setup followers
    this.m_rightFollower.follow(m_rightLeader, FollowerType.PercentOutput);
    this.m_leftFollower.follow(m_leftLeader, FollowerType.PercentOutput);

    // Setup Brake
    this.m_leftLeader.setNeutralMode(NeutralMode.Brake);
    this.m_rightLeader.setNeutralMode(NeutralMode.Brake);
    this.m_rightFollower.setNeutralMode(NeutralMode.Brake);
    this.m_leftFollower.setNeutralMode(NeutralMode.Brake);

    // Setup PID
    this.m_rightLeader.config_kP(0, Constants.AutoConstants.RIGHT_CONFIG.getKp());
    this.m_leftLeader.config_kP(0, Constants.AutoConstants.LEFT_CONFIG.getKp());
    
    // Setup Inverted
    this.m_leftFollower.setInverted(true);
    this.m_leftLeader.setInverted(true);

    this.diffDrive = new DifferentialDrive(m_leftLeader, m_rightLeader);

    this.isForward = true;

    this.gyroTalon = new WPI_TalonSRX(Constants.DriverConstants.gyroPorts);
    this.gyro = new PigeonIMU(gyroTalon);
    this.gyro.configFactoryDefault();

    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("Driver");
    tab.addEntry("FollowPath Right Setpoint");
    tab.addEntry("FollowPath Left Setpoint");

    primoOdometry = new PrimoDifferentialDriveOdometry(this, ()-> resetEncoders());
  }

  public void d_control(double speed, double rotation){
    /*
      Gets speed and rotation and set data in the differentialDrive object
    */
    if(!isDirectionForward()){
      speed *= -1.0;
    }
    this.diffDrive.arcadeDrive(speed, -rotation);
  }

  public void rotationDrive(double rotation) {
    diffDrive.arcadeDrive(0, rotation, false);
  }
  
  //general funcions
  
  public static void setMotorSpeed(WPI_TalonFX motor, double speed)
  {
    /* 
      Gets a Talon instance and sets it's speed
    */
    motor.set(speed);
  }

  public void changeDirection(){
    this.isForward = !this.isForward;  
       
  }

  public boolean isDirectionForward(){
    return this.isForward;
  }


  //auto relate function:
  public double getYaw() {
    /*
      creat an arr of 3 types of angle: Yaw, pitch and roll.
      return the yaw angle 
    */
    double[] ypr = new double[3];
    this.gyro.getYawPitchRoll(ypr);

    return ypr[0];
  }

  
  public double getLeftPositionInMeters() {
    return this.m_leftLeader.getSelectedSensorPosition() * Constants.AutoConstants.METER_PER_TICK ; 
  }

  public double getRightPositionInMeters() {
    return this.m_rightLeader.getSelectedSensorPosition() * Constants.AutoConstants.METER_PER_TICK ; 
  }

  public void resetEncoders(){
    this.m_rightLeader.setSelectedSensorPosition(0,0,10);
    this.m_leftLeader.setSelectedSensorPosition(0,0,10);
    this.gyro.setYaw(0);
    }

  public void resetGyro() {
    this.gyro.setYaw(0);
  }  

  public PrimoDifferentialDriveOdometry getPrimoOdometry(){
    return primoOdometry;
  }

  public void driveVelocity(double rightMetersPerSecond, double leftMetersPerSecond) {
    // Feedforward calculates the amount of voltage we need to stay at the speeds we want
    double leftFF = Constants.AutoConstants.FEEDFORWARD_LEFT.calculate(leftMetersPerSecond) / 12;
    double rightFF = Constants.AutoConstants.FEEDFORWARD_RIGHT.calculate(rightMetersPerSecond) / 12;

    // Tranlsates the speeds to be per 100ms (The motors update at a rate of 100hz)
    double leftSpeedsIn100ms = leftMetersPerSecond / 10;
    double rightSpeedsIn100ms = rightMetersPerSecond / 10;

    // Sets the motor's velocity setpoint to the velocity given, and uses the FeedForward values to stabilize its velocity and be able to stay on it for a while
    this.m_leftLeader.set(ControlMode.Velocity, leftSpeedsIn100ms / Constants.AutoConstants.METER_PER_TICK, DemandType.ArbitraryFeedForward, leftFF);
    this.m_rightLeader.set(ControlMode.Velocity, rightSpeedsIn100ms / Constants.AutoConstants.METER_PER_TICK, DemandType.ArbitraryFeedForward, rightFF);
    diffDrive.feed();
  }

  public void driveVoltage(double leftVoltage, double rightVoltage) {
    this.m_leftLeader.setVoltage(leftVoltage);
    this.m_rightLeader.setVoltage(rightVoltage);
    diffDrive.feed();
  }

  public void feed() {
    diffDrive.feed();
  }
  
  //implement Dif-drive function:
  @Override
  public double getLeftVelocity() {
    return (this.m_leftLeader.getSelectedSensorVelocity() * 0.1) / Constants.AutoConstants.METER_PER_TICK;
  }

  @Override
  public double getRightVelocity() {
    return (this.m_rightLeader.getSelectedSensorVelocity() * 0.1) / Constants.AutoConstants.METER_PER_TICK;
  }

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
  public void periodic() {
    /*
      This method will be called once per scheduler run
      Debug Info, general subsystem info we might need
    */
    primoOdometry.update();
   
    tab.addEntry("Left Velocity").setNumber(getLeftVelocity());
    tab.addEntry("Left Pos. ").setNumber(getLeftPositionInMeters());
    tab.addEntry("Right Velocity").setNumber(getRightVelocity());
    tab.addEntry("Right Pos. ").setNumber(getRightPositionInMeters());
    tab.addEntry("Odometry X").setNumber(primoOdometry.getPose().getX());
    tab.addEntry("Odometry Y").setNumber(primoOdometry.getPose().getY());
    tab.addEntry("Gyro angle").setNumber(getYaw());
    tab.addEntry("Is forward").setBoolean(isDirectionForward());
  }

  


}
