package me.blackout.Sentry;

import javax.swing.*;
import java.awt.*;

public class Panels {
    public static void passkeysPanel(JFrame frame, String fileData) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel keys = new JLabel("Pass keys");
        JButton keyButton = new JButton("Add new keys");
        JButton edit = new JButton("Edit");

        keys.add(fileData, edit);

        panel.add(keys);
        bottomPanel.add(keyButton);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
