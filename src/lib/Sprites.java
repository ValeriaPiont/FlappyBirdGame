package lib;
/*
 * Sprites.java
 * Создает HashMap с спрайтми и изменят их размер
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Sprites {

    private static final double RESIZE_FACTOR = 2.605;


    private static final HashMap<String, Texture> textures = new HashMap<>();

    public Sprites () throws IOException {
        //птица  - разные цвета
        textures.put("yellowBird1", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\yellowBird\\birdY-1.png"))), 172, 250));
        textures.put("yellowBird2", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\yellowBird\\birdY-2.png"))), 172, 250));
        textures.put("yellowBird3", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\yellowBird\\birdY-3.png"))),  172, 250));

        textures.put("blueBird1", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\blueBird\\birdB-1.png"))), 172, 250));
        textures.put("blueBird2", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\blueBird\\birdB-2.png"))), 172, 250));
        textures.put("blueBird3", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\blueBird\\birdB-3.png"))),  172, 250));

        textures.put("redBird1", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\redBird\\birdR-1.png"))), 172, 250));
        textures.put("redBird2", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\redBird\\birdR-2.png"))), 172, 250));
        textures.put("redBird3", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\birds\\redBird\\birdR-3.png"))),  172, 250));
        //колонны
        textures.put("pipe-top",    new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\pipes\\greenPipeTop.png"))), 0, 0));
        textures.put("pipe-topNight",    new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\pipes\\redPipeTop.png"))), 0, 0));
        textures.put("pipe-bottom", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\pipes\\greenPipeBottom.png"))), 0, 0));
        textures.put("pipe-bottomNight", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\pipes\\redPipeBottom.png"))), 0, 0));

        //экран
        textures.put("ground",    new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\backgrounds\\ground.png"))), 0, 521));
        textures.put("backgroundDay",    new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\backgrounds\\backgroundDay.png"))), 0, 0));
        textures.put("backgroundNight",    new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\backgrounds\\backgroundNight.png"))), 0, 0));
        // кнопки
        textures.put("playButton",   new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\buttons\\startButton.png"))), 120, 400));

        //сообщения
        textures.put("gameTitle", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\messages\\gameTitle.png"))),  72, 100));
        textures.put("instruction", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\messages\\instruction.png"))),  113, 300));
        textures.put("getReadyText", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\messages\\getReadyText.png"))),  68, 180));
        textures.put("gameOverText", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\messages\\gameOverText.png"))),  62, 100));

        //рекорды
        textures.put("newMark", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\newMark.png"))),  210, 305));
        textures.put("scoreTable", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\scoreTable.png"))),  40, 230));
        textures.put("medal1", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\medal1.png"))),  73, 285));
        textures.put("medal2", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\medal2.png"))),  73, 285));
        textures.put("medal3", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\medal3.png"))),  73, 285));
        textures.put("medal0", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\medal0.png"))),  73, 285));
        for (int i = 0; i < 10; i++) {
            textures.put("num" + i + "f", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\nums1\\"+ "num" + i + "f" + ".png"))),  0, 0));
        }
        for (int i = 0; i < 10; i++) {
            textures.put("num" + i + "m", new Texture(resize(ImageIO.read(new File("src\\lib\\resources\\images\\score\\nums2\\"+ "num" + i + "m" + ".png"))),  0, 0));
        }

    }


    private static BufferedImage resize (BufferedImage image) {

        int newWidth = (int) (image.getWidth() * RESIZE_FACTOR);
        int newHeight = (int) (image.getHeight() * RESIZE_FACTOR);


        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }


    public HashMap<String, Texture> getGameTextures () {
        return textures;
    }
}
