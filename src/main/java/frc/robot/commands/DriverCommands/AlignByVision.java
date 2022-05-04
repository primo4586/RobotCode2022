package frc.robot.commands.DriverCommands;


import PrimoLib.PrimoCommandBase;
import autonomous.PIDConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Notifier;
import frc.robot.Constants.AlignConstants;
import frc.robot.subsystems.Driver;
import vision.Limelight;

public class AlignByVision extends PrimoCommandBase implements Runnable {
  
  private Driver driver;
  PIDController controller;
  private double setPoint;
  // Kept notifier just in case it's actually required to be running at a different thread and frequency then the normal command scheduler
  Notifier notifier;
  private Limelight limelight;
  

  public AlignByVision(Driver driver, Limelight limelight) {
    this.driver = driver;
    this.limelight = limelight;

    notifier = new Notifier(this);
    PIDConfig config = AlignConstants.PID;

    addRequirements(driver);

    this.controller = config.getController(0.01);
  }

  @Override
  public void initialize() {
    this.setPoint = limelight.getAngleX();
    this.controller.reset();
    this.controller.setSetpoint(setPoint);
    controller.setTolerance(1);

    notifier.startPeriodic(0.01);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    notifier.stop();
    driver.d_control(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }
  
  @Override
  public void run() {
    setPoint = limelight.isVisible() ? limelight.getAngleX() : 0;

    double power = controller.calculate(0,setPoint);
    // driver.getTab().addEntry("power").forceSetDouble(power);

    driver.driveVelocity(-power, power);
  }
}
