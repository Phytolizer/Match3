package match3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

/**
 * Created by kyle on 5/17/16.
 */
public class Graphics implements Runnable, KeyListener, WindowListener {
    private boolean isRunning;
    private boolean isDone;
    private FancyFrame frame;
    private Image imgBuffer;

    private Grid grid;
    private Coordinate clickingCoords;
    private boolean canSwitch = true;

    private Color bgColor = new Color(128, 128, 255);
    public Graphics() {
        frame = new FancyFrame("Match 3 Attempt", new Dimension(800, 600));
        frame.addKeyListener(this);
        frame.addWindowListener(this);
        isRunning = true;
        isDone = false;
        frame.setVisible(true);
        imgBuffer = frame.createImage(frame.getWidth(), frame.getHeight());
        grid = new Grid(8, 8);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(false);
        isRunning = false;
        frame.dispose();
        isDone = true;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        while(true) {
            if(isDone)
                System.exit(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void run() {
        while(isRunning) {
            Coordinate mouseCoords = grid.getCoordsAt(frame.mouseX, frame.mouseY);
            if(mouseCoords != null) {
                if(clickingCoords == null) {
                    if(frame.isClicking()) {
                        clickingCoords = mouseCoords;
                    }
                } else if(frame.isClicking() && !mouseCoords.equals(clickingCoords) && canSwitch) {
                    frame.setTitle("" + Direction.getDirection(clickingCoords, mouseCoords));
                    grid.switchGems(clickingCoords, Direction.getDirection(clickingCoords, mouseCoords));
                    canSwitch = false;
                    clickingCoords = mouseCoords;
                }
            }
            if(clickingCoords != null && !frame.isClicking()) {
                clickingCoords = null;
            }
            if(!frame.isClicking() && !canSwitch) {
                canSwitch = true;
            }
            draw();
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void draw() {
        Graphics2D art = (Graphics2D) imgBuffer.getGraphics();
        art.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        art.setColor(bgColor);
        art.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        grid.draw(frame, art);

        art = (Graphics2D) frame.getGraphics();
        if(art != null) {
            imgBuffer = Resizer.PROGRESSIVE_BILINEAR.resize((BufferedImage) imgBuffer, frame.getWidth(), frame.getHeight());
            art.drawImage(imgBuffer, 0, 0, frame.getWidth(), frame.getHeight(), null);
            art.dispose();
        }
    }
}
