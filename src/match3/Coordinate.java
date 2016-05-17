package match3;

/**
 * Created by kyle on 5/17/16.
 */
public class Coordinate {
    public int x, y;
    public Coordinate(int x_, int y_) {
        x = x_;
        y = y_;
    }
    public boolean equals(Coordinate otherCoordinate) {
        return this.x == otherCoordinate.x && this.y == otherCoordinate.y;
    }
}
