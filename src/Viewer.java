import java.awt.Graphics;

import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Viewer extends JPanel {

    private TextureCache textureCache = new TextureCache();

    private Model gameworld;

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

        // Draw room
        drawRoom(g);

        // Draw player
        int x = (int) gameworld.getPlayer().getCentre().getX();
        int y = (int) gameworld.getPlayer().getCentre().getY();
        int width = (int) gameworld.getPlayer().getWidth();
        int height = (int) gameworld.getPlayer().getHeight();
        String texture = gameworld.getPlayer().getTexture();

        drawPlayer(x, y, width, height, texture, g);
    }

    private void drawRoom(Graphics g) {
        Tile[][] tiles = gameworld.getRoom();
        for (int i = 0; i < GameConstants.GRID_SIZE; i++) {
            for (int j = 0; j < GameConstants.GRID_SIZE; j++) {
                String texturePath;
                switch (tiles[i][j]) {
                case VOID:
                    texturePath = GameConstants.VOID_TEXTURE;
                    break;
                case FLOOR:
                    texturePath = GameConstants.FLOOR_TEXTURE;
                    break;
                case WALL:
                    texturePath = GameConstants.WALL_TEXTURE;
                    break;
                case DOOR:
                    texturePath = GameConstants.DOOR_TEXTURE;
                    break;
                default:
                    texturePath = GameConstants.VOID_TEXTURE;
                    break;
                }
                Image textureToRender = textureCache.getImg(texturePath);
                int startX = GameConstants.TILE_SIZE * i;
                int startY = GameConstants.TILE_SIZE * j;
                g.drawImage(textureToRender, startX, startY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
            }
        }
    }

    private void drawPlayer(int x, int y, int width, int height, String texture, Graphics g) {
        Image playerImg = textureCache.getImg(texture);
        g.drawImage(playerImg, x, y, width, height, null);
    }
}
