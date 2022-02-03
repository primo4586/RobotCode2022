package AutoLib;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;


public class PrimoDifferentialDriveOdometry {
    private DifferentialDriveOdometry odometry;
    private DifferentialDriveData data;
    private Runnable resetSensors;

    public PrimoDifferentialDriveOdometry(DifferentialDriveData data, Runnable resetSensors) {
        this.data = data;
        this.resetSensors = resetSensors;
        resetSensors.run();
        this.odometry = new DifferentialDriveOdometry(new Rotation2d(data.getHeading()));
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(data.getLeftVelocity(), data.getRightVelocity());
    }

    public Rotation2d getHeading() {
        return new Rotation2d(data.getHeading());
    }

    public void update() {
        this.odometry.update(getHeading(), data.getLeftDistance(), data.getRightDistance());
    }

    public void reset(Rotation2d rot) {
        resetSensors.run();
        odometry.resetPosition(new Pose2d(), rot);
    }

    public double getAverageDistance() {
        return (data.getLeftDistance() + data.getRightDistance()) / 2;
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void setOdometry(Pose2d pose, Rotation2d rot) {
        odometry.resetPosition(pose, rot);
    }

}
