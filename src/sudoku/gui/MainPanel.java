package sudoku.gui;

import java.awt.*;
import javax.swing.*;

public class MainPanel extends JFrame {
    JPanel panel;
    JLabel label;

    public MainPanel(String title) {
        super(title);
        setSize(150, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());
        label = new JLabel("Hello Swing!");
        add(label);
    }
}
