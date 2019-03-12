import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int size = 320; // размер поля
    private final int dot_Size = 16; // размер ячейки змейки
    private final int All_Dots = 400;
    private Image dot;
    private Image apple;
    private int applePosX; // позиция яблока ширина
    private int applePosY; // позиция яблока длина
    private int[] x = new int[All_Dots]; // змейка может занимать все поле ширина
    private int[] y = new int[All_Dots]; // змейка может занимать все поле длина
    private int dots; // размер змейки текущий
    private Timer timer; // таймер
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener()); //обработчик событий
        setFocusable(true); //подключаю фокус именно над игровым полем

    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 60 - i * dot_Size;
            y[i] = 60;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        applePosX = new Random().nextInt(20 * dot_Size);
        applePosY = new Random().nextInt(20 * dot_Size);
    }

    public void loadImages() {
        ImageIcon imageIconApple = new ImageIcon("apple.png");
        apple = imageIconApple.getImage();
        ImageIcon imageIconDot = new ImageIcon("dot.png");
        dot = imageIconDot.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, applePosX, applePosY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String gameOver = "Игра окончена";
            g.setColor(Color.RED);
            g.drawString(gameOver, 150, 150);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= dot_Size;
        }
        if (right) {
            x[0] += dot_Size;
        }
        if (down) {
            y[0] += dot_Size;
        }
        if (up) {
            y[0] -= dot_Size;
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[i] == x[0] && y[i] == y[0]) { // если змейка ест сама себя
                inGame = false;
            }
        }
        if (x[0] > size) { // если змейка удариться о стенку
            inGame = false;
        }
        if (x[0] < 0) { // если змейка удариться о левую стенку
            inGame = false;
        }
        if (y[0] > size) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    public void checkApple() { // метод если змейка съела яблоко
        if (x[0] == applePosX && y[0] == applePosY) {
            dots++;
            createApple();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) { //если я в игре
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); // метод который вызывает paintComponent отрисовку
    }

    class FieldKeyListener extends KeyAdapter { //движение змейки клавишами
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) { //если нажать влево при этом чтобы не двигалась змея вправо
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }
        }
    }


}
