package lib;
/*
 * GamePanel.java
 * Главная игровая панель игры
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener, KeyListener {
    //птица
    private Bird bird;

    //колонны
    private int columnDistTracker;//расстояние между колоннами
    private ArrayList<Column> columns;//колонны

    //перемещение экрана
    private static int baseCoords1 = 0;
    private static int baseCoords2 = 435;
    private static int baseSpeed = 2;

    //счет
    private final HighScore highscore = new HighScore();
    private String medal;
    private boolean isScoreGreater;
    private int score;

    //текстуры
    public static HashMap<String, Texture> textures;

    //состояния во время игры
    final static int MENU = 0;
    final static int GAME = 1;
    private int gameState = MENU;
    public boolean ready = false; // загружена ли игра
    private boolean inStartGameState = false; // экран инструкций
    private Point clickedPoint = new Point(-1, -1);
    private boolean isDark = false;

    //звук
    public static Audio audio = new Audio();

    static {
        try {
            textures = new Sprites().getGameTextures();//загружаем все текстуры для игры
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public GamePanel() {
        restart(); // сбрасываем игру

        // добавлем слушатлей
        addKeyListener(this);
        addMouseListener(this);
        ready = true;
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    //Перезапускает игру, сбросив игровые переменные
    private void restart() {
        score = 0;
        columnDistTracker = 0;
        isScoreGreater = false;
        int currentHour;

        Random rand = new Random();
        String randomColor;

        String[] birds = new String[]{//возмжные цвета
                "yellow",
                "blue",
                "red"
        };

        // цвет объекта
        randomColor = birds[rand.nextInt(3)];

        bird = new Bird(randomColor, 170, 250, new BufferedImage[]{
                textures.get(randomColor + "Bird1").getImage(),
                textures.get(randomColor + "Bird2").getImage(),
                textures.get(randomColor + "Bird3").getImage()
        });

        // создаем новый массив колонн
        columns = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        isDark = currentHour >= 18 || currentHour <= 6;


    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(textures.get(isDark ? "backgroundNight" : "backgroundDay").getImage(), 0, 0, null);

        if (bird.isAlive()) {
          baseCoords1 = baseCoords1 - baseSpeed < -435 ? 435 : baseCoords1 - baseSpeed;
          baseCoords2 = baseCoords2 - baseSpeed < -435 ? 435 : baseCoords2 - baseSpeed;
        }

        bird.renderBird(g);//рисуем объект

        switch (gameState) {

            case MENU:
                drawBase(g);
                drawMenu(g);
                bird.menuFloat();
                break;
            case GAME:

                if (bird.isAlive()) {
                    if (inStartGameState) {
                        startGameScreen(g);
                    } else {
                        pipeHandler(g);
                        bird.inGame();
                    }

                    drawBase(g);
                    drawScore(g, score, false, 0, 0);
                } else {
                    pipeHandler(g);
                    gameOver(g);
                    drawBase(g);

                }
                break;
        }

    }


    //меню
    private void drawMenu(Graphics g) {
        g.drawImage(textures.get("gameTitle").getImage(),
        textures.get("gameTitle").getX(),
        textures.get("gameTitle").getY(), null);

        g.drawImage(textures.get("playButton").getImage(),
        textures.get("playButton").getX(),
        textures.get("playButton").getY(), null);
    }

    //нижняя панель заднего фона
    private void drawBase(Graphics g) {
        g.drawImage(textures.get("ground").getImage(), baseCoords1, textures.get("ground").getY(), null);
        g.drawImage(textures.get("ground").getImage(), baseCoords2, textures.get("ground").getY(), null);
    }

    //старт игры
    private void startGameScreen(Graphics g) {

        bird.setGameStartPos();//задаем начальную позицию

        g.drawImage(textures.get("getReadyText").getImage(),
        textures.get("getReadyText").getX(),
        textures.get("getReadyText").getY(), null);


        g.drawImage(textures.get("instruction").getImage(),
        textures.get("instruction").getX(),
        textures.get("instruction").getY(), null);

    }


    //клавиатура
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (gameState == MENU) {

            if (keyCode == KeyEvent.VK_ENTER) {
                gameState = GAME;
                inStartGameState = true;
            }

        } else if (gameState == GAME) {

            if (bird.isAlive()) {
                if (keyCode == KeyEvent.VK_SPACE) {

                    if (inStartGameState) {
                        inStartGameState = false;
                    }
                    bird.up();
                    audio.up();
                }
            } else {
                if (keyCode == KeyEvent.VK_ENTER) {
                    inStartGameState = true;
                    gameState = GAME;
                    restart();
                    bird.setGameStartPos();
                }

            }

        }
    }


    private boolean isTouching(Rectangle r) {//на фрейме ли мы
        return r.contains(clickedPoint);
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickedPoint = e.getPoint();
        if (gameState == MENU) {
            if (isTouching(textures.get("playButton").getRect())) {
                gameState = GAME;
                inStartGameState = true;
            }
        } else if (gameState == GAME) {

            if (bird.isAlive()) {
                if (inStartGameState) {
                    inStartGameState = false;
                }
                bird.up();
                audio.up();
            } else {
                if (isTouching(textures.get("playButton").getRect())) {
                    inStartGameState = true;
                    gameState = GAME;
                    restart();
                    bird.setGameStartPos();
                }

            }

        }
    }


    private void pipeHandler(Graphics g) {
        if (bird.isAlive()) {
            columnDistTracker--;
        }

        Column topColumn = null;
        Column bottomColumn = null;
        Column currentColumn;

        if (columnDistTracker < 0) {
            columnDistTracker = Column.PIPE_DISTANCE;

            for (Column p : columns){
                if (p.getX() < 0) {
                    if (topColumn == null) {
                        topColumn = p;
                        topColumn.canAwardPoint = true;
                    } else if (bottomColumn == null) {
                        bottomColumn = p;
                        topColumn.canAwardPoint = true;
                    }
                }
            }
            if (topColumn == null) {
                currentColumn = new Column("top");
                topColumn = currentColumn;
                columns.add(topColumn);
            } else {
                topColumn.reset();
            }

            if (bottomColumn == null) {
                currentColumn = new Column("bottom");
                bottomColumn = currentColumn;
                columns.add(bottomColumn);
                bottomColumn.canAwardPoint = false;
            } else {
                bottomColumn.reset();
            }

           bottomColumn.setY(topColumn.getY() + Column.PIPE_SPACING);// верхняя труба + расстояние
        }
        // Перемещаеи и рисуем трубы
        for (Column p : columns) {
            if (bird.isAlive()) {
                p.move();
            }

            if (p.getY() <= 0) {
                g.drawImage(textures.get(isDark ? "pipe-topNight" : "pipe-top").getImage(), p.getX(), p.getY(), null);
            } else {
                g.drawImage(textures.get(isDark ? "pipe-bottomNight" : "pipe-bottom").getImage(), p.getX(), p.getY(), null);
            }

            if (bird.isAlive()) {
                if (p.collide(bird.getX(), bird.getY(), bird.BIRD_WIDTH, bird.BIRD_HEIGHT)) {
                    bird.kill();
                    audio.hit();
                } else {
                  if (bird.getX() >= p.getX() + Column.WIDTH / 2) {
                        if (p.canAwardPoint) {
                            score++;
                            p.canAwardPoint = false;
                            audio.point();
                        }
                    }
                }
            }
        }
    }


    private void gameOver(Graphics g) {
        g.drawImage(textures.get("gameOverText").getImage(),
        textures.get("gameOverText").getX(),
        textures.get("gameOverText").getY(), null);

        g.drawImage(textures.get("playButton").getImage(),
        textures.get("playButton").getX(),
        textures.get("playButton").getY(), null);

        g.drawImage(textures.get("scoreTable").getImage(),
        textures.get("scoreTable").getX(),
        textures.get("scoreTable").getY(), null);


        if (isScoreGreater) {
            g.drawImage(textures.get("newMark").getImage(),
            textures.get("newMark").getX(),
            textures.get("newMark").getY(), null);
        }
        drawScore(g, score, true, 303, 276);
        drawScore(g, highscore.bestScore(), true, 303, 330);

        if (score > highscore.bestScore()) {
            isScoreGreater = true;
            highscore.setNewBest(score);
        }

        if (score >= 0) {
            medal = "medal0";
        }
        if (score >= 5) {
            medal = "medal3";
        }
        if (score >= 10) {
            medal = "medal2";
        }

        if (score >= 15) {
            medal = "medal1";
        }


        if (score >= 0) {
            g.drawImage(textures.get(medal).getImage(),
            textures.get(medal).getX(),
            textures.get(medal).getY(), null);
        }


        g.drawImage(textures.get("playButton").getImage(),
        textures.get("playButton").getX(),
        textures.get("playButton").getY(), null);
    }

   private void drawScore(Graphics g, int drawNum, boolean mini, int x, int y) {

        char[] digits = ("" + drawNum).toCharArray();

        int digitCount = digits.length;

        int takeUp = 0;
        for (char digit : digits) {

            if (mini) {
                takeUp += 18;
            } else {
                takeUp += digit == '1' ? 25 : 35;
            }
        }

        int drawScoreX = mini ? (x - takeUp) : (FlappyBird.WIDTH / 2 - takeUp / 2);


        for (int i = 0; i < digitCount; i++) {
            g.drawImage(textures.get("num" + digits[i] + (mini ? "m" : "f")).getImage(), drawScoreX, (mini ? y : 60), null);
            if (mini) {
                drawScoreX += 18;
            } else {
                drawScoreX += digits[i] == '1' ? 25 : 35;
            }
        }

    }

}
