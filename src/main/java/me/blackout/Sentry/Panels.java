package me.blackout.Sentry;

import javax.swing.*;
import java.awt.*;

public class Panels {

    public static void passkeysPanel(JFrame frame, String fileData) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel bottomPanel = new JPanel();

        JLabel title = new JLabel("PASS KEYS", SwingConstants.CENTER);
        title.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 25f));

        JButton addKey = new JButton("Add new key");

        addKey.addActionListener(e -> {
            JFrame addKeyFrame = new JFrame();

            addKeyFrame.setSize(200, 200);
        });

        panel.add(title);
        panel.add(bottomPanel);

        bottomPanel.add(addKey);

        frame.getContentPane().removeAll();
        frame.add(panel);
    }
}
