package AutoLib;

public interface DifferentialDriveData {
    /**
     * @return the left velocity in meters perSecond
     */
    double getLeftVelocity();
    /**
     * @return the right velocity in meters perSecond
     */
    double getRightVelocity();
    /**
     * @return get the driver heading angle in radians
     */
    double getHeading();
    /**
     * @return get the left distance in meters
     */
    double getLeftDistance();
    /**
     * @return get the right distance in meters
     */
    double getRightDistance();
    


}
