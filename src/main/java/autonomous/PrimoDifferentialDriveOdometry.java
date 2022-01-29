package autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

public class PrimoDifferentialDriveOdometry {
    private DifferentialDriveOdometry odometry;
    private DifferentialDriveData data;
    private Runnable resetSensors;

    /**
     * Wrapper class to handle Odometry (Getting robot's positon on the field)
     * @param data Data needed from the robot to get its position
     * @param resetSensors called to reset the robot's sensors when starting a new path
     */
    public PrimoDifferentialDriveOdometry(DifferentialDriveData data, Runnable resetSensors) {
        this.data = data;
        this.resetSensors = resetSensors;
        resetSensors.run();
        this.odometry = new DifferentialDriveOdometry(new Rotation2d(data.getHeading()));
    }

    /**
     * Gets robot's wheels velocity
     * @return Robot's wheel velocities
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(data.getLeftVelocity(), data.getRightVelocity());
    }

    /**
     * Gets the robot's angle (yaw)
     * @return Returns the robot's angle in a Rotation2d object
     */
    public Rotation2d getHeading() {
        return new Rotation2d(data.getHeading());
    }

    /**
     * Updates the odometry's position to represent the robot's position
     */
    public void update() {
        this.odometry.update(getHeading(), data.getLeftDistance(), data.getRightDistance());
    }

    /**
     * Resets the odometry's position to 0,0 with a specific rotation.
     * @param rot - rotation to be reset to
     */
    public void reset(Rotation2d rot) {
        resetSensors.run();
        odometry.resetPosition(new Pose2d(), rot);
    }

    public void resetOdmetry(Pose2d resetPosition) {
        resetSensors.run();
        odometry.resetPosition(resetPosition, resetPosition.getRotation());
    }

    /**
     * Gets the robot's position on the field as a Pose2d object
     * @return Location of the robot, represented in a Pose2d
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Resetting the odometry's position to a specific position and rotation
     * @param pose Position on the field
     * @param rot Rotation at the position
     */
    public void setOdometry(Pose2d pose, Rotation2d rot) {
        odometry.resetPosition(pose, rot);
    }

}
