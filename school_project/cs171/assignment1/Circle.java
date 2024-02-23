/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. CAROL ZHOU
*/

// This class represents a circle shape
public class Circle {

    // Instance variables (data members) of class Circle
    protected double radius; // the radius of the circle
    protected double x; // the x coordinate of the circle's center
    protected double y; // the y coordinate fo the circle's center

    // The default constructor with no argument
    public Circle(){
      // Initializing the values of the instance variables
      radius = 1.0;
      x = 0.0;
      y = 0.0;
    }

    // Second constructor with given radius, but origin default
    public Circle(double r) {
      radius = r;
      x = 0.0;
      y = 0.0;
    }

    // Overloaded constructor
    // Parameter r should be the radius length
    // Parameter ex should be the x coordinate
    // Parameter why should be the y coordinate
    public Circle(double r, double ex, double why) {
      radius = r;
      x = ex;
      y = why;
    }

    // A public getter method for retrieving the radius
    public double getRadius() {
     return radius;
    }

    // A public getter method for retrieving the center coordinates
    public double[] getCenter() {
     double[] c = {this.x, this.y};
     return c;
    }

    // A public getter method for computing and returning
    // the area of the circle
    public double getArea() {
      return radius * radius * Math.PI;
    }

    // A public method you need to write to
    // compute and return the circumference of the circle
    public double getCircumference() {
      return 2 * radius * Math.PI;
    }

    // A public method that compares the sizes of two circles: the circle
    // represented by the current object, and the circle passed as a parameter.
    // Example: circleA.isBiggerThan(circleB) should return true if circleA
    // has a larger area than circleB, false otherwise.
    // NOTE: You may need to modify the parameter list!
    public boolean isBiggerThan(Circle circleB) {
      if(!(circleB instanceof Circle)){
        return false;
      }
      return this.getArea() > circleB.getArea();
    }

    // A public method that takes as input an x coordinate (as a double)
    // and a y coordinate (a double), and returns true if the (x, y) coordinate
    // is inside the current circle, false otherwise.
    // NOTE: You may need to modify the parameter list!
    public boolean containsPoint(double ex, double why) {
      double[] coord = this.getCenter();
      double r = this.getRadius();
      return ((ex - coord[0]) * (ex - coord[0]) + (why - coord[1]) * (why - coord[1])) <= r * r;
    }

    // A public method named setRadius that sets this object's radius
    // based on the passed parameter (of type double).
    // The method should not return anything.
    public void setRadius(double r) {
      radius = r;
    }

    // A public method named setCenter that sets this object's center.
    // The method takes two doubles as parameters: ex and why.
    // It should set the x coordinate of the circle to ex,
    // and the y coordinate of the circle to why.
    // The method should not return anything.
    public void setCenter(double ex, double why) {
      x = ex;
      y = why;
    }

    // Overriden method toString which should
    // return the string representation of this Circle object, as follows:
    // "This circle is centered at point <display_coordinate_of_center_here>
    // with radius <display_radius>"
    @Override
    public String toString() {
      double[] coord = this.getCenter();
      return "This circle is centered at point (" + coord[0] + ", " + coord[1] + ") with radius " + this.getRadius();
    }

		// Override the method equals which is inherited from class Object
		// (similar to what we did in class Employee), and make it return true if the
		// two circles have equal areas, false otherwise
		// Important: Use the @Override annotation
    @Override
    public boolean equals(Object obj) {
      if(!(obj instanceof Circle)){
        return false;
      } 
      Circle circleB = (Circle) obj;
      return(this.getArea() == circleB.getArea());
    }

    public static void main(String[] args) {
      Circle cirA = new Circle();
      System.out.println(cirA.getRadius()); // 1.0
      System.out.println(cirA.getCenter()[0]); // 0.0
      System.out.println(cirA.getCenter()[1]); // 0.0
      System.out.println(cirA.getArea()); // 3.14
      System.out.println(cirA.getCircumference()); // 6.28
      System.out.println(cirA.containsPoint(0,0)); // true
      System.out.println(cirA.containsPoint(0,1)); // true
      System.out.println(cirA.containsPoint(1,1)); // false
      System.out.println(cirA.toString() + "\n"); // "This circle is centered at point (0.0, 0.0) with radius 1.0"

      Circle cirB = new Circle(2);
      System.out.println(cirB.getCircumference()); // 12.57
      System.out.println(cirB.containsPoint(0,0)); // true
      System.out.println(cirB.containsPoint(2,0)); // true
      System.out.println(cirB.containsPoint(2,2)); // false
      System.out.println(cirA.isBiggerThan(cirB)); // false
      System.out.println(cirB.isBiggerThan(cirA)); // true
      System.out.println(cirB.toString()); // "This circle is centered at point (0.0, 0.0) with radius 2.0"
      System.out.println(cirA.equals(cirB) + "\n"); // false

      Circle cirC = new Circle(2, 2, 2);
      System.out.println(cirC.getCircumference()); // 12.57
      System.out.println(cirC.containsPoint(0,0)); // false
      System.out.println(cirC.containsPoint(2,0)); // true
      System.out.println(cirC.containsPoint(2,2)); // true
      System.out.println(cirC.isBiggerThan(cirB)); // false
      System.out.println(cirC.isBiggerThan(cirA)); // true
      System.out.println(cirC.toString()); // "This circle is centered at point (2.0, 2.0) with radius 2.0"
      System.out.println(cirC.equals(cirB)); // true
      System.out.println(cirC.equals(cirA)); // false
      cirC.setRadius(3);
      System.out.println(cirC.isBiggerThan(cirB)); // true
      System.out.println(cirC.equals(cirB)); // false
      cirC.setCenter(3,3);
      System.out.println(cirC.containsPoint(2,0)); // false
      System.out.println(cirC.containsPoint(3,6)); // true
    }

}
