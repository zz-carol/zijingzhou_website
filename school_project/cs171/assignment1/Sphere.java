/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. CAROL ZHOU
*/

public class Sphere extends Circle {

	// Instance variables (data members) of class Sphere
	protected double z; // the z coordinate of the sphere's center

	// The default constructor with no argument
    public Sphere() {
    	// Initializing the values of the instance variables
     	super();
      	z = 0.0;
    }

    // alternative constructor
    // Parameter ex should be the x coordinate
    // Parameter why should be the y coordinate
    // Parameter zee should be the z coordinate
    // Parameter r should be the radius length
    public Sphere(double ex, double why, double zee, double r) {
	    super(r, ex, why);
	    z = zee;
    }

    // A public getter method for retrieving the center coordinates
    // Returns the center as an array of doubles
    @Override
    public double[] getCenter() {
	    double[] c = {super.x, super.y, this.z};
	    return c;
    }

    // A public method named setCenter that sets this object's center.
    // The method takes three doubles as parameters: ex, why, and zee.
    // It should set the x coordinate to ex,
    //  the y coordinate to why, and 
    // the z coordinate to zee.
    // The method should not return anything.
    public void setCenter(double ex, double why, double zee) {
	    super.setCenter(ex, why);
	    z = zee;
    }

    // A public getter method for computing and returning
    // the area of the sphere
    public double getArea() {
      	return 4.0 * super.getArea();
    }

    // A public getter method for computing and returning
    // the volume of the sphere
    public double getVolume() {
      	return (4.0/3.0) * radius * super.getArea();
    }

    public static void main(String[] args) {
        Sphere sphA = new Sphere();
        double[] ctrA = sphA.getCenter();
        for (int i = 0; i < 3; i++) {
            System.out.println(ctrA[i]);
        } // 0.0 0.0 0.0
        System.out.println(sphA.getArea()); // 12.57
        System.out.println(sphA.getVolume()); // 4.19
        sphA.setCenter(1, 2, 3);
        double[] ctrB = sphA.getCenter();
        for (int i = 0; i < 3; i++) {
            System.out.println(ctrB[i]);
        } // 1.0 2.0 3.0
        System.out.println();

        Sphere sphB = new Sphere(3, 2, 5, 4);
        double[] ctrC = sphB.getCenter();
        for (int i = 0; i < 3; i++) {
            System.out.println(ctrC[i]);
        } // 3.0 2.0 5.0
        System.out.println(sphB.getRadius()); // 4.0
        System.out.println(sphB.getCircumference()); // 25.13
        System.out.println(sphB.isBiggerThan(sphA)); // true
        System.out.println(sphB.equals(sphA)); // false
        sphA.setRadius(4);
        System.out.println(sphB.equals(sphA)); // true
    }

}