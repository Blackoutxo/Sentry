package me.blackout.Sentry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static String input, masterKey;
    public static byte[] salt;

    public static void main(String[] args) throws IOException, GeneralSecurityException, FontFormatException {
        Panels panel = new Panels();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Open file manager
        FileManager file = new FileManager();

        // Create file
        file.create();

        // Generate salt once
        if (file.read(file.SALT_FILE).isEmpty()) salt = Utils.generateSalt();

        // Input Box
        input = file.read(file.DATA_FILE).isEmpty() ? JOptionPane.showInputDialog("Set master key") : JOptionPane.showInputDialog("Enter master key");

        // Check file & set masterkey for once
        if (file.read(file.DATA_FILE).isEmpty()) {
            masterKey = input;

            // Save the seasoning
            file.write(salt, file.SALT_FILE);

            // Write password in file
            file.save("masterkey" + "|" + input);

            // System exit on success
            System.exit(0);

            return;
        }

        // Pass key
        if (!Utils.checkMasterkey(input)) {
            System.exit(0); // System exit on fail
            return;
        }

        // Set the master key
        masterKey = input;

        // Set Icon for application
        Utils.setIcon(panel);

        // Assemble components and display window
        panel.pack();
        panel.setLocationRelativeTo(null);
        panel.setResizable(true);
        panel.setVisible(true);
    }
}
