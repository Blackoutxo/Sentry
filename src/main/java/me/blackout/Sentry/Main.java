package me.blackout.Sentry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
    public static String input, salt, masterKey;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        JFrame frame = new JFrame("Sentry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileManager file = new FileManager();

        // Create file
        file.create();

        // Input Box
        input = file.read().isEmpty() ? JOptionPane.showInputDialog("Set master key") : JOptionPane.showInputDialog("Enter master key");
        salt = file.read().isEmpty() ? JOptionPane.showInputDialog("Set special word") : JOptionPane.showInputDialog("Enter the special word");

        // Check file
        if (file.read().isEmpty()) {
            masterKey = input;

            file.write(input);
            file.saveFile("Sentry.txt", masterKey, salt);

            System.exit(0);
            return;
        }

        // Pass key
        if (!file.passKey(input, salt)) {
            System.exit(0);
            return;
        }

        // Set Icon for application
        Utils.setIcon(frame);

        // Set up the display panel and fields
        JPanel panel = new JPanel();

       // panel.add(b);

        // Assemble components and display window
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // Save file
        file.saveFile("Sentry.txt", input, salt);
    }
}
