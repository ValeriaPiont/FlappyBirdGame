package lib;
/*
 * Column.java
 * Управляет колоннами
 */
public class Column {
    private int x = FlappyBird.WIDTH;
    private int y;
    private String location; // Размещение колонны

    // Константы колонны
    public static final int WIDTH  = 67;
    public static final int HEIGHT = 416;
    public static final int PIPE_DISTANCE = 150; //Горизонтальное расстояние между колннами
     public static final int PIPE_SPACING  = HEIGHT + 170; // Вертикальное расстояние между колннами
    public static int SPEED  = -2;
    public boolean canAwardPoint = true;

    public Column(String location) {
        this.location = location;
        reset();
    }

    public void reset () {
        x = FlappyBird.WIDTH;
        if (location.equals("top")) {
           y = - Math.max((int) (Math.random() * 320) + 30, 140);//140 - чуть ниже середины //30-высота птицы
        }
    }


    public void move () {//сохращаем расстояние между колонной и птицей на два
        x += SPEED;
    }


    public boolean collide (int bX, int bY, int bW, int bH) {
        if (bX > x && bY < 0 && canAwardPoint) {
            return true;
        }

        return bX < x + WIDTH &&
                bX + bW > x &&
                bY < y + HEIGHT &&
                bY + bH > y;
    }

    public int getX () {
        return x;
    }


    public int getY () {
        return y;
    }


    public void setY (int newY) {
        y = newY;
    }

}
