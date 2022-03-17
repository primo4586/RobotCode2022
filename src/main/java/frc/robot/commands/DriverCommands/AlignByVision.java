package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import PrimoLib.PrimoCommandBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Driver;
import vision.Limelight;
import vision.LimelightConstants;

public class AlignByVision extends PrimoCommandBase implements Runnable {
  private Driver driver;
  private NetworkTableEntry Kp, Ki, Kd, error, Kf, target, liMax, liMin;
  private DoubleSupplier s;
  PIDController controller;
  private double setPoint, initialGyro;
  Timer timer;
  Notifier notifier;
  private boolean isCancelled = false;
  private Joystick joystick;
  private Limelight limelight;
  double prevTime;

  public AlignByVision(Driver driver, DoubleSupplier limelightAngle, Limelight limelight, Joystick joystick) {
    this.driver = driver;
    s = limelightAngle;
    timer = new Timer();
    this.joystick = joystick;
    notifier = new Notifier(this);
    this.limelight = limelight;

    this.Kp = this.driver.getTab().addEntry("AlignByVision Kp");
    Kp.setNumber(0.05);

    this.liMax = this.driver.getTab().addEntry("AlignByVision lim max");
    liMax.setNumber(99);

    this.liMin = this.driver.getTab().addEntry("AlignByVision lim min");
    liMin.setNumber(-99);

    this.Ki = this.driver.getTab().addEntry("AlignByVision Ki");
    Ki.setNumber(0.00);
    this.Kd = this.driver.getTab().addEntry("AlignByVision Kd");
    Kd.setNumber(0.0005);
    this.error = this.driver.getTab().addEntry("pid error");
    
    this.target = this.driver.getTab().addEntry("AlignByVision Setpoint");

    // s = () -> target.getDouble(0);
    addRequirements(driver);

    this.controller = new PIDController(this.Kp.getDouble(0), this.Ki.getDouble(0), this.Kd.getDouble(0), 0.01);
  }

  @Override
  public void initialize() {
    if(!(limelight.getDistance() < 2.05 && limelight.getDistance() >= 1.5)) {
      driver.getTab().addEntry("CANCELLED").forceSetBoolean(true);
      isCancelled = true;
      return;
    }
    timer.start();

    prevTime = timer.get();

    target.setNumber(0.0);
    initialGyro = driver.getYaw();
    setPoint = s.getAsDouble(); // + initialGyro;
    this.controller.reset();
    this.controller.setPID(this.Kp.getDouble(0), Ki.getDouble(0), this.Kd.getDouble(0));
    this.controller.setSetpoint(setPoint);

    controller.setTolerance(1);

    notifier.startPeriodic(0.01);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    isCancelled = !(limelight.getDistance() < 2.05 && limelight.getDistance() >= 1.5);
    if(isCancelled) {
      joystick.setRumble(RumbleType.kRightRumble, 1);
      joystick.setRumble(RumbleType.kLeftRumble, 1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    notifier.stop();
    driver.d_control(0, 0);
    joystick.setRumble(RumbleType.kRightRumble, 0);
    joystick.setRumble(RumbleType.kLeftRumble, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return controller.atSetpoint() && !isCancelled;
  }

  public double limitOutPut(double output, double max, double min) {
    return Math.min(max, Math.max(min, output));
  }

  @Override
  public void run() {
    double currTime = timer.get();

    this.controller.setPID(this.Kp.getDouble(0), this.Ki.getDouble(0), this.Kd.getDouble(0));
    setPoint = s.getAsDouble() == -LimelightConstants.TARGET_NOT_VISIBLE ? 0 : s.getAsDouble();

    double power = limitOutPut(controller.calculate(0, setPoint), liMax.getDouble(0), liMin.getDouble(0));
    driver.getTab().addEntry("power").forceSetDouble(power);


    if (Math.abs(s.getAsDouble()) > 0.9)
      driver.driveVelocity(-power, power);

    error.setNumber(controller.getPositionError());


    prevTime = currTime;

  }
}