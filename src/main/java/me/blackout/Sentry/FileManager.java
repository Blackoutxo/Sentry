package me.blackout.Sentry;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileManager {
    public String path;
    private Key key;

    // Create file
    public void create() throws IOException {
        File file = new File("Sentry.txt");

        if (file.exists()) return;

        file.createNewFile();
    }

    // Write in the file
    public void write(String input, String password, String Salt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        File file = new File("Sentry.txt");

        // String into bytes
        byte[] pTxt = input.getBytes();
        Key secret = Utils.generateKey(password, Salt);

        // Encrypt the file
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedText = cipher.doFinal(pTxt);

        // Write the input into the save file
        FileOutputStream IStream = new FileOutputStream(file);
        IStream.write(encryptedText);
    }

    // Read existing file
    public String read() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        key = Utils.generateKey(Utils.masterKey, Utils.Salt);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] fileBytes = Files.readAllBytes(Path.of("Sentry.txt"));
        byte[] decryptedBytes = cipher.doFinal(fileBytes);

        return new String(decryptedBytes);
    }

    // Check if user has password or not
    public boolean passKey(String masterKey, String salt) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        key = Utils.generateKey(masterKey, salt);
        for (String line : decipher()) {
             if (line.contains("masterkey:")) {
                 System.out.println(line);
                 return Objects.equals(masterKey, line.substring(10));
             }
         }

        return false;
    }

    // Encrypt created file
    /*public void saveFile(String file,String password, String Salt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Read in bytes
        byte[] pTxt = Files.readAllBytes(Path.of(file));

        // Generate a key
        Key secret = Utils.generateKey(password, Salt);

        // Encrypt the file
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedText = cipher.doFinal(pTxt);

        // Write the encrypted file
        Files.write(Paths.get("Sentry.txt"), encryptedText);
    }*/

    public List<String> decipher() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] fileBytes = Files.readAllBytes(Path.of("Sentry.txt"));
        byte[] decryptedBytes = cipher.doFinal(fileBytes);

        String str = new String(decryptedBytes);

        return List.of(str);
    }
}
