package AutoLib.LimeLight;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;
import frc.robot.Constants.Color;
import frc.robot.subsystems.LEDs;

public class LimeLight {
    private NetworkTable table;
    private Vector3D TargetPos;
    public double angleX, angleY, distance, skew;

    private static final double X_ANGLE_OFFSET = 0;
    private static final double Y_ANGLE_OFFSET = 28.3;

    /**
     * these 3 values represent the camera's pos compared to the robot's center. for
     * example if the camera is positioned 30 cm to the right of the robot ,40 cm in
     * front of the robot's center and 50 cm above the robots center then
     * X_POSITION_OFFSET = 0.3 Y_POSITION_OFFSET = 0.4 Z_POSITION_OFFSET = 0.5
     */
    private static final double X_POSITION_OFFSET = 0;
    private static final double Y_POSITION_OFFSET = 0.35;
    private static final double Z_POSITION_OFFSET = 0;

    private static final double POWER_PORT_HEIGHT = 2.5;
    private static final double CAMERA_HEIGHT = 0.66;

    private DoubleSupplier angle;

    // this part is gonna be very arabush
    double pitchDownAngle = 0.48;
    private boolean isActive;
    // private PrimoTab tab;
    private ShuffleboardTab tab;
    private Map<String, NetworkTableEntry> entryMap;

    private MedianFilter tvFilter;

    public LimeLight(DoubleSupplier angle, Runnable rumble, Runnable stopRumble) {

        isActive = true;
        this.TargetPos = new Vector3D(0, 0, 0);
        this.table = NetworkTableInstance.getDefault().getTable("limelight");
        this.angle = angle;
        // this.tab = PrimoTab("limelight_debug");
        this.tab = Shuffleboard.getTab("limelight_debug");
        this.entryMap = new HashMap<>();

        this.tvFilter = new MedianFilter(10);
    }


    public void process() {
        double tv = this.table.getEntry("tv").getDouble(0);

        if (!isActive()) {
            this.angleX = -999;
            this.angleY = -999;
            this.distance = -999;
        } else if (tvFilter.calculate(tv) > 0.8) {
            offSetCameraCalc();
            this.angleX = getXAngle();
            this.angleY = getYAngle();
            this.distance = getDistance();
        } else {
            this.angleX = -999;
            this.angleY = -999;
            this.distance = -999;
        }
        double delta = this.angle.getAsDouble() - pitchDownAngle;
        this.addEntry("tx").setNumber(this.angleX);
        this.addEntry("ty").setNumber(this.angleY - delta);
        this.addEntry("distance").setNumber(this.distance);
        this.addEntry("pitch").setNumber(this.angle.getAsDouble());

       // if  (tvFilter.calculate(tv) > 0.8 && Math.abs(angleX) < 1)
        //LEDs.getInstance().setEffect(new Constants.LEDEffects.FlashEffect(Color.GREEN, 2));
    }

    private double getXAngle() {
        return Math.toDegrees(Math.atan2(this.TargetPos.x, this.TargetPos.y));
    }

    public boolean inRange(double tolarance) {
        return table.getEntry("tv").getDouble(0) == 1 && Math.abs(this.angleX) < tolarance;
    }

    private double getYAngle() {
        return Math.toDegrees(Math.atan2(this.TargetPos.z, this.TargetPos.y));
    }

    /**
     * @return the distance from the target in the horizontal plain
     */
    private double getDistance() {
        return Math.sqrt(Math.pow(this.TargetPos.y, 2) + Math.pow(this.TargetPos.x, 2));
    }

    /**
     * if the limelight doesn't work then use this function to return constant
     * values.
     */
    public void disable() {
        this.isActive = false;
    }

    /**
     * enables the limelight. limelight returns real world values.
     */
    public void enable() {
        this.isActive = true;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    public void setlightsOn() {
        setLights(3);
    }

    public void setlightsOff() {
        setLights(1);
    }

    public void setLights(int state) {
        this.table.getEntry("ledMode").forceSetNumber(state);
    }

    // asummes the angle is between 180 to -180
    @Experimental
    private double getSkew(double RobotAngle) {
        // keep the angle between 90 and -90
        // the angle gets closer to 0 when the gyro nears 180 or 0
        double angle = getXAngle();
        double step = 90;
        if (angle < -step) {
            angle += 2 * step;
        } else if (angle > step) {
            angle -= 2 * step;
        }
        return angle + RobotAngle;
    }

    /**
     * align the target position to the center of the inner circle
     * 
     * @param RobotAngle
     */
    @Experimental
    private void alignTargetVectorToCenter(double RobotAngle) {
        Vector3D inner = new Vector3D(Math.sin(Math.toRadians(RobotAngle)), Math.cos(Math.toRadians(RobotAngle)), 0);
        this.TargetPos.add(inner);
    }

    /**
     * find the vector from the center of the robot to the target using the camera
     * offSet from the center of the robot
     *
     * this algorithem assumes that the camera is at pos (0,0,0)
     *
     * X Axis - robot's right Y Axis - robot's forward Z Axis - robot's up
     */
    public void offSetCameraCalc() {
        double x, y, z;
        z = POWER_PORT_HEIGHT - CAMERA_HEIGHT;
        y = z / Math.tan(Math.toRadians(table.getEntry("ty").getDouble(0) + Y_ANGLE_OFFSET));
        x = y * Math.tan(Math.toRadians(table.getEntry("tx").getDouble(0) + X_ANGLE_OFFSET));
        Vector3D simpleTarget = new Vector3D(x, y, z);
        Vector3D robotCenter = new Vector3D(X_POSITION_OFFSET, Y_POSITION_OFFSET, Z_POSITION_OFFSET);
        this.TargetPos = Vector3D.subtract(simpleTarget, robotCenter);
        // this.TargetPos.z += .2159;
    }

    /**
     * get virtual height
     */
    public double getTargetHeight() {
        return table.getEntry("tvert").getDouble(0) * Math.cos(Math.toRadians(table.getEntry("ty").getDouble(0)));
    }

    public NetworkTableEntry addEntry(String name) {
        if (entryMap.containsKey(name)) {
            NetworkTableEntry entry = entryMap.get(name);
            return entry;
        }
        NetworkTableEntry entry = tab.add(name, 0).getEntry();
        entryMap.put(name, entry);
        return entry;
    }

    /**
     * @return the targetPos
     */
    public Vector3D getTargetPos() {
        return new Vector3D(TargetPos);
    }
}