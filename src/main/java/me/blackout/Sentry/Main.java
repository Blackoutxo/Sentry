package me.blackout.Sentry;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {
    private static String hwid, input;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sentry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL iconURL = Main.class.getResource("/logo.png");
        if (iconURL != null) {
            Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
            frame.setIconImage(icon);
        } else {
            System.err.println("Icon file not found in resources!");
        }

        // Set up the display panel and fields
        JPanel jp = new JPanel(new GridLayout(1, 2, 20, 5)); // 1 row, 2 columns, 10px spacing 5px vertical gap
        jp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around elements
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        input = JOptionPane.showInputDialog("Enter master key");

        // Assemble components and display window
        frame.add(jp);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack(); // Automatically sizes window to fit your text field and button
        frame.setLocationRelativeTo(null); // Centers window on screen
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
