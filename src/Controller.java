import core.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

// Singleton pattern
public class Controller implements KeyListener, MouseListener {

	private static boolean keyAPressed = false;
    private static boolean keySPressed = false;
    private static boolean keyDPressed = false;
    private static boolean keyWPressed = false;
    private static boolean keySpacePressed = false;
    private static boolean keyIPressed = false;
    private static int clickX = -1;
    private static int clickY = -1;
    private static boolean clicked = false;

    private static final Controller instance = new Controller();

    public Controller() {
    }

    public static Controller getInstance() {
        return instance;
    }

    @Override
    // Key typed (not used here)
    public void keyTyped(KeyEvent e) {
    }

    @Override
    // Key pressed (keeps triggering while held)
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                setKeyAPressed(true);
                break;
            case 's':
                setKeySPressed(true);
                break;
            case 'w':
                setKeyWPressed(true);
                break;
            case 'd':
                setKeyDPressed(true);
                break;
            case ' ':
                setKeySpacePressed(true);
                break;
            case 'i':
            	setKeyIPressed(true);
            	break;
            default:
                // Unknown key
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                setKeyAPressed(false);
                break;
            case 's':
                setKeySPressed(false);
                break;
            case 'w':
                setKeyWPressed(false);
                break;
            case 'd':
                setKeyDPressed(false);
                break;
            case ' ':
                setKeySpacePressed(false);
                break;
            case 'i':
            	setKeyIPressed(false);
            	break;
            default:
                // Unknown key
                break;
        }
    }
    
    public void mouseClicked(MouseEvent event) {
        clickX = event.getX()/GameConstants.TILE_SIZE;
        clickY = event.getY()/GameConstants.TILE_SIZE;
        clicked = true;
    }
    
    public boolean isClicked() {
    	return clicked;
    }
    public int getClickX() {
    	return clickX;
    }
    public int getClickY() {
    	return clickY;
    }
    public void setClicked(boolean c) {
    	clicked = c;
    }

    public boolean isKeyAPressed() {
        return keyAPressed;
    }

    public void setKeyAPressed(boolean keyAPressed) {
        Controller.keyAPressed = keyAPressed;
    }

    public boolean isKeySPressed() {
        return keySPressed;
    }

    public void setKeySPressed(boolean keySPressed) {
        Controller.keySPressed = keySPressed;
    }

    public boolean isKeyDPressed() {
        return keyDPressed;
    }

    public void setKeyDPressed(boolean keyDPressed) {
        Controller.keyDPressed = keyDPressed;
    }

    public boolean isKeyWPressed() {
        return keyWPressed;
    }

    public void setKeyWPressed(boolean keyWPressed) {
        Controller.keyWPressed = keyWPressed;
    }

    public boolean isKeySpacePressed() {
        return keySpacePressed;
    }

    public void setKeySpacePressed(boolean keySpacePressed) {
        Controller.keySpacePressed = keySpacePressed;
    }
    
    public boolean isKeyIPressed() {
    	return keyIPressed;
    }
    
    public void setKeyIPressed(boolean keyIPressed) {
    	Controller.keyIPressed = keyIPressed;
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
