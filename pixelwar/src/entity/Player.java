package entity;

import main.KeyHandler;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Player extends Entity {

    private GamePanel gp;
    private KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        attackArea.width = 36;
        attackArea.height = 36;
        getPlayerImages();
        setDefaultValues();
        getPlayerImages();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 8;
        direction = "down";

        // status
        maxLife = 6;
        life = maxLife;
        invincible = false;
    }

    public void getPlayerImages() {

        up1 = setup("/player/McUp_0", gp.tileSize, gp.tileSize);
        up2 = setup("/player/McUp_1", gp.tileSize, gp.tileSize);
        up3 = setup("/player/McUp_2", gp.tileSize, gp.tileSize);
        up4 = setup("/player/McUp_3", gp.tileSize, gp.tileSize);

        down1 = setup("/player/McDown_0", gp.tileSize, gp.tileSize);
        down2 = setup("/player/McDown_1", gp.tileSize, gp.tileSize);
        down3 = setup("/player/McDown_2", gp.tileSize, gp.tileSize);
        down4 = setup("/player/McDown_3", gp.tileSize, gp.tileSize);

        left1 = setup("/player/McKiri_0", gp.tileSize, gp.tileSize);
        left2 = setup("/player/McKiri_1", gp.tileSize, gp.tileSize);
        left3 = setup("/player/McKiri_2", gp.tileSize, gp.tileSize);
        left4 = setup("/player/McKiri_3", gp.tileSize, gp.tileSize);

        right1 = setup("/player/McRight_0", gp.tileSize, gp.tileSize);
        right2 = setup("/player/McRight_1", gp.tileSize, gp.tileSize);
        right3 = setup("/player/McRight_2", gp.tileSize, gp.tileSize);
        right4 = setup("/player/McRight_3", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAttackImage() {

        attackUp1 = setup("/player/Atas1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/Atas2", gp.tileSize, gp.tileSize * 2);

        attackDown1 = setup("/player/Bawah1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/Bawah2", gp.tileSize, gp.tileSize * 2);

        attackLeft1 = setup("/player/Kiri1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/Kiri2", gp.tileSize * 2, gp.tileSize);

        attackRight1 = setup("/player/Kanan1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/Kanan2", gp.tileSize * 2, gp.tileSize);

    }

    public void update() {

        if (attacking) {
            attacking();
        } else {
            // Check if the attack key is pressed
            if (keyH.enterPressed) {
                attacking = true;
                keyH.enterPressed = false; // Reset the key press to avoid continuous attacks
            } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

                if (keyH.upPressed == true) {
                    direction = "up";
                } else if (keyH.downPressed == true) {
                    direction = "down";
                } else if (keyH.leftPressed == true) {
                    direction = "left";
                } else if (keyH.rightPressed == true) {
                    direction = "right";
                }

                // CheckTile
                collisionOn = false;
                gp.cChecker.CheckTile(this);

                // Check Slime Collision
                int monsterIndex = gp.cChecker.checkEntity(this, gp.slime);
                contactMonster(monsterIndex);
                interactMonster(monsterIndex);

                if (collisionOn == false && keyH.enterPressed == false) {
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

                gp.keyH.enterPressed = false;

                spritecounter++;
                if (spritecounter > 5) {
                    if (spritenum == 1) {
                        spritenum = 2;
                    } else if (spritenum == 2) {
                        spritenum = 3;
                    } else if (spritenum == 3) {
                        spritenum = 4;
                    } else if (spritenum == 4) {
                        spritenum = 1;
                    }
                    spritecounter = 0;
                }
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

            if (life > 0 && gp.ui.remainingTime < 0) {
                gp.gameState = gp.winState;
            }
        }

    }

    public void attacking() {

        spritecounter++;
        if (spritecounter <= 5) {
            spritenum = 1;
        }
        if (spritecounter > 5 && spritecounter <= 25) {
            spritenum = 2;

            // Save current worldX and worldY
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Set worldX and worldY to attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // Attack Area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.slime);
            damageMonster(monsterIndex);

            // Restore worldX and worldY
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spritecounter > 25) {
            spritenum = 1;
            spritecounter = 0;
            attacking = false;

        }

    }

    public void interactMonster(int i) {
        if (i != 999) {
            if (gp.keyH.enterPressed == true) {
                attacking = true;
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if (i != 999) {
            if (gp.slime[i].invincible == false) {
                gp.slime[i].life -= 1;
                gp.slime[i].invincible = true;

                if (gp.slime[i].life <= 0) {
                    gp.slime[i] = null;

                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spritenum == 1) {
                        image = up1;
                    }
                    if (spritenum == 2) {
                        image = up2;
                    }
                    if (spritenum == 3) {
                        image = up3;
                    }
                    if (spritenum == 4) {
                        image = up4;
                    }
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spritenum == 1) {
                        image = attackUp1;
                    }
                    if (spritenum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spritenum == 1) {
                        image = down1;
                    }
                    if (spritenum == 2) {
                        image = down2;
                    }
                    if (spritenum == 3) {
                        image = down3;
                    }
                    if (spritenum == 4) {
                        image = down4;
                    }
                }
                if (attacking == true) {
                    if (spritenum == 1) {
                        image = attackDown1;
                    }
                    if (spritenum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spritenum == 1) {
                        image = left1;
                    }
                    if (spritenum == 2) {
                        image = left2;
                    }
                    if (spritenum == 3) {
                        image = left3;
                    }
                    if (spritenum == 4) {
                        image = left4;
                    }
                }

                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spritenum == 1) {
                        image = attackLeft1;
                    }
                    if (spritenum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spritenum == 1) {
                        image = right1;
                    }
                    if (spritenum == 2) {
                        image = right2;
                    }
                    if (spritenum == 3) {
                        image = right3;
                    }
                    if (spritenum == 4) {
                        image = right4;
                    }
                }

                if (attacking == true) {
                    if (spritenum == 1) {
                        image = attackRight1;
                    }
                    if (spritenum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }
}