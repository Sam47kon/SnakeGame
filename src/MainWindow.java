import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,320);
        setLocation(400,400);
        setVisible(true);
        add(new GameField());
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

    }
}
