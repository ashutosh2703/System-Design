
// Single interface for all shapes (Violates ISP)
interface Shape {
    double area();
    double volume(); // 2D shapes don't have volume!
}

// Square is a 2D shape but is forced to implement volume()
class Square implements Shape {
    private double side;

    public Square(double s) {
        this.side = s;
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public double volume() {
        throw new UnsupportedOperationException("Volume not applicable for Square"); // Unnecessary method
    }
}

// Rectangle is also a 2D shape but is forced to implement volume()
class Rectangle implements Shape {
    private double length, width;

    public Rectangle(double l, double w) {
        this.length = l;
        this.width  = w;
    }

    @Override
    public double area() {
        return length * width;
    }

    @Override
    public double volume() {
        throw new UnsupportedOperationException("Volume not applicable for Rectangle"); // Unnecessary method
    }
}

// Cube is a 3D shape, so it actually has a volume
class Cube implements Shape {
    private double side;

    public Cube(double s) {
        this.side = s;
    }

    @Override
    public double area() {
        return 6 * side * side;
    }

    @Override
    public double volume() {
        return side * side * side;
    }
}

public class ISPViolated {
    public static void main(String[] args) {
        Shape square    = new Square(5);
        Shape rectangle = new Rectangle(4, 6);
        Shape cube      = new Cube(3);

        System.out.println("Square Area: "    + square.area());
        System.out.println("Rectangle Area: " + rectangle.area());
        System.out.println("Cube Area: "      + cube.area());
        System.out.println("Cube Volume: "    + cube.volume());

        try {
            System.out.println("Square Volume: " + square.volume()); // Will throw an exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
