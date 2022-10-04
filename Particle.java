import java.awt.*;

public class Particle {
    private Color color;
    private float density;
    private String name;
    private int id;
    private int x;
    private int y;

    //constructor
    Particle(Color color, String name, int id, float density) {
        this.color = color;
        this.density = density;
        this.name = name;
        this.id = id;
    }
    Particle(int partEnum) {
        switch (partEnum) {
            case 0:
            color = Color.black;
            density = 0;
            name = "air";
            id = 0;
            break;
            case 1:
            color = Color.orange;
            density = 1;
            name = "dust";
            id = 1;
            break;
            case 2:
            color = Color.gray;
            density = 2;
            name = "stone";
            id = 2;
            break;
            case 3:
            break;
        }
    }
    //setters
    public void setColor(Color color) {
        this.color = color;
    }
    public void setDensity(float density) {
        this.density = density;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //getters
    public Color getColor() {
        return color;
    }
    public float getDensity() {
        return density;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}