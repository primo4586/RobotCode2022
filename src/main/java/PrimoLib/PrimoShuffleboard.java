package PrimoLib;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.CameraHandler;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PistonForFeeder;
import frc.robot.subsystems.Shooter;
import vision.Limelight;

public class PrimoShuffleboard {

    private Map<String, PrimoTab> tabs;
    private static PrimoShuffleboard instance;

    private PrimoShuffleboard() {
        tabs = new HashMap<>();
    }

    public static PrimoShuffleboard getInstance() {
        if (instance == null)
            instance = new PrimoShuffleboard();
        return instance;
    }

    public void updateCameras(CameraHandler camHandler) {
        // getCompetitonBoard().getTab().add("POV: You are Itzik", camHandler.getActiveSource());
        // getCompetitonBoard().getTab().add("POV: You are Limelight", camHandler.getLimelightSource());
    }

    public void updateDebug(Driver driver, Shooter shooter, Climb climb, Feeder feeder, Intake intake, PistonForFeeder pistonForFeeder, Limelight limelight) {

        PrimoTab climbTab = getPrimoTab("Climb");
        PrimoTab driverTab = getPrimoTab("Driver");
        PrimoTab shooterTab = getPrimoTab("Shooter");
        PrimoTab intakeTab = getPrimoTab("Intake");


        /**
         *  Driver Tab
         */
        driverTab.addEntry("Left Velocity").setNumber(driver.getLeftVelocity());
        driverTab.addEntry("Left Pos. ").setNumber(driver.getLeftPositionInMeters());
        driverTab.addEntry("Right Velocity").setNumber(driver.getRightVelocity());
        driverTab.addEntry("Right Pos. ").setNumber(driver.getRightPositionInMeters());
        driverTab.addEntry("Odometry X").setNumber(driver.getPrimoOdometry().getPose().getX());
        driverTab.addEntry("Odometry Y").setNumber(driver.getPrimoOdometry().getPose().getY());
        driverTab.addEntry("Gyro angle").setNumber(driver.getYaw());
        driverTab.addEntry("Is forward").setBoolean(driver.isDirectionForward());

        /**
         * Climb Tab
         */
        climbTab.addEntry("Climb Enabled").forceSetBoolean(climb.isEnabled());
    
        /**
         * Intake Tab
         */
        intakeTab.addEntry("Roller Speed").setNumber(intake.getRollerSpeed());
        intakeTab.addEntry("Joint State").setBoolean(intake.isJointOpen());

        /**
         * Shooter Tab
         */
        shooterTab.addEntry("Shooter Velocity").forceSetNumber(shooter.getShooterVelocity());
        shooterTab.addEntry("Reached target velocity").forceSetBoolean(shooter.isReadyToShoot());
        shooterTab.addEntry("Shooter Setpoint").forceSetNumber(shooter.getPidSetpoint());

       
    }

    public void updateCompetiton(Driver driver, Shooter shooter, Climb climb, Feeder feeder, Intake intake, PistonForFeeder pistonForFeeder, Limelight limelight) {

        PrimoTab compTab = getCompetitonBoard();

        compTab.addEntry("Feeder Piston").forceSetBoolean(!pistonForFeeder.getState());
        compTab.addEntry("Reached Shooter Speed").forceSetBoolean(shooter.isReadyToShoot());
        compTab.addEntry("Is Visible").forceSetBoolean(limelight.isVisible());
        compTab.addEntry("Is In Range").forceSetBoolean(shooter.isWithInRange(limelight.getDistance()));
        compTab.addEntry("Climb Enabled").forceSetBoolean(climb.isEnabled());
    }

    // Adds or gets a new PrimoTab to avoid adding a tab that already exists and
    // crashing the robot
    public PrimoTab getPrimoTab(String tabName) {
        if (tabs.containsKey(tabName))
            return tabs.get(tabName);

        PrimoTab tab = new PrimoTab(tabName);
        tabs.put(tabName, tab);
        return tab;
    }

    // Switches the dashboard to view a different tab, if it exists
    public void selectTab(String tabName) {
        if (tabs.containsKey(tabName))
            Shuffleboard.selectTab(tabName);
    }

    public void buildCompetitionTab() {

        PrimoTab tab = PrimoShuffleboard.getInstance().getCompetitonBoard();

        tab.addEntry("Time").setNumber(Timer.getMatchTime());
        tab.addEntry("Climb Alert").forceSetBoolean(true);
    }

    public PrimoTab getCompetitonBoard() {
        return PrimoShuffleboard.getInstance().getPrimoTab("Competition Dashboard");
    }

}
