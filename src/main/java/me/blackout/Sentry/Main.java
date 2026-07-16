package me.blackout.Sentry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static String input, masterKey;
    public static byte[] salt;

    public static void main(String[] args) throws IOException, GeneralSecurityException, FontFormatException {
        // Open file manager
        FileManager file = new FileManager();

        // Create file
        file.create();

        // Input Box
        input = file.read(file.DATA_FILE).isEmpty() ? JOptionPane.showInputDialog("Set master key") : JOptionPane.showInputDialog("Enter master key");

        // Generate salt once
        if (file.read(file.SALT_FILE).isEmpty()) {
            salt = Utils.generateSalt();

            // Save the seasoning
            file.write(salt, file.SALT_FILE);
        }

        // Check file & set masterkey for once
        if (file.read(file.DATA_FILE).isEmpty()) {
            masterKey = input;

            // Write password in file
            file.save("masterkey", input, true);

            // System exit on success
            System.exit(0);

            return;
        }

        // Set the master key
        masterKey = input;
                                 // Triple fold protection lol
        // Load file
        file.load(file.DATA_FILE);

        // Pass key
        if (!Utils.checkMasterkey(input)) {
            System.exit(0); // System exit on fail
            return;
        }

        // Init panel here
        Panels panel = new Panels();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set Icon for application
        Utils.setIcon(panel);

        // Assemble components and display window
        panel.pack();
        panel.setLocationRelativeTo(null);
        panel.setResizable(true);
        panel.setVisible(true);
    }
}
