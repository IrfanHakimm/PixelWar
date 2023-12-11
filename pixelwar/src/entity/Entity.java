package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    public GamePanel gp;
    public int worldX;
    public int worldY;
    public String name;
    public int speed;
    public BufferedImage up1, down1, right1, left1, up2, down2, right2, left2, up3, down3, right3, left3, up4, down4,
            right4, left4;
    public BufferedImage[] attackUpRight, attackUPLeft, attackDownLeft, attackDownRight;
    public Rectangle solidArea = new Rectangle(8, 16, 32, 32);

    // State
    public int spritenum = 1;
    public boolean invincible = false;
    public String direction = "down";
    public boolean collisionOn = false;
    public int totalFrames;
    public int currentFrame = 0;
    boolean attacking = false;

    // Counter
    public int spritecounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;

    // char status
    public int maxLife;
    public int life;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int type; // player = 1, monster = 2

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void draw(Graphics2D g2) {

    }

    public void update() {

        setAction();
        collisionOn = false;
        gp.cChecker.CheckTile(this);
        spritecounter++;
        if (spritecounter % 10 == 0) { // Change the number for animation speed
            spritenum = (spritenum % 2) + 1; // Loop through frames 1 to 4
        }

        collisionOn = false;
        gp.cChecker.CheckTile(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (gp.player.invincible == false) {
                gp.player.life--;
                gp.player.invincible = true;
            }
        }

        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

    }
}
