package me.blackout.Sentry;

import javax.swing.*;
import java.awt.*;

public class Panels {
    public static void passkeysPanel(JFrame frame, String fileData) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("PASS KEYS", SwingConstants.CENTER);
        title.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 25f));

        JTextArea dataArea = new JTextArea(fileData);
        dataArea.setEditable(false);
        dataArea.setLineWrap(true);
        dataArea.setWrapStyleWord(true);
        dataArea.setSize(200, 100);

        JScrollPane scrollPane = new JScrollPane(dataArea);

        JButton keyButton = new JButton("Add new keys");
        JButton edit = new JButton("Edit");

        bottomPanel.add(keyButton);
        bottomPanel.add(edit);

        edit.addActionListener(e -> {
            dataArea.setEditable(true);
            edit.setLabel("Done");
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.WEST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(panel);
    }
}
