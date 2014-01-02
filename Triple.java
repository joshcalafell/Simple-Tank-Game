import java.util.Scanner;

public class Triple
{
	public double x, y, z;

	public Triple(double a, double b, double c)
	{
		x = a;
		y = b;
		z = c;
	}

	public Triple(Scanner input)
	{
		x = input.nextDouble();
		y = input.nextDouble();
		z = input.nextDouble();
	}

	// produce the vector from this point to the other point

	// this.vectorTo(???);

	public Triple vectorTo(Triple other)
	{
		return new Triple(other.x - x, other.y - y, other.z - z);
	}

	public Triple getPosition()
	{
		return new Triple(x, y, z);
	}

	public double distance(Triple other)
	{
		return Math.sqrt((other.x - x) * (other.x - x) + (other.y - y)
				* (other.y - y));
	}

	public Triple add(Triple other)
	{
		return new Triple(other.x + x, other.y + y, other.z + z);
	}

	// produce a new Triple that is s times this one
	public Triple scalarMult(double s)
	{
		return new Triple(s * x, s * y, s * z);
	}

	public String toString()
	{
		return "[" + x + "," + y + "," + z + "]";
	}

	public double findAngle()
	{
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

	public double dotProduct(Triple other)
	{
		return x * other.x + y * other.y + z * other.z;
	}

	public Triple crossProduct(Triple other)
	{
		return new Triple(y * other.z - z * other.y, z * other.x - x * other.z,
				x * other.y - y * other.x);
	}

	public double norm()
	{
		return Math.sqrt(x * x + y * y + z * z);
		// or: Math.sqrt( this.dotProduct( this ) );
	}

	public double norm2()
	{
		return x * x + y * y + z * z;
		// bad: return norm()*norm();
	}

	public void normalize()
	{
		double temp = norm();
		x /= temp;
		y /= temp;
		z /= temp;
	}

	public double findLambda(Triple playerVect, Triple wallPoint)
	{

		double lambdaX = (wallPoint.x - this.x) / playerVect.x + .0001;
		double lambdaY = (wallPoint.y - this.y) / playerVect.y + .0001;
		System.out.println("LambdaX: " + lambdaX + " LambdaY: " + lambdaY);

		return lambdaX;

	}

}
