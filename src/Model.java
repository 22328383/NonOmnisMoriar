import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Model {
	private int Money = 0;
	private boolean isFishing = false;
    private GameObject player;
    private Controller controller = Controller.getInstance();
    private int score = 0;
    private int[][] room;
   

    public Model() {
        player = new GameObject(
                GameConstants.HERO_TEXTURE,
                GameConstants.TILE_SIZE,
                GameConstants.TILE_SIZE,
                new Point3f(
                    GameConstants.PLAYER_START_X * GameConstants.TILE_SIZE,
                    GameConstants.PLAYER_START_Y * GameConstants.TILE_SIZE,
                    0
                )
        );

        room = new int[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        for(int i = 0; i < GameConstants.GRID_SIZE; i++) {
            for(int j = 0; j < GameConstants.GRID_SIZE; j++) {
                if(i == 0 || i == GameConstants.GRID_SIZE - 1 || j == 0 || j == GameConstants.GRID_SIZE - 1) {
                    room[i][j] = 0;
                } else if(i == 1 || i == GameConstants.GRID_SIZE - 2 || j == 1 || j == GameConstants.GRID_SIZE - 2) {
                    room[i][j] = 1;
                } else {
                    room[i][j] = 2;
                }
            }
        }
    }

    // This is the heart of the game: the model takes in all inputs,
    // decides outcomes, and updates the model accordingly.
    public void gamelogic() {
        // Player logic first
        playerLogic();

        // Interactions between objects
        gameLogic();
    }

    private void gameLogic() {
        // Increment across the array list

        // See if they hit anything
        // Using enhanced for-loop style as it makes it easier to read
    }

    private void playerLogic() {
        if(controller.isKeyAPressed()) {
            player.getCentre().ApplyVector(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            Controller.getInstance().setKeyAPressed(false);
        }

        if(controller.isKeyDPressed()) {
            player.getCentre().ApplyVector(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            Controller.getInstance().setKeyDPressed(false);
        }

        if(controller.isKeyWPressed()) {
            player.getCentre().ApplyVector(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            Controller.getInstance().setKeyWPressed(false);
        }

        if(controller.isKeySPressed()) {
            player.getCentre().ApplyVector(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            Controller.getInstance().setKeySPressed(false);
        }
        if(controller.isKeySpacePressed()) {
        	castRod();
        	Controller.getInstance().setKeySpacePressed(false);
        }

    }

    public GameObject getPlayer() {
        return player;
    }

    public int getScore() {
        return Money;
    }

    private void castRod() {
        if(!isFishing) {
            isFishing = true;
            System.out.println("Rod cast!");
        } else {
            isFishing = false;
            System.out.println("Reeled in!");
            Money = Money + 10;
        }
    }

    public int[][] getRoom() {
    	return room;
    }
}
