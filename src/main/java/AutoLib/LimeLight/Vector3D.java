package AutoLib.LimeLight;
class Vector3D {
	public double x, y, z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D p,Vector3D q) {
		// a vector P-->Q,  i.e. Q-P
		this.x = q.x - p.x;
		this.y = q.y - p.y;
		this.z = q.z - p.z;
	}
	
	
	public Vector3D(Vector3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public double getLength() {
		return (double) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
	}
	
	public void normalize() {
		double l = (double) (1 / Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z));
    this.x *= l;
    this.y *= l;
    this.z *= l;
  }
  
  public void add(Vector3D v) {
  	this.x += v.x;
  	this.y += v.y;
  	this.z += v.z;
  }
  
  public static Vector3D add(Vector3D v1, Vector3D v2) {
  	return new Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
  }
  
  public void subtract(Vector3D v) {
  	this.x -= v.x;
  	this.y -= v.y;
  	this.z -= v.z;
  }
  
  public static Vector3D subtract(Vector3D v1, Vector3D v2) {
  	return new Vector3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
  }
  
  public void scale(double s) {
  	this.x *= s;
  	this.y *= s;
  	this.z *= s;
  }
  
  public static Vector3D scale(Vector3D v, double s) {
  	return new Vector3D(s*v.x, s*v.y, s*v.z);
  }
  
  public double dot(Vector3D v) {
  	return this.x*v.x + this.y*v.y + this.z*v.z;
  }
  
  public static double dot(Vector3D v1, Vector3D v2) {
  	return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
  }
  
  public Vector3D cross(Vector3D v) {
  	return new Vector3D(this.y*v.z - this.z*v.y, this.z*v.x - this.x*v.z, this.x*v.y - this.y*v.x);
  }
	
  public static Vector3D cross(Vector3D v1, Vector3D v2) {
  	return new Vector3D(v1.y*v2.z - v1.z*v2.y, v1.z*v2.x - v1.x*v2.z, v1.x*v2.y - v1.y*v2.x);
  }
  
}