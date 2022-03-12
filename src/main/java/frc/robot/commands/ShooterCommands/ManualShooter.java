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
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;


public class ManualShooter extends PrimoCommandBase {
  Shooter shooter;
  // NetworkTableEntry Kp, Ki, Kd, Kf, setPoint, speed;
  NetworkTableEntry speed;

  private boolean reached = false;

  // private double speed;

  public ManualShooter(Shooter shooter, double shooterSpeed) {
    this.shooter = shooter;
    // this.speed = shooterSpeed;
    addRequirements(shooter);

    // speed = this.shooter.getTab().addEntry("Speed");
  }

  @Override
  public void initialize() {
  }
  

  @Override
  public void execute() {
    // shooter.setConfig(new PIDConfig(Kp.getDouble(0), Ki.getDouble(0), Kd.getDouble(0), Kf.getDouble(0)));
    this.shooter.setVelocity(ShooterConstants.ShooterSpeed);  
  }

  @Override
  public void end(boolean interrupted) {
    shooter.s_control(0);
    // LEDs.getInstance().setClimbBarsEffect(new StaticColor(LEDColor.PRIMO_BLUE));
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
