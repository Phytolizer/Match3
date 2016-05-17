package match3;

import java.awt.*;
import java.util.Random;

import static java.awt.Color.*;
import static match3.Direction.*;
/**
 * Created by kyle on 5/17/16.
 */
public class Grid {
    private int sizeX, sizeY;
    private int boxWidth, centerPos;
    private final int MIN_BOX_WIDTH = 14;
    private String alignment;
    private Gem[][] gems;
    private final Color[] allowedColors = new Color[]{yellow, orange, green, red, white, blue, magenta};
    public Grid(int size_x, int size_y) {
        sizeX = size_x;
        sizeY = size_y;
        gems = new Gem[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                gems[i][j] = createRandomGem();
            }
        }
    }
    public void draw(FancyFrame frame, Graphics2D art) {
        boxWidth = getBoxWidth(frame.getWidth(), frame.getHeight() - 35);
        centerPos = getCenterPos(frame.getWidth(), frame.getHeight() - 35);
        art.setColor(black);
        art.drawRect((alignment.equals("horizontal") ? 0 : centerPos),
                (alignment.equals("vertical") ? 0 : centerPos),
                /*(alignment == "horizontal" ? 0 : centerPos) + */boxWidth * sizeX,
                /*(alignment == "vertical" ? 0 : centerPos) + */boxWidth * sizeY);
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                if(i == 0) {
                    art.setColor(black);
                    art.drawLine(alignment.equals("horizontal") ? 0 : centerPos,
                            (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * (j + 1),
                            (alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * sizeX,
                            (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * (j + 1));
                }
                gems[i][j].draw(art,
                        (alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * i,
                        (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * j, boxWidth);
            }
            art.setColor(black);
            art.drawLine((alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * (i + 1),
                    alignment.equals("vertical") ? 0 : centerPos,
                    (alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * (i + 1),
                    (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * sizeY);
        }
    }
    private int getBoxWidth(int frameX, int frameY) {
        if((frameX / sizeX) * sizeY < frameY) {
            alignment = "horizontal";
            return frameX / sizeX < MIN_BOX_WIDTH ? MIN_BOX_WIDTH : frameX / sizeX;
        } else {
            alignment = "vertical";
            return frameY / sizeY < MIN_BOX_WIDTH ? MIN_BOX_WIDTH : frameY / sizeY;
        }
    }
    private int getCenterPos(int frameX, int frameY) {
        int absoluteCenter;
        switch(alignment) {
            case "horizontal":
                absoluteCenter = frameY / 2;
                return absoluteCenter - (sizeY / 2 * boxWidth);
            case "vertical":
                absoluteCenter = frameX / 2;
                return absoluteCenter - (sizeX / 2 * boxWidth);
        }
        return 0;
    }
    public Coordinate getCoordsAt(int x, int y) {
        if(alignment == null)
            return null;
        if(alignment.equals("horizontal")) {
            int x1 = x / boxWidth;
            int y1 = (y - centerPos) / boxWidth;
            if(x1 >= sizeX || x1 < 0 || y1 >= sizeY || y1 < 0)
                return null;
            return new Coordinate(x1, y1);
        } else {
            int x1 = (x - centerPos) / boxWidth;
            int y1 = y / boxWidth;
            if(x1 >= sizeX || x1 < 0 || y1 >= sizeY || y1 < 0)
                return null;
            return new Coordinate(x1, y1);
        }
    }
    public void switchGems(Coordinate coords, int direction) {
        int x1 = coords.x, y1 = coords.y;
        int x2, y2;
        switch(direction) {
            case UP:
                x2 = coords.x;
                y2 = coords.y - 1;
                break;
            case RIGHT:
                x2 = coords.x + 1;
                y2 = coords.y;
                break;
            case DOWN:
                x2 = coords.x;
                y2 = coords.y + 1;
                break;
            case LEFT:
                x2 = coords.x - 1;
                y2 = coords.y;
                break;
            default:
                x2 = coords.x;
                y2 = coords.y;
                break;
        }
        Gem temp = gems[x1][y1];
        gems[x1][y1] = gems[x2][y2];
        gems[x2][y2] = temp;
    }
    public Gem createRandomGem() {
        Random r = new Random();
        return new Gem(allowedColors[r.nextInt(allowedColors.length)]);
    }
}
