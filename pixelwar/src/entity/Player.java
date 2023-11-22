package entity;

import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends Entity {

    private GamePanel gp;
    private KeyHandler keyH;
    private BufferedImage[] up, down, left, right;
    private int currentFrame = 0;
    private int totalFrames = 4;
    private boolean isMoving = false;
    private long lastFrameChangeTime;
    private long frameChangeDelay = 100;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImages();
        setDefaultValues();
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        lastFrameChangeTime = System.currentTimeMillis();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize *23;
        worldY = gp.tileSize *21;
        speed = 8;
        direction = "down";
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            worldY -= speed;
            isMoving = true;
            updateAnimation();
        } else if (keyH.downPressed) {
            direction = "down";
            worldY += speed;
            isMoving = true;
            updateAnimation();
        } else if (keyH.leftPressed) {
            direction = "left";
            worldX -= speed;
            isMoving = true;
            updateAnimation();
        } else if (keyH.rightPressed) {
            direction = "right";
            worldX += speed;
            isMoving = true;
            updateAnimation();
        } else {
            isMoving = false;
        }
        
        if (!isMoving) {
            switch (direction) {
                case "up":
                    currentFrame = 0;
                    break;
                case "down":
                    currentFrame = 0;
                    break;
                case "left":
                    currentFrame = 0;
                    break;
                case "right":
                    currentFrame = 0;
                    break;
            }
        }
    }

    private void updateAnimation() {
        long now = System.currentTimeMillis();

        // Check if enough time has passed to change the frame
        if (now - lastFrameChangeTime > frameChangeDelay) {
            currentFrame = (currentFrame + 1) % totalFrames;
            lastFrameChangeTime = now;
        }
    }

    public void getPlayerImages() {
        try {
            up = loadFrames("/player/Character_Up.png");
            down = loadFrames("/player/Character_Down.png");
            left = loadFrames("/player/Character_Left.png");
            right = loadFrames("/player/Character_Right.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage[] loadFrames(String path) throws Exception {
        BufferedImage[] frames = new BufferedImage[totalFrames];
        BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
        int frameWidth = spriteSheet.getWidth() / totalFrames;

        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, spriteSheet.getHeight());
        }

        return frames;
    }

    public void draw(Graphics2D g2) {
        BufferedImage[] frames = null;

        switch (direction) {
            case "up":
                frames = up;
                break;
            case "down":
                frames = down;
                break;
            case "left":
                frames = left;
                break;
            case "right":
                frames = right;
                break;
        }

        BufferedImage image = frames[currentFrame];
        g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
    }
}
