import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
// TODO 3 is no longer needed — Color and Graphics are already imported!
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import core.*;
import mobs.*;


public class Viewer extends JPanel {
	private Font logFont;

    private TextureCache textureCache = new TextureCache();

    private Model gameworld;
    
    private InventoryScreen invScreen;

    public Viewer(Model world) {
        this.gameworld = world;
        this.invScreen = new InventoryScreen(gameworld.getPlayer(), textureCache);
        loadFont();
        playMusic();
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
    	switch(gameworld.getState()) {
    	case INV:
    		invScreen.draw(g);
    		break;
    	default:
            super.paintComponent(g);

            drawRoom(g);

            int x = (int) gameworld.getPlayer().getSprite().getCentre().getX();
            int y = (int) gameworld.getPlayer().getSprite().getCentre().getY();
            int width = (int) gameworld.getPlayer().getSprite().getWidth();
            int height = (int) gameworld.getPlayer().getSprite().getHeight();
            String texture = gameworld.getPlayer().getSprite().getTexture();

            int playerX = gameworld.getPlayerTileX();
            int playerY = gameworld.getPlayerTileY();
            Occupant[][] occupants = gameworld.getOccupants();
            for(int i = 0; i < GameConstants.GRID_SIZE; i++) {
                for(int j = 0; j < GameConstants.GRID_SIZE; j++) {
                    if(occupants[i][j] != null) {
                        int dist = Math.abs(i - playerX) + Math.abs(j - playerY);
                        if(dist <= GameConstants.FOG_MAX_RADIUS) {
                            g.drawImage(textureCache.getImg(occupants[i][j].getTexture()), i * GameConstants.TILE_SIZE, j * GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
                        }
                    }
                }
            }
            drawPlayer(x, y, width, height, texture, g);
            drawLog(g);
    		break;
    	}

    }

    private void drawRoom(Graphics g) {
    	int playerX = gameworld.getPlayerTileX();
    	int playerY = gameworld.getPlayerTileY();
        Tile[][] tiles = gameworld.getRoom();
        String[][] textures = gameworld.getRoomTextures();
        for (int i = 0; i < GameConstants.GRID_SIZE; i++) {
            for (int j = 0; j < GameConstants.GRID_SIZE; j++) {
            	int dist = Math.abs(i - playerX) + Math.abs(j - playerY);
            	if(dist > GameConstants.FOG_MAX_RADIUS) {
            		g.setColor(Color.BLACK);
            		g.fillRect(i * GameConstants.TILE_SIZE, j * GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            	} else {
                    String texturePath;
                    switch (tiles[i][j]) {
                    case DOOR:
                        texturePath = GameConstants.DOOR_TEXTURE;
                        break;
                    case STAIRS:
                    	texturePath = GameConstants.STAIRS_TEXTURE;
                    	break;
                    default:
                        texturePath = textures[i][j];
                        break;
                    }
                    Image textureToRender = textureCache.getImg(texturePath);
                    int startX = GameConstants.TILE_SIZE * i;
                    int startY = GameConstants.TILE_SIZE * j;
                    g.drawImage(textureToRender, startX, startY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
            	}
            }
        }
    }

    private void loadFont() {
        try {
            logFont = Font.createFont(Font.TRUETYPE_FONT, new File(GameConstants.FONT)).deriveFont(Font.PLAIN, 16);
        } catch (Exception e) {
            e.printStackTrace();
            logFont = new Font("Monospaced", Font.PLAIN, 18);
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
    	g.setFont(logFont);
    	g.drawString(gameworld.getPlayer().getGold() + " G", 10, GameConstants.WINDOW_HEIGHT - GameConstants.LOG_HEIGHT);
    	g.drawString(gameworld.getHighScore() + " HS", GameConstants.WINDOW_WIDTH-50, GameConstants.WINDOW_HEIGHT - GameConstants.LOG_HEIGHT);
    	g.setColor(Color.YELLOW);

    	LinkedList<String> log = gameworld.getLog();
    	for(int i = 0; i < log.size(); i++) {
    	    g.drawString(log.get(i), 10, GameConstants.WINDOW_HEIGHT - GameConstants.LOG_HEIGHT + 20 + (i * 20));
    	}
    }
    
    public InventoryScreen getInvScreen() {
    	return invScreen;
    }
    
    public static void playSound(String soundLocation) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundLocation));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	private static void playMusic() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(GameConstants.BG_MUSIC));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}


}
