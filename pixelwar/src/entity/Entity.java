package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    public GamePanel gp;
    public int worldX;
    public int worldY;
    public String name;
    public int speed;
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2,
            right3, right4;
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    // State
    public int spritenum = 1;
    public boolean invincible = false;
    public String direction = "down";
    public boolean collisionOn = false;
    boolean attacking = false;
    public boolean onPath = false;

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

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (gp.pFinder.search() == true) {
            int nextX = gp.pFinder.pathList.get(0).col + gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row + gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX)
                    direction = "left";
                if (enLeftX < nextX)
                    direction = "right";
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";

                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";

                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";

                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";

                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }

            // int nextCol = gp.pFinder.pathList.get(0).col;
            // int nextRow = gp.pFinder.pathList.get(0).row;
            // if (nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }
    }

    public boolean checkEntityCollision(Entity entity) {
        Rectangle thisBounds = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width,
                solidArea.height);
        Rectangle otherBounds = new Rectangle(entity.worldX + entity.solidArea.x, entity.worldY + entity.solidArea.y,
                entity.solidArea.width, entity.solidArea.height);

        return thisBounds.intersects(otherBounds);
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.CheckTile(this);
        gp.cChecker.checkEntity(this, gp.slime);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        spritecounter++;

        if (this.type == 2 && contactPlayer == true) {
            if (gp.player.invincible == false) {
                gp.player.life--;
                gp.player.invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {

    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {

        setAction();
        checkCollision();

        if (spritecounter % 10 == 0) { // Change the number for animation speed
            spritenum = (spritenum % 2) + 1; // Loop through frames 1 to 4
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

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }
}
