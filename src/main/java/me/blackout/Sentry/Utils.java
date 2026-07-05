package me.blackout.Sentry;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Utils {
    public static String cFile;

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

    // Key generation
    public static Key generateKey(String masterKey) throws NoSuchAlgorithmException, InvalidKeySpecException, UnknownHostException {
        byte[] salt =  (Main.salt + System.getProperty("os.name") + System.getProperty("user.name") + InetAddress.getLocalHost().getHostName()).getBytes();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(masterKey.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);

        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
}
