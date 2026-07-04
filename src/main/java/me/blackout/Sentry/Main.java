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
    public static String input;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        JFrame frame = new JFrame("Sentry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileManager file = new FileManager();

        // Create file
        file.create();

        // Input Box
        input = file.read().isEmpty() ? JOptionPane.showInputDialog("Set master key") : JOptionPane.showInputDialog("Enter master key");
        Utils.Salt = file.read().isEmpty() ? JOptionPane.showInputDialog("Set special word") : JOptionPane.showInputDialog("Enter the special word");

        // Check file
        if (file.read().isEmpty()) {
            Utils.masterKey = input;
            System.out.println("Js created another input lmfao");

            file.write("masterkey:" + input, Utils.masterKey, Utils.Salt);
            //file.saveFile("Sentry.txt", masterKey, salt);

            System.exit(0);
            return;
        }

        // Pass key
        if (!file.passKey(input, Utils.Salt)) {
            System.exit(0);
            return;
        }

        // Data panel
        Panels.passkeysPanel(frame, file.read());

        // Set Icon for application
        Utils.setIcon(frame);

        // Set up the display panel and fields

        // Assemble components and display window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
