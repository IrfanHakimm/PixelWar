package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
// import java.util.Random;

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
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getMonsterImage();
    }

    public void getMonsterImage() {
        up1 = setup("/monsters/greenslime_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/greenslime_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/greenslime_down_1", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/greenslime_down_1", gp.tileSize, gp.tileSize);

        up2 = setup("/monsters/greenslime_down_2", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/greenslime_down_2", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/greenslime_down_2", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {

        chasePlayer();

        actionLockCounter++;

        // if (actionLockCounter == 120) {

        // Random random = new Random();
        // int i = random.nextInt(100) + 1;

        // if (i <= 25) {
        // direction = "up";
        // }
        // if (i > 25 && i <= 50) {
        // direction = "down";
        // }
        // if (i > 50 && i <= 75) {
        // direction = "left";
        // }
        // if (i > 75 && i <= 100) {
        // direction = "right";
        // }
        // actionLockCounter = 0;
        // }
    }

    public void chasePlayer() {
        int playerCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int playerRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

        int slimeCol = (worldX + solidArea.x) / gp.tileSize;
        int slimeRow = (worldY + solidArea.y) / gp.tileSize;

        // Calculate the direction to move towards the player
        if (playerCol < slimeCol) {
            direction = "left";
        } else if (playerCol > slimeCol) {
            direction = "right";
        } else if (playerRow < slimeRow) {
            direction = "up";
        } else if (playerRow > slimeRow) {
            direction = "down";
        }

        // Move the slime towards the player
        switch (direction) {
            case "up":
                direction = "up";
                break;
            case "down":
                direction = "down";
                break;
            case "left":
                direction = "left";
                break;
            case "right":
                direction = "right";
                break;
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

            if (invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
    }
}
