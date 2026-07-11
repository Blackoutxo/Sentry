package me.blackout.Sentry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
    public static String input, salt, masterKey;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, FontFormatException {
        Panels panel = new Panels();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Open file manager
        FileManager file = new FileManager();

        // Create file
        file.create();

        // Input Box
        input = file.read(false, "").isEmpty() ? JOptionPane.showInputDialog("Set master key") : JOptionPane.showInputDialog("Enter master key");
        salt = file.read(false, "").isEmpty() ? JOptionPane.showInputDialog("Set special word") : JOptionPane.showInputDialog("Enter the special word");

        // Check file
        if (file.read(false, "").isEmpty()) {
            masterKey = input;

            // Write password in file
            file.save("masterkey|" + input, masterKey);

            // System exit on success
            System.exit(0);

            return;
        }

        // Pass key
        if (!file.passKey(input)) {
            // System exit on fail
            System.exit(0);
            return;
        }

        // Set the master key
        masterKey = input;

        // Set current file
        Utils.cFile = file.read(true, masterKey);

        // Set Icon for application
        Utils.setIcon(panel);

        // Assemble components and display window
        panel.pack();
        panel.setLocationRelativeTo(null);
        panel.setResizable(true);
        panel.setVisible(true);
    }
}
