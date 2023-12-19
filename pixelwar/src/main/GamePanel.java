package main;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import monster.Slime;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 768px
    public int screenHeight = tileSize * maxScreenRow; // 576px

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldwidth = tileSize * maxWorldCol;
    public final int worldheight = tileSize * maxWorldRow;

    int fps = 60;

    // system
    public TileManager tm = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    public UI ui = new UI(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager env = new EnvironmentManager(this);
    Thread gameThread;

    // game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int winState = 4;

    // Entity
    public Player player = new Player(this, keyH);
    public Entity slime[] = new Slime[20];
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setSlime();
        // env.setup();
        gameState = titleState;
    }

    public void respawnMonster() {
        if (Arrays.stream(slime).allMatch(Objects::isNull)) {
            aSetter.setSlime();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void restart() {
        player.setDefaultValues();
        aSetter.setSlime();
    }

    public void run() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void update() {
        if (gameState == playState) {
            player.update();
            respawnMonster();

            // Update goblin clubs
            for (int i = 0; i < slime.length; i++) {
                if (slime[i] != null) {
                    slime[i].update();
                }
            }
        }
        if (gameState == pauseState) {
            // Handle pause state updates here
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tm.draw(g2);
            // player
            entityList.add(player);

            // Monster
            for (int i = 0; i < slime.length; i++) {
                if (slime[i] != null) {
                    entityList.add(slime[i]);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // Draw Entity
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // ResetDraw
            entityList.clear();

            // env.draw(g2);
            ui.draw(g2);

        }
        g2.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
