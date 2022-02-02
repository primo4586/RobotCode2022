package AutoLib;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class PIDConfig implements Sendable {

    private double Kp;
    private double Ki;
    private double Kd;
    private double Kf;

    public PIDController getController(){
        return new PIDController(Kp, Ki, Kd, 0.02);
    }

    public PIDConfig(double Kp, double Ki, double Kd, double Kf) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kf = Kf;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Kp", this::getKp, this::setKp);
        builder.addDoubleProperty("Ki", this::getKi, this::setKi);
        builder.addDoubleProperty("Kd", this::getKd, this::setKd);
        builder.addDoubleProperty("Kf", this::getKf, this::setKf);
    }
    
    public double getKp(){return this.Kp;}
    public double getKi(){return this.Ki;}
    public double getKd(){return this.Kd;}
    public double getKf(){return this.Kf;}

    public void setKp(double Kp){this.Kp = Kp;}
    public void setKi(double Ki){this.Ki = Ki;}
    public void setKd(double Kd){this.Kd = Kd;}
    public void setKf(double Kf){this.Kf = Kf;}
}
