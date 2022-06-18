package vision;

import PrimoLib.PrimoShuffleboard;
import PrimoLib.PrimoTab;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    
    private NetworkTable table;
    private double angleX;
    private double distance;

    // Follows the right hand rule: X is Right/Left, Y is forward/backward, Z is up/down.
    private Vector3D targetPos;

    private LinearFilter visibilityRate;
    private LinearFilter distanceAverageFilter;
    private double distanceAverage;
    private double visibility;
    private PrimoTab debugTab;


    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        visibilityRate = LinearFilter.movingAverage(10);
        distanceAverageFilter = LinearFilter.movingAverage(20);
        debugTab = PrimoShuffleboard.getInstance().getPrimoTab("limelight_debug");
    }


    // Updates all the values for the limelight - Angle, recalculates the target position, etc, etc.
    public void update() {
        double tv = table.getEntry("tv").getDouble(0);

        visibility = visibilityRate.calculate(tv);
        // If it sees the target well, ("40%" visibillity) we want to keep updating, otherwise the values might not be reliable
        if(isVisible()) {
            calculateTargetVector();

            this.angleX = table.getEntry("tx").getDouble(0);
            this.distance = Math.sqrt(Math.pow(targetPos.getX(), 2) + Math.pow(targetPos.getY(), 2));
            distanceAverage = distanceAverageFilter.calculate(distance);
        } else {
            distanceAverageFilter.reset();
        }
        debugTab.addEntry("visibillity rate").setNumber(visibility);
        debugTab.addEntry("Robot Angle X").setNumber(angleX);
        debugTab.addEntry("Distance").setNumber(distance);

    }

    // Calculates the vector from the robot center to the target.
    public void calculateTargetVector() {
        // Angles from the target relative to the camera
        double cameraAngleX = table.getEntry("tx").getDouble(0); 
        double cameraAngleY = table.getEntry("ty").getDouble(0);
        
        double x, y, z; 

        z = LimelightConstants.TARGET_HEIGHT - LimelightConstants.CAMERA_HEIGHT;
        y = z / Math.tan(Math.toRadians(cameraAngleY + LimelightConstants.Y_CAMERA_ANGLE_OFFSET));
        x = y * Math.tan(Math.toRadians(cameraAngleX + LimelightConstants.X_CAMERA_ANGLE_OFFSET)); 
        
        Vector3D cameraTarget = new Vector3D(x, y, z); // Target's position relative to the camera
        Vector3D robotCenterOffset = new Vector3D(LimelightConstants.X_CENTER_OFFSET, LimelightConstants.Y_CENTER_OFFSET, LimelightConstants.Z_CENTER_OFFSET);
        
        // Gets the position of the target relative to the robot center by subtracting the center.
        this.targetPos = Vector3D.subtract(cameraTarget, robotCenterOffset);
    }
    
    public boolean isVisible() {
        return visibility >= 0.4;
    }


    public double getDistance() {
        return distance;
    }

    public double getAngleX() {
        if(!isVisible())
            return LimelightConstants.TARGET_NOT_VISIBLE;
        return -angleX;
    }

    public double getAverageDistance() {
        return distanceAverage;
    }
}
