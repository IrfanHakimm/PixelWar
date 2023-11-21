package entity;

import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends Entity {

    private GamePanel gp;
    private KeyHandler keyH;
    private BufferedImage[] upFrames, downFrames, leftFrames, rightFrames;
    private int currentFrame = 0;
    private int totalFrames = 4;
    private int animationSpeed = 8;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImages();
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            y -= speed;
            updateAnimation();
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
            updateAnimation();
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
            updateAnimation();
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
            updateAnimation();
        }
    }

    private void updateAnimation() {
        // Update animation frame only when the player is moving
        currentFrame = (currentFrame + 1) % totalFrames;
    }


    public void getPlayerImages() {
        try {
            upFrames = loadFrames("/player/Character_Up.png");
            downFrames = loadFrames("/player/Character_Down.png");
            leftFrames = loadFrames("/player/Character_Left.png");
            rightFrames = loadFrames("/player/Character_Right.png");
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
                frames = upFrames;
                break;
            case "down":
                frames = downFrames;
                break;
            case "left":
                frames = leftFrames;
                break;
            case "right":
                frames = rightFrames;
                break;
        }

        BufferedImage image = frames[currentFrame];
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
