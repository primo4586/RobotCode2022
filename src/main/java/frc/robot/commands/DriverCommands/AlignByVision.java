package frc.robot.commands.DriverCommands;

import java.util.function.DoubleSupplier;

import PrimoLib.PrimoCommandBase;
import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import autonomous.PIDConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.AlignConstants;
import frc.robot.subsystems.Driver;

public class AlignByVision extends PrimoCommandBase implements Runnable {
  private Driver driver;
  private NetworkTableEntry Kp, Ki, Kd, error, Kf, target, liMax, liMin;
  private DoubleSupplier s;

  private PrimoTab tab;
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
    tab = PrimoShuffleboard.getInstance().getPrimoTab("AlignByVision");


    PIDConfig config = AlignConstants.PID;

    this.Kp = this.tab.addEntry("AlignByVision Kp");
    Kp.setNumber(config.getKp());


    this.Ki = this.tab.addEntry("AlignByVision Ki");
    Ki.setNumber(config.getKi());
    this.Kd = this.tab.addEntry("AlignByVision Kd");
    Kd.setNumber(config.getKd());
    this.error = this.tab.addEntry("pid error");
    this.target = this.tab.addEntry("AlignByVision Setpoint");

    // s = () -> target.getDouble(0);
    addRequirements(driver);

    this.controller = AlignConstants.PID.getController(0.01);
  }

  @Override
  public void initialize() {
    timer.start();

    prevTime = timer.get();

    target.setNumber(0.0);
    initialGyro = driver.getYaw();
    setPoint = s.getAsDouble();
    this.controller.reset();
    this.controller.setPID(this.Kp.getDouble(0), this.Ki.getDouble(0), this.Kd.getDouble(0));
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

  @Override
  public void run() {
    double currTime = timer.get();

    this.controller.setPID(this.Kp.getDouble(0), this.Ki.getDouble(0), this.Kd.getDouble(0));
    setPoint = s.getAsDouble() == -999 ? 0 : s.getAsDouble();

    double power = controller.calculate(0, setPoint);


    if (Math.abs(s.getAsDouble()) > 0.9)
      driver.driveVelocity(-power, power);

    controller.setSetpoint(setPoint);
    error.setNumber(controller.getPositionError());
    driver.feed();

    prevTime = currTime;

  }
}