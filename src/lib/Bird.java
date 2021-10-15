package lib;
/*
 * Bird.java
 * Управляет состоянием(жива/мертва) и действиями птицы(взлет)
 */

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bird {
    public String color;
    private int x, y;
    private boolean isAlive = true;

    private int AMPLITUDE = -1;
    public final int BIRD_WIDTH = 44;
    public final int BIRD_HEIGHT = 31;
    private final int SHIFT = 10;
    private final int STARTING_BIRD_X = 90;
    private final int STARTING_BIRD_Y = 300;
    private final int BASE_COLLISION  = 521 - BIRD_HEIGHT - 5;

    private double velocity = 0;//скорость падения
    private double gravity = 0.40;//гравитация
    private double delay = 0;//задержка падения птицы
    private double rotation = 0;//вращение


    private final BufferedImage[] sprites;//спрайт


    public Bird(String color, int x, int y, BufferedImage[] s){
        this.color = color;
        this.x = x;
        this.y = y;
        this.sprites = s;
    }


    public void menuFloat(){
        y += AMPLITUDE;
       if (y < 220) {
            AMPLITUDE *= -1;
        } else if (y > 280) {
          AMPLITUDE *= -1;
       }
    }

    public void up(){
       if (delay < 1) {
            velocity = -SHIFT;
            delay = SHIFT;
        }
    }


    public void inGame(){
        if (y < BASE_COLLISION) {
            velocity += gravity;

            if (delay > 0) {
                delay--;
            }
            y += (int) velocity;

        } else {
            GamePanel.audio.hit();
            isAlive = false;
        }

    }

    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }


    public boolean isAlive(){
        return isAlive;
    }


    public void kill(){
        isAlive = false;
    }


    public void setGameStartPos(){//нач коорд
        x = STARTING_BIRD_X;
        y = STARTING_BIRD_Y;
    }

    public void renderBird(Graphics g){
      rotation = ((90 * (velocity + 25) / 25) - 90) * Math.PI / 180;
        if (!isAlive()) {
            if (y < BASE_COLLISION - 10) {
                velocity += gravity;
                y += (int) velocity;
            }
        }
      if(isAlive){
          Animation.animate(g, sprites, x, y, 0.09, rotation);
      }
    }
}
