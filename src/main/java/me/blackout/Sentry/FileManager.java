package me.blackout.Sentry;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

public class FileManager {
    public String path;
    private Key key;

    // Create file
    public void createFile() throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        File file = new File("Sentry.txt");

        if (file.exists()) return;

        file.createNewFile();
    }

    // Read existing file
    public String readFile() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader("Sentry.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            return line;
        }
        reader.close();

        return "";
    }

    // Check if user has password or not
    public void checkPassword() {
        String pass;
    }

    // Encrypt created file
    public void saveFile(String password, String Salt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Read in bytes
        byte[] pTxt = Files.readAllBytes(Paths.get(readFile()));

        // Generate a key
        byte[] salt =  (Salt + System.getProperty("os.name") + System.getProperty("user.name") + InetAddress.getLocalHost().getHostName()).getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        key = secret;

        // Encrypt the file
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedText = cipher.doFinal(pTxt);

        // Write the encrypted file
        Files.write(Paths.get("Sentry.txt"), encryptedText);
    }

    public List<byte[]> decipher() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] fileBytes = Files.readAllBytes(new File(readFile()).toPath());
        byte[] decryptedBytes = cipher.doFinal(fileBytes);

        return List.of(decryptedBytes);
    }
}
