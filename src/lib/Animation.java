package lib;
/*
 * Animation.java
 * Создает анимацию из массива спрайтов
 */

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Animation {

    private static double currentFrame = 0;

    public static void animate(Graphics g, BufferedImage[] sprites, int x, int y, double speedOfAnimation, double angle) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform trans = g2d.getTransform();
        AffineTransform at = new AffineTransform();

        at.rotate(angle, x + 25, y + 25);
        g2d.transform(at);

        g2d.drawImage(sprites[(int) (Math.round(currentFrame))], x, y, null);

        g2d.setTransform(trans);

        if (currentFrame >= sprites.length - 1) {
            currentFrame = 0;
        } else {
            currentFrame += speedOfAnimation;
        }

    }

}
