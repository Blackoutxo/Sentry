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
    private static String input, salt;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        JFrame frame = new JFrame("Sentry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileManager file = new FileManager();

        input = JOptionPane.showInputDialog("Enter master key");

        if (!checkKey(file.readFile())) System.exit(0);

        // Create file if needed
        file.createFile();

        // read file
        file.readFile();

        URL iconURL = Main.class.getResource("/Sentry.png");
        if (iconURL != null) {
            Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
            frame.setIconImage(icon);
        } else {
            System.err.println("Icon file not found in resources!");
        }

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
        file.saveFile(String.valueOf(checkKey(file.readFile())), salt);
    }

    // Check file
    private static boolean checkKey(String file) {
        String ctStr = file.substring(11);
        return ctStr.equals(input);
    }
}
