package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import PrimoLib.PrimoCommandBase;
import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.PIDConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AlignConstants;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.Driver;
import vision.LimelightConstants;

public class AlignByVision extends CommandBase {

  private Driver driver;
  private DoubleSupplier limelightAngle;
  private PrimoTab tab;
  private NetworkTableEntry kP, kI, kD, kF;

  double wheelCenterRadius = AutoConstants.TRACKWIDTH / 2;
  double wheelPerimeter = AutoConstants.DIAMETER  * Math.PI;
  double ticksWithRatio = AutoConstants.TICKS * AutoConstants.GEAR_RATIO;

 
  private double positonSetpoint;

  public AlignByVision(Driver driver, DoubleSupplier limelightAngle) {
    this.driver = driver;
    this.limelightAngle = limelightAngle;

    this.tab = PrimoShuffleboard.getInstance().getPrimoTab("AlignByVision");

    this.kP = tab.addEntry("Kp");
    this.kI = tab.addEntry("Ki");
    this.kD = tab.addEntry("Kd");
    this.kF = tab.addEntry("Kf");
    addRequirements(driver);


    // PID is built into the Falcons, so we don't need to make a different PID profile for aligning, 
    // but we do need to make a PID profile for position control in general
    driver.selectPIDProfile(0);
  }

  @Override
  public void initialize() {
    
    driver.resetEncoders();
    double limelightAngleInRadians = Math.toRadians(limelightAngle.getAsDouble());


    PIDConfig config = new PIDConfig(kP.getDouble(0), kI.getDouble(0), kD.getDouble(0), kF.getDouble(0));
    driver.setPIDConfig(0, config, config);
    positonSetpoint = ((limelightAngleInRadians * wheelCenterRadius) / wheelPerimeter) * ticksWithRatio;


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double limelightAngleInRadians = Math.toRadians(limelightAngle.getAsDouble());
    positonSetpoint = ((limelightAngleInRadians * wheelCenterRadius) / wheelPerimeter) * ticksWithRatio;



    PIDConfig config = new PIDConfig(kP.getDouble(0), kI.getDouble(0), kD.getDouble(0), kF.getDouble(0));
    driver.setPIDConfig(0, config, config);

    driver.setPosition(positonSetpoint, -positonSetpoint);
    driver.feed();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driver.d_control(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(limelightAngle.getAsDouble()) <= 2;
    // return driver.getLeftPositionError() <= AlignConstants.ERROR_TOLERANCE && driver.getRightPositionError() <= AlignConstants.ERROR_TOLERANCE;
  }

}