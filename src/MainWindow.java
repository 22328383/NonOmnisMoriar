import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import util.UnitTests;

public class MainWindow {

    private static JFrame frame = new JFrame("Game"); // Name of your game
    private static Model gameworld = new Model();
    private static Viewer canvas = new Viewer(gameworld);

    private KeyListener controller = new Controller();

    private static int targetFPS = 60;
    private static boolean startGame = false;

    private JLabel backgroundImageForStartMenu;

    public MainWindow() {
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.add(canvas);
        canvas.setBounds(0, 0, 1000, 1000);
        canvas.setBackground(new Color(255, 255, 255));
        canvas.setVisible(false);

        JButton startMenuButton = new JButton("Start Game");
        startMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton.setVisible(false);
                backgroundImageForStartMenu.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(controller);
                canvas.requestFocusInWindow();
                startGame = true;
            }
        });

        startMenuButton.setBounds(400, 500, 200, 40);

        // Load background image
        File backgroundToLoad = new File("res/startscreen.png");
        try {
            BufferedImage myPicture = ImageIO.read(backgroundToLoad);
            backgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
            backgroundImageForStartMenu.setBounds(0, 0, 1000, 1000);
            frame.add(backgroundImageForStartMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(startMenuButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow(); // Sets up environment

        while (true) { // Simple game loop
            int timeBetweenFrames = 1000 / targetFPS;
            long frameCheck = System.currentTimeMillis() + timeBetweenFrames;

            // Wait until next frame
            while (frameCheck > System.currentTimeMillis()) {
            }

            if (startGame) {
                gameloop();
            }

            // Unit test to check framerate
            UnitTests.CheckFrameRate(
                    System.currentTimeMillis(),
                    frameCheck,
                    targetFPS
            );
        }
    }

    // Basic Model-View-Controller pattern
    private static void gameloop() {
        // Model update
        gameworld.gamelogic();

        // View update
        canvas.updateview();

        // Score update
        frame.setTitle("Score = " + gameworld.getScore());
    }
}
