package lib;
/*
 * FlappyBird.java
 * Главный класс игры
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlappyBird  extends JFrame implements ActionListener {
    private final GamePanel game;
    private Timer gameTimer;

    public static final int WIDTH  = 380;
    public static final int HEIGHT = 667;
    private static final int DELAY = 12;

    public FlappyBird () {
        super("Flappy Bird");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src\\lib\\\\resources\\icon\\icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        gameTimer = new Timer(DELAY, this);
        gameTimer.start();
        game = new GamePanel();
        add(game);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlappyBird game = new FlappyBird();
    }

    @Override
    public void actionPerformed (ActionEvent e){
        if (game != null && game.ready) {
            game.repaint();
        }
    }

}
