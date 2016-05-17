package match3;

import java.awt.*;
import java.util.Random;

import static java.awt.Color.*;
/**
 * Created by kyle on 5/17/16.
 */
public class Grid {
    private int sizeX, sizeY;
    private int boxWidth, centerPos;
    private final int MIN_BOX_WIDTH = 14;
    private String alignment;
    private Gem[][] gems;
    private final Color[] allowedColors = new Color[]{yellow, orange, green, red, white, blue, green};
    public Grid(int size_x, int size_y) {
        sizeX = size_x;
        sizeY = size_y;
        Random colorChooser = new Random();
        gems = new Gem[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                gems[i][j] = new Gem(allowedColors[colorChooser.nextInt(6)]);
            }
        }
    }
    public void draw(FancyFrame frame, Graphics2D art) {
        boxWidth = getBoxWidth(frame.getWidth(), frame.getHeight());
        centerPos = getCenterPos(frame.getWidth(), frame.getHeight());
        art.setColor(black);
        art.drawRect((alignment == "horizontal" ? 0 : centerPos),
                (alignment == "vertical" ? 0 : centerPos),
                (alignment == "horizontal" ? 0 : centerPos) + boxWidth * sizeX,
                (alignment == "vertical" ? 0 : centerPos) + boxWidth * sizeY);
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                if(i == 0) {
                    art.setColor(black);
                    art.drawLine(alignment == "horizontal" ? 0 : centerPos,
                            (alignment == "vertical" ? 0 : centerPos) + boxWidth * (j + 1),
                            (alignment == "horizontal" ? 0 : centerPos) + boxWidth * sizeX,
                            (alignment == "vertical" ? 0 : centerPos) + boxWidth * (j + 1));
                }
                gems[i][j].draw(art,
                        (alignment == "horizontal" ? 0 : centerPos) + boxWidth * i,
                        (alignment == "vertical" ? 0 : centerPos) + boxWidth * j, boxWidth);
            }
            art.setColor(black);
            art.drawLine((alignment == "horizontal" ? 0 : centerPos) + boxWidth * (i + 1),
                    alignment == "vertical" ? 0 : centerPos,
                    (alignment == "horizontal" ? 0 : centerPos) + boxWidth * (i + 1),
                    (alignment == "vertical" ? 0 : centerPos) + boxWidth * sizeY);
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
}
