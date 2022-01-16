import java.awt.*;

public class Particle {
    Color color;
    float density;
    String name;
    int id;


    //constructor
    Particle(Color color, String name, int id) {
        this.color = color;
        density = 1;
        this.name = name;
        this.id = id;
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
}
