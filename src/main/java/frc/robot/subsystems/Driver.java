// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import PrimoLib.PrimoTab;
import autonomous.DifferentialDriveData;
import autonomous.PIDConfig;
import autonomous.PrimoDifferentialDriveOdometry;
import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Driver extends SubsystemBase implements DifferentialDriveData{
  /** Creates a new Driver. */

    // Motors
    private WPI_TalonFX m_leftLeader;
    private WPI_TalonFX m_leftFollower;
    private WPI_TalonFX m_rightLeader;
    private WPI_TalonFX m_rightFollower;
  
    // for the controllers to work togther:
    private MotorControllerGroup rightGroup;
    private MotorControllerGroup leftGroup;
    private DifferentialDrive diffDrive;

    //sensors
    private PigeonIMU gyro;
    
    //shufelboard insert and get - object
    private PrimoTab tab;
      //all the values we want to be in the shufelboard:
      public NetworkTableEntry gyroAngleEntry, xEntry, yEntry, leftPos, rightPos, leftVelocityEntry, rightVelocityEntry;
      
      //pid:
      private final PIDConfig rightConfig;
      private final PIDConfig leftConfig;
      public PrimoDifferentialDriveOdometry primoOdometry;


  public Driver(WPI_TalonSRX gyroTalon) {
    /*
      Create new objects for motors, sensors
    */
    this.m_leftLeader = new WPI_TalonFX(Constants.DriverConstants.leftLeaderPort); 
    this.m_leftFollower = new WPI_TalonFX(Constants.DriverConstants.leftFollowerPort);
    this.m_rightLeader = new WPI_TalonFX(Constants.DriverConstants.rightLeaderPort);
    this.m_rightFollower = new WPI_TalonFX(Constants.DriverConstants.rightFollowerPort);
   
    this.leftGroup = new MotorControllerGroup(m_leftLeader, m_leftFollower);
    this.rightGroup = new MotorControllerGroup(m_rightLeader, m_rightFollower);
   
    this.diffDrive = new DifferentialDrive(leftGroup, rightGroup);

    this.gyro = new PigeonIMU(gyroTalon);
    this.gyro.configFactoryDefault();
    

    this.tab = new PrimoTab("Driver");
    this.gyroAngleEntry = tab.addEntry("gyro angle");
    this.xEntry = tab.addEntry("X position");
    this.yEntry = tab.addEntry("Y position");

    this.leftPos = tab.addEntry("left position meters");
    this.rightPos = tab.addEntry("right position meters");

    this.leftVelocityEntry = tab.addEntry("left velocity");
    this.rightVelocityEntry = tab.addEntry("right velocity");


    this.rightConfig = new PIDConfig(0, 0, 0, 0);
    this.leftConfig = new PIDConfig(0, 0, 0, 0);
    

  }

  //Driver control funcions
  public void d_control(double speed, double rotation){
    /*
      Gets speed and rotation and set data in the differentialDrive object
    */
    this.diffDrive.arcadeDrive(speed, rotation);
  }



  //general funcions

  public static void setMotorSpeed(WPI_TalonFX motor, double speed)
  {
    /* 
      Gets a Talon instance and sets it's speed
    */
    motor.set(speed);
  }

  public double getYaw() {
    
    double[] ypr = new double[3];
    this.gyro.getYawPitchRoll(ypr);

    return ypr[0];
  }

  public double getLeftPositionInMeters() {
    return this.m_leftLeader.getSelectedSensorPosition() * Constants.DriverConstants.METER_PER_TICK;
  }

  public double getRightPositionInMeters() {
    return this.m_rightLeader.getSelectedSensorPosition() * Constants.DriverConstants.METER_PER_TICK;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public double getLeftVelocity() {
    // TODO Auto-generated method stub
    return (this.m_leftLeader.getSelectedSensorVelocity() * 0.1) / Constants.DriverConstants.METER_PER_TICK;
  }

  @Override
  public double getRightVelocity() {
    // TODO Auto-generated method stub
    return (this.m_rightLeader.getSelectedSensorVelocity() * 0.1) / Constants.DriverConstants.METER_PER_TICK;
  }

  @Override
  public double getHeading() {
    // TODO Auto-generated method stub
    return -Math.toRadians(getYaw());
  }

  @Override
  public double getLeftDistance() {
    // TODO Auto-generated method stub
    return getLeftPositionInMeters();
  }

  @Override
  public double getRightDistance() {
    // TODO Auto-generated method stub
    return getRightPositionInMeters();
  }
}