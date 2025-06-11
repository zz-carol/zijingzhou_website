/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. CAROL ZHOU
*/

public class ShapeTester {

    // !!!Important!!!
    // All methods in this class should be declared "static"

    // A public method isLarger which takes as input two shapes
    // (a Circle first, then a Rectangle) and returns true if the area of
    // the circle is bigger than (or equal to) the area of the rectangle,
    // false otherwise.
    public static boolean isLarger(Circle cir, Rectangle rec) {
      return cir.getArea() >= rec.getArea();
    }

    // A public method longerPerim which takes as input
    // a Circle object followed by a Rectangle object and returns
    // the length of the perimeter of the longer of the two objects.
    public static double longerPerim(Circle cir, Rectangle rec) {
      if (cir.getCircumference() >= rec.getPerimeter()) {
        return cir.getCircumference();
      } else {
        return rec.getPerimeter();
      } 
    }

    // Another version of the public method longerPerim which has the
    // same name and functionality but it takes as input a Rectangle object
    // followed by a Circle object. The method also returns the length of
    // the perimeter of the longer of the two objects.
    public static double longerPerim(Rectangle rec, Circle cir) {
      if (cir.getCircumference() >= rec.getPerimeter()) {
        return cir.getCircumference();
      } else {
        return rec.getPerimeter();
      } 
    }

    // A public method largerArea which takes as input
    // a Circle object followed by a Rectangle object and returns
    // the area of the larger of the two objects.
    public static double largerArea(Circle cir, Rectangle rec) {
      if (cir.getArea() >= rec.getArea()) {
        return cir.getArea();
      } else {
        return rec.getArea();
      } 
    }

    // Another version of the public method largerArea which has the
    // same name and functionality but it takes as input a Rectangle object
    // followed by a Circle object.
    public static double largerArea(Rectangle rec, Circle cir) {
      if (cir.getArea() >= rec.getArea()) {
        return cir.getArea();
      } else {
        return rec.getArea();
      } 
    }

    // A public method containsCenter which takes as input two circles,
    // and returns true if the first circle contains the center of the second circle,
    // false otherwise.
    public static boolean containsCenter(Circle circleA, Circle circleB) {
      double[] coordA = circleA.getCenter();
      double rA = circleA.getRadius();
      double[] coordB = circleB.getCenter();
      return ((coordB[0] - coordA[0]) * (coordB[0] - coordA[0]) + (coordB[1] - coordA[1]) * (coordB[1] - coordA[1])) <= rA * rA;
    }

    public static void main(String[] args) {
      Circle cirA = new Circle();
      Rectangle recA = new Rectangle();
      System.out.println(isLarger(cirA, recA)); // true
      Rectangle recB = new Rectangle(2, 4, 0, 0);
      System.out.println(isLarger(cirA, recB)); // false
      System.out.println(longerPerim(cirA, recA)); // 6.28
      System.out.println(longerPerim(recA, cirA)); // 6.28
      System.out.println(longerPerim(cirA, recB)); // 12.0
      System.out.println(longerPerim(recB, cirA)); // 12.0
      System.out.println(largerArea(cirA, recA)); // 3.14
      System.out.println(largerArea(recA, cirA)); // 3.14
      System.out.println(largerArea(cirA, recB)); // 8.0
      System.out.println(largerArea(recB, cirA)); // 8.0
      Circle cirB = new Circle(2);
      System.out.println(containsCenter(cirA, cirB)); // true
      System.out.println(containsCenter(cirB, cirA)); // true
      cirB.setCenter(1,1);
      System.out.println(containsCenter(cirA, cirB)); // false
      System.out.println(containsCenter(cirB, cirA)); // true
    }

}
