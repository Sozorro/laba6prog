package managers;

public class Coordinates {
    private float x; //Значение поля должно быть больше -692
    private Float y; //Значение поля должно быть больше -859, Поле не может быть null
    
    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
    
    @Override
    public String toString() {
        return "Coordinates{" +
                "x = " + x +
                ", y = " + y + '}';
    }
}
