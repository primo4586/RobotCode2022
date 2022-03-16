package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import PrimoLib.PrimoCommandBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Driver;

public class AlignByVision extends PrimoCommandBase implements Runnable {
  private Driver driver;
  private NetworkTableEntry Kp, Ki, Kd, error, Kf, target, liMax, liMin;
  private DoubleSupplier s;
  PIDController controller;
  private double setPoint, initialGyro;
  Timer timer;
  Notifier notifier;
  double prevTime;

  public AlignByVision(Driver driver, DoubleSupplier limelightAngle) {
    this.driver = driver;
    s = limelightAngle;
    timer = new Timer();
    notifier = new Notifier(this);

    this.Kp = this.driver.getTab().addEntry("AlignByVision Kp");
    Kp.setNumber(0.05);

    this.liMax = this.driver.getTab().addEntry("AlignByVision lim max");
    liMax.setNumber(0.3);

    this.liMin = this.driver.getTab().addEntry("AlignByVision lim min");
    liMin.setNumber(-0.3);

    this.Ki = this.driver.getTab().addEntry("AlignByVision Ki");
    Ki.setNumber(0.00);
    this.Kd = this.driver.getTab().addEntry("AlignByVision Kd");
    Kd.setNumber(0.0005);
    this.error = this.driver.getTab().addEntry("pid error");
    this.Kf = this.driver.getTab().addEntry("AlignByVision Kf");
    Kf.setNumber(0.1);
    this.target = this.driver.getTab().addEntry("AlignByVision Setpoint");

    // s = () -> target.getDouble(0);
    addRequirements(driver);

    this.controller = new PIDController(this.Kp.getDouble(0), this.Ki.getDouble(0), this.Kd.getDouble(0), 0.01);
  }

  @Override
  public void initialize() {
    timer.start();

    prevTime = timer.get();

    target.setNumber(0.0);
    initialGyro = driver.getYaw();
    setPoint = s.getAsDouble(); // + initialGyro;
    this.controller.reset();
    this.controller.setPID(this.Kp.getDouble(0), 0, this.Kd.getDouble(0));
    this.controller.setSetpoint(setPoint);

    this.controller.setIntegratorRange(-0.2, 0.2);
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

  public double limitOutPut(double output, double max, double min) {
    return Math.min(max, Math.max(min, output));
  }

  @Override
  public void run() {
    double currTime = timer.get();
    SmartDashboard.putNumber("dt", prevTime - currTime);

    this.controller.setPID(this.Kp.getDouble(0), this.Ki.getDouble(0), this.Kd.getDouble(0));
    setPoint = s.getAsDouble() == -999 ? 0 : s.getAsDouble();

    double power = limitOutPut(controller.calculate(0, setPoint), liMax.getDouble(0), liMin.getDouble(0));
    driver.getTab().addEntry("power").forceSetDouble(power);

    power = setPoint >= -2 || setPoint == 0 ? power : -0.2;

    if (Math.abs(s.getAsDouble()) > 0.9)
      driver.driveVelocity(-power, power);

    controller.setSetpoint(setPoint);
    error.setNumber(controller.getPositionError());

    // driver.feed();

    prevTime = currTime;

  }
}