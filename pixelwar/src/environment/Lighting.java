package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {
        // create buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // membuat kotak screen area
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        // mencari center point dari x dan y
        int centerX = gp.player.screenX + (gp.tileSize);
        int centerY = gp.player.screenY + (gp.tileSize);

        // mencari jarak atas dari x dan y dari center
        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        // membuat bentuk lingkaran
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // membuat light circle area
        Area lightArea = new Area(circleShape);

        // mengsubstract area circle dari kotak screen area\
        screenArea.subtract(lightArea);

        // membuat gradasi
        Color color[] = new Color[5];
        float fraction[] = new float[5];

        color[0] = new Color(0, 0, 0, 0);
        color[1] = new Color(0, 0, 0, 0.25f);
        color[2] = new Color(0, 0, 0, 0.5f);
        color[3] = new Color(0, 0, 0, 0.75f);
        color[4] = new Color(0, 0, 0, 0.98f);

        fraction[0] = 0f;
        fraction[1] = 0.25f;
        fraction[2] = 0.5f;
        fraction[3] = 0.75f;
        fraction[4] = 0.95f;

        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize / 2), fraction, color);
        g2.setPaint(gPaint);
        g2.fill(lightArea);

        g2.setColor(new Color(0, 0, 0, 0.98f));
        g2.fill(screenArea);
        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
