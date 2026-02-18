import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

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
        drawLog(g);

        // Draw player
        int x = (int) gameworld.getPlayer().getCentre().getX();
        int y = (int) gameworld.getPlayer().getCentre().getY();
        int width = (int) gameworld.getPlayer().getWidth();
        int height = (int) gameworld.getPlayer().getHeight();
        String texture = gameworld.getPlayer().getTexture();

        LinkedList<Enemy> mobs = gameworld.getMobs();
        for(int i = 0; i < mobs.size(); i++) {
        	g.drawImage(textureCache.getImg(mobs.get(i).getTexture()), mobs.get(i).getX() * GameConstants.TILE_SIZE, mobs.get(i).getY() * GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
        }
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
    
    private void drawLog(Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(0, (GameConstants.WINDOW_HEIGHT-GameConstants.LOG_HEIGHT), GameConstants.WINDOW_WIDTH, GameConstants.LOG_HEIGHT);
    	
    	g.setColor(Color.WHITE);
    	g.setFont(new Font("Monospaced", Font.PLAIN, 14));
    	LinkedList<String> log = gameworld.getLog();
    	for(int i = 0; i < log.size(); i++) {
    	    g.drawString(log.get(i), 10, GameConstants.WINDOW_HEIGHT - GameConstants.LOG_HEIGHT + 20 + (i * 20));
    	}
    }
}
