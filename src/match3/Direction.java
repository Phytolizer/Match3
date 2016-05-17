package match3;

/**
 * Created by kyle on 5/17/16.
 */
public class Direction {
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    public static final int up = 0, right = 1, down = 2, left = 3;
    public static final int NONE = -1, none = -1;
    public static int getDirection(Coordinate a, Coordinate b) {
        if(a.x == b.x && a.y == b.y) return NONE;
        if(a.x == b.x) {
            if(a.y - b.y < 0) {
                return DOWN;
            } else {
                return UP;
            }
        }
        if(a.y == b.y) {
            if(a.x - b.x < 0) {
                return RIGHT;
            } else {
                return LEFT;
            }
        } else {
            return NONE;
        }
    }
}
