import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Model {
    private GameObject player;
    private Controller controller = Controller.getInstance();
    private int score = 0;
    private Tile[][] room;
    private int playerX;
    private int playerY;

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

        room = new Tile[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        for(int i = 0; i < GameConstants.GRID_SIZE; i++) {
            for(int j = 0; j < GameConstants.GRID_SIZE; j++) {
                if(i == 0 || i == GameConstants.GRID_SIZE - 1 || j == 0 || j == GameConstants.GRID_SIZE - 1) {
                    room[i][j] = Tile.VOID;
                } else if(i == 1 || i == GameConstants.GRID_SIZE - 2 || j == 1 || j == GameConstants.GRID_SIZE - 2) {
                    room[i][j] = Tile.WALL;
                } else {
                    room[i][j] = Tile.FLOOR;
                }
            }
        }
    }

    public void gamelogic() {
        playerLogic();
    }

    public void computeLocation() {
        playerX = (int) (player.getCentre().getX() / GameConstants.TILE_SIZE);
        playerY = (int) (player.getCentre().getY() / GameConstants.TILE_SIZE);
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public Tile roomRight() {
        computeLocation();
        if(playerX + 1 < GameConstants.GRID_SIZE) {
            return room[playerX+1][playerY];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomLeft() {
        computeLocation();
        if(playerX - 1 >= 0) {
            return room[playerX-1][playerY];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomDown() {
        computeLocation();
        if(playerY + 1 < GameConstants.GRID_SIZE) {
            return room[playerX][playerY+1];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomUp() {
        computeLocation();
        if(playerY - 1 >= 0) {
            return room[playerX][playerY-1];
        } else {
            return Tile.NOTHING;
        }
    }

    private void playerLogic() {
        if(controller.isKeyAPressed()) {
            Controller.getInstance().setKeyAPressed(false);
            if(roomLeft() == Tile.FLOOR) {
                player.getCentre().ApplyVector(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            }
        }

        if(controller.isKeyDPressed()) {
            Controller.getInstance().setKeyDPressed(false);
            if(roomRight() == Tile.FLOOR) {
                player.getCentre().ApplyVector(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            }
        }

        if(controller.isKeyWPressed()) {
            Controller.getInstance().setKeyWPressed(false);
            if(roomUp() == Tile.FLOOR) {
                player.getCentre().ApplyVector(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            }
        }

        if(controller.isKeySPressed()) {
            Controller.getInstance().setKeySPressed(false);
            if(roomDown() == Tile.FLOOR) {
                player.getCentre().ApplyVector(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            }
        }

        if(controller.isKeySpacePressed()) {
            Controller.getInstance().setKeySpacePressed(false);
        }
    }

    public GameObject getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public Tile[][] getRoom() {
        return room;
    }
}
