/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. CAROL ZHOU
*/

// This class represents a rectangle shape
public class Rectangle {

	private double L; // the length of the rectangle
	private double H; // the height of the rectangle
	private double x; // the x coordinate of the bottom left corner of the rectangle
	private double y; // the y coordinate of the bottom left corner of the rectangle

	// A basic constructor that creates a rectangle with
	// side lengths 1 whose bottom left corner is at (0.0,0.0)
	public Rectangle() {
		L = 1.0;
		H = 1.0;
		x = 0.0;
		y = 0.0;
	}

	// A constructor that takes all four inputs,
	// named Ell, Eich, Ex, and Why for L, H, x, and y, respectively.
	public Rectangle(double Ell, double Eich, double Ex, double Why) {
		L = Ell;
		H = Eich;
		x = Ex;
		y = Why;
	}

	// A public method called getLength which returns the length.
	public double getLength() {
		return L;
	}

	// A public method called getHeight which returns the height.

	public double getHeight() {
		return H;
	}

	// A public method called setLength which takes as input a double
	// called Ell and uses it to set the length of the rectangle.
	public void setLength(double Ell) {
		L = Ell;
	}

	// A public method called setHeight which takes as input a double
	// called Eich and uses it to set the height of the rectangle.
	public void setHeight(double Eich) {
		H = Eich;
	}

	// A public method called getPerimeter which computes and returns
	// the perimeter of the rectangle.
	public double getPerimeter() {
		return (L + H) * 2;
	}

	// A public method called getArea which computes and returns the
	// area of the rectangle.
	public double getArea() {
		return L * H;
	}

	// Override the method equals which is inherited from class Object
	// (similar to what we did in class Employee), and make it return true if the
	// two rectangles have equal areas, false otherwise
	// Important: Use the @Override annotation
	@Override
	public boolean equals(Object obj) {
  		if(!(obj instanceof Rectangle)){
	        return false;
	      } 
  		Rectangle rec = (Rectangle) obj;
  		return(this.getArea() == rec.getArea());
	}

	public static void main(String[] args) {
		Rectangle recA = new Rectangle();
		System.out.println(recA.getLength()); // 1.0
		System.out.println(recA.getHeight()); // 1.0
		System.out.println(recA.getPerimeter()); // 4.0
		System.out.println(recA.getArea() + "\n"); // 1.0

		Rectangle recB = new Rectangle(2, 3, 1, 2);
		System.out.println(recB.getLength()); // 2.0
		System.out.println(recB.getHeight()); // 3.0
		System.out.println(recB.getPerimeter()); // 10.0
		System.out.println(recB.getArea()); // 6.0
		System.out.println(recB.equals(recA)); // false
		recA.setLength(2);
		recA.setHeight(3);
		System.out.println(recB.equals(recA)); // true
	}

}
