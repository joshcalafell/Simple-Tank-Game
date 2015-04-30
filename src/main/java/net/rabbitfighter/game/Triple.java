package net.rabbitfighter.game;
public class Triple {
	public double x;
	public double y;
	public double z;

	public Triple(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double findAngle() {
		double angle = Math.toDegrees(Math.atan(x / y));
		if (y == 0)
			angle = 90;
		if (x < 0 && y >= 0)
			angle = 180 - angle;
		else if (x < 0 && y < 0)
			angle += 180;
		else if (x >= 0 && y < 0)
			angle = 360 - angle;

		// return Math.atan(x/y);

		return angle;
	}

	// produce the vector from this point to the other point
	public Triple vectorTo(Triple other) {
		return new Triple(other.x - x, other.y - y, other.z - z);
	}

	public Triple add(Triple other) {
		return new Triple(other.x + x, other.y + y, other.z + z);
	}

	public Triple subtract(Triple other) {
		return new Triple(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	// produce a new Triple that is s times this one
	public Triple scalarMult(double lambdaX) {
		return new Triple(lambdaX * x, lambdaX * y, lambdaX * z);
	}

	public String toString() {
		return "[" + x + "," + y + "," + z + "]";
	}

	public double dotProduct(Triple other) {
		return x * other.x + y * other.y + z * other.z;
	}

	public Triple crossProduct(Triple other) {
		return new Triple(y * other.z - z * other.y, z * other.x - x * other.z,
				x * other.y - y * other.x);
	}

	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
		// or: Math.sqrt( this.dotProduct( this ) );
	}

	public double norm2() {
		return x * x + y * y + z * z;
		// bad: return norm()*norm();
	}

	public void normalize() {
		double temp = norm();
		x /= temp;
		y /= temp;
		z /= temp;
	}

}
