package frc.robot.commands.ShooterCommands;

import PrimoLib.PrimoCommandBase;
import PrimoLib.leds.LEDColor;
import PrimoLib.leds.LEDs;
import PrimoLib.leds.LEDEffects.GradientEffect;
import PrimoLib.leds.LEDEffects.StaticColor;
import autonomous.PIDConfig;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;


public class ManualShooter extends PrimoCommandBase {
  Shooter shooter;
  NetworkTableEntry Kp, Ki, Kd, Kf, setPoint, speed;

  public ManualShooter(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);

    System.out.println("CONSTRUCTOR");
    speed = this.shooter.getTab().addEntry("Speed");
    Kp = this.shooter.getTab().addEntry("Kp");
    Ki = this.shooter.getTab().addEntry("Ki");
    Kd = this.shooter.getTab().addEntry("Kd");
    Kf = this.shooter.getTab().addEntry("Kf");

    setPoint = this.shooter.getTab().addEntry("setPoint");

  }

  @Override
  public void initialize() {
    SmartDashboard.putNumber("shooter", 0);
    LEDs.getInstance().setEffect(new GradientEffect(LEDColor.FLAME_ORANGE,LEDColor.RED));
  }
  

  @Override
  public void execute() {
    
    shooter.setConfig(new PIDConfig(Kp.getDouble(0), Ki.getDouble(0), Kd.getDouble(0), Kf.getDouble(0)));
    this.shooter.setVelocity(speed.getDouble(0));
  }

  @Override
  public void end(boolean interrupted) {
    shooter.s_control(0);
    LEDs.getInstance().setEffect(new StaticColor(LEDColor.PRIMO_BLUE));
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
