package me.blackout.Sentry;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Utils {
    public static String masterKey, Salt;

    // Setup salts and keys
    public static void set(String MK, String s) {
        masterKey = MK;
        Salt = s;
    }

    // Icon
    public static void setIcon(JFrame frame) {
        URL iconURL = Main.class.getResource("/Sentry.png");

        if (iconURL == null)  return;

        Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
        frame.setIconImage(icon);
    }

    // Key generation
    public static Key generateKey(String password, String Salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnknownHostException {
        byte[] salt =  (Salt + System.getProperty("os.name") + System.getProperty("user.name") + InetAddress.getLocalHost().getHostName()).getBytes();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);

        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
}
