package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import object.OBJ_Heart;
import object.SuperObject;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font modernDOS;
    BufferedImage heart_full, heart_half, heart_blank;

    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/ModernDOS9x16.ttf");
            modernDOS = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(modernDOS);
        g2.setColor(Color.WHITE);
        // title
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // Pause
        if (gp.gameState == gp.pauseState) {
            drawPuseScreen();
            drawPlayerLife();
        }
        // play
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            // nothing
        }
        // Game Over
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

        // create hud
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));

        text = "Game Over";
        g2.setColor(Color.black);
        x = getXforCenter(text);
        y = gp.tileSize * 3;
        g2.drawString(text, x, y);
        g2.setColor(Color.BLACK);

        g2.setColor(Color.white);
        g2.drawString(text, x + 5, y + 5);

        g2.setFont(g2.getFont().deriveFont(35F));
        text = "RESTART";
        x = getXforCenter(text);
        y += gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT TO MENU";
        x = getXforCenter(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPuseScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        String text = "PAUSED";
        int x = getXforCenter(text);
        int y = gp.screenHeight / 2;
        g2.setColor(new Color(255, 255, 255));
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
        String text_info = "Press ESC to continue";
        x = getXforCenter(text_info);
        y = gp.screenHeight - (gp.screenHeight / 8);
        g2.drawString(text_info, x, y);
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2; // Starting x-coordinate
        int y = gp.tileSize / 4; // Starting y-coordinate
        int i = 0;

        // Draw max life
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, (int) (gp.tileSize * 1), (int) (gp.tileSize * 1), null);
            i++;
            x += (int) (gp.tileSize * 1); // Increase x-coordinate to create gap between images
        }

        x = gp.tileSize / 2; // Reset x-coordinate
        y = gp.tileSize / 4; // Reset y-coordinate
        i = 0;

        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, (int) (gp.tileSize * 1), (int) (gp.tileSize * 1), null);
            i++;

            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, (int) (gp.tileSize * 1), (int) (gp.tileSize * 1), null);
            }

            i++;
            x += (int) (gp.tileSize * 1); // Increase x-coordinate to create gap between images
        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
        String text = "PIXEL WAR";
        int x = getXforCenter(text);
        int y = gp.tileSize * 3;
        // Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);
        // Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

        text = "START";
        x = getXforCenter(text);
        y += gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenter(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public int getXforCenter(String text) {
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - lenght / 2;
        return x;
    }
}
