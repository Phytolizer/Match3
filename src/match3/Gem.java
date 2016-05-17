package match3;

import java.awt.*;

/**
 * Created by kyle on 5/17/16.
 */
public class Gem {
    private Color color;
    private String type = "normal";
    public Gem(Color c) {
        color = c;
    }

    public void draw(Graphics2D art, int x, int y, int bWidth) {
        art.setColor(color);
        int border = bWidth / 5;
        art.fillRect(x + border, y + border, bWidth - border * 2, bWidth - border * 2);
    }
}
