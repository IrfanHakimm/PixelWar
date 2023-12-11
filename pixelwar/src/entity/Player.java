package entity;

import main.KeyHandler;

import java.awt.AlphaComposite;
// import java.awt.Color;
// import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize * 2;
        solidArea.height = gp.tileSize * 2;
        lastFrameChangeTime = System.currentTimeMillis();
        getPlayerImages();
        setDefaultValues();
        getPlayerAttackImages();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 8;
        direction = "down";

        // status
        maxLife = 6;
        life = maxLife;
        invincible = false;
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

    public void getPlayerAttackImages() {
        try {
            attackUpRight = loadFrames("/player/Sword_UpRight.png");
            attackUpRight = loadFrames("/player/Sword_UpLeft.png");
            attackDownRight = loadFrames("/player/Sword_DownRight.png");
            attackDownLeft = loadFrames("/player/Sword_DownLeft.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        isMoving = false;

        if (keyH.upPressed) {
            direction = "up";
            isMoving = true;
        } else if (keyH.downPressed) {
            direction = "down";
            isMoving = true;
        } else if (keyH.leftPressed) {
            direction = "left";
            isMoving = true;
        } else if (keyH.rightPressed) {
            direction = "right";
            isMoving = true;
        }

        collisionOn = false;
        gp.cChecker.CheckTileForPlayer(this);
        int slimeIndex = gp.cChecker.checkEntity(this, gp.slime);
        interactMonster(slimeIndex);
        contactMonster(slimeIndex);

        if (collisionOn == false && isMoving) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    updateAnimation();
                    break;
                case "down":
                    worldY += speed;
                    updateAnimation();
                    break;
                case "left":
                    worldX -= speed;
                    updateAnimation();
                    break;
                case "right":
                    worldX += speed;
                    updateAnimation();
                    break;
            }
        }

        if (!isMoving) {
            currentFrame = 0;
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (life <= 0) {
            gp.playSE(2);
            gp.gameState = gp.gameOverState;
        }
    }

    public void interactMonster(int i) {
        if (i != 999) {
            System.out.println("Slime collided with player");
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                life -= i;
                invincible = true;
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

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        BufferedImage image = frames[currentFrame];
        g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        // DEBUG INVINCIBLE
        // g2.setFont(new Font("Arial", Font.PLAIN, 26));
        // g2.setColor(Color.white);
        // g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}