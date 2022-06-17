import gui.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("交通咨询系统设计");
        jFrame.setBounds(500, 250, 900,600);
        jFrame.add(new MainWindow());
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
