package me.blackout.Sentry;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {
    public static List<Entry> allEntries = new ArrayList<>();
    public static final DefaultListModel<Utils.Entry> listModel = new DefaultListModel<>();

    public static Font spaceGrotesk;

    // Register font
    public static void registerFont() throws IOException, FontFormatException {
        InputStream is = Utils.class.getResourceAsStream("/fonts/SpaceGrotesk/static/SpaceGrotesk-Regular.ttf");
        spaceGrotesk = Font.createFont(Font.TRUETYPE_FONT, is);
    }

    // Icon
    public static void setIcon(JFrame frame) {
        URL iconURL = Main.class.getResource("/Sentry.png");

        if (iconURL == null)  return;

        Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
        frame.setIconImage(icon);
    }
    
    // Check masterkey
    public static boolean checkMasterkey(String input) {
        for (Entry entry : allEntries) {
            return entry.password.equals(input) && entry.title.equals("masterkey");
        }
        return false;
    }

    // Set in list model
    public static void setListModel() {
        for (Entry entry : allEntries) {
            listModel.addElement(entry);
        }
    }

    // Find by title
    public static Optional<Entry> findByTitle(String title) {
        return allEntries.stream()
                .filter(entry -> entry.title().equalsIgnoreCase(title))
                .findFirst();
    }

    // Key generation
    public static Key generateKey(String masterKey) throws GeneralSecurityException, IOException {
        FileManager file = new FileManager();

        // Read file
        byte[] salt = Files.readAllBytes(Path.of(file.SALT_FILE));

        // Set-up key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(masterKey.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);

        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    // Salt *Gotta make it salty
    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }

    // Record
    public record Entry(String title, String password) {
    }
}
