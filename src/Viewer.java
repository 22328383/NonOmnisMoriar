import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Viewer extends JPanel {

    private long currentAnimationTime = 0;

    private Model gameworld = new Model();

    public Viewer(Model world) {
        this.gameworld = world;
    }

    public Viewer(LayoutManager layout) {
        super(layout);
    }

    public Viewer(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public void updateview() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentAnimationTime++;

        // Draw background
        drawBackground(g);

        // Draw player
        int x = (int) gameworld.getPlayer().getCentre().getX();
        int y = (int) gameworld.getPlayer().getCentre().getY();
        int width = (int) gameworld.getPlayer().getWidth();
        int height = (int) gameworld.getPlayer().getHeight();
        String texture = gameworld.getPlayer().getTexture();

        drawPlayer(x, y, width, height, texture, g);
    }

    private void drawBackground(Graphics g) {
        File textureToLoad = new File("res/spacebackground.png");
        try {
            Image myImage = ImageIO.read(textureToLoad);
            g.drawImage(myImage, 0, 0, 1000, 1000, 0, 0, 1000, 1000, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBullet(int x, int y, int width, int height, String texture, Graphics g) {
        File textureToLoad = new File(texture);
        try {
            Image myImage = ImageIO.read(textureToLoad);
            g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 63, 127, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawPlayer(int x, int y, int width, int height, String texture, Graphics g) {
        File textureToLoad = new File(texture);
        try {
            Image myImage = ImageIO.read(textureToLoad);

            int currentPositionInAnimation =
                    ((int) ((currentAnimationTime % 40) / 10)) * 32;

            g.drawImage(
                    myImage,
                    x, y, x + width, y + height,
                    currentPositionInAnimation, 0,
                    currentPositionInAnimation + 31, 32,
                    null
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
