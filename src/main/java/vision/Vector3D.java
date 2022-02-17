package vision;

public class Vector3D {

    private double x;
    private double y;
    private double z;

    // Vectors relative a point in space. in 3 dimensions
    // Follows the right hand rule: X is Right/Left, Y is forward/backward, Z is up/down.
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Vector3D subtract(Vector3D vec1, Vector3D vec2) {
        return new Vector3D(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY(), vec1.getZ() - vec2.getZ());
    }
}
