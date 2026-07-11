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
import java.util.Arrays;

public class Main {
    public static String input, masterKey;
    public static byte[] salt;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, FontFormatException {
        Panels panel = new Panels();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Open file manager
        FileManager file = new FileManager();

        // Create file
        file.create();

        // Generate salt once
        if (file.read("", file.DATA_FILE, false).isEmpty()) salt = Utils.generateSalt();

        // Input Box
        input = file.read("", file.DATA_FILE, false).isEmpty() ? JOptionPane.showInputDialog("Set master key") : JOptionPane.showInputDialog("Enter master key");

        // Check file
        if (file.read("", file.DATA_FILE, false).isEmpty()) {
            masterKey = input;

            // Save the seasoning
            file.write(salt);

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
        Utils.cFile = file.read(masterKey, file.DATA_FILE, false);

        // Set Icon for application
        Utils.setIcon(panel);

        // Assemble components and display window
        panel.pack();
        panel.setLocationRelativeTo(null);
        panel.setResizable(true);
        panel.setVisible(true);
    }
}
