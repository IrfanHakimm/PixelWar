package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class Slime extends Entity {

    private GamePanel gp;
    public final int screenX;
    public final int screenY;

    public Slime(GamePanel gp) {
        super(gp);

        type = 2;
        speed = 3;
        direction = "down";
        maxLife = 1;
        life = maxLife;

        this.gp = gp;
        solidArea = new Rectangle();
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea.x = 2;
        solidArea.y = 4;
        solidArea.width = gp.tileSize * 2;
        solidArea.height = gp.tileSize * 2;
        getMonsterImage();
    }

    public void getMonsterImage() {
        try {
            up1 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_1.png"));
            down1 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_1.png"));
            left1 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_1.png"));
            right1 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_1.png"));

            up2 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_2.png"));
            down2 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_2.png"));
            left2 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_2.png"));
            right2 = ImageIO.read(getClass().getResource("/monsters/greenslime_down_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spritenum == 1) {
                        image = up1;
                    }
                    if (spritenum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spritenum == 1) {
                        image = down1;
                    }
                    if (spritenum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spritenum == 1) {
                        image = left1;
                    }
                    if (spritenum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spritenum == 1) {
                        image = right1;
                    }
                    if (spritenum == 2) {
                        image = right2;
                    }
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
