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
    public void write(String input) throws IOException {
        File file = new File("Sentry.txt");

        FileOutputStream IStream = new FileOutputStream(file);

        IStream.write(input.getBytes());
    }

    // Read existing file
    public String read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Sentry.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            return line;
        }

        reader.close();

        return "";
    }

    // Check if user has password or not
    public boolean passKey(String masterKey, String salt) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        key = Utils.generateKey(masterKey, Main.salt);
        for (String line : decipher()) {
             if (line.contains("masterkey:")) {
                 int passLength = line.length() - 10;

                 return Objects.equals(masterKey, line.substring(10));
             }
         }

        return false;
    }

    // Encrypt created file
    public void saveFile(String file,String password, String Salt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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
    }

    public List<String> decipher() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] fileBytes = Files.readAllBytes(new File(read()).toPath());
        String[] decryptedBytes = new String[]{Arrays.toString(cipher.doFinal(fileBytes))};

        System.out.println(Arrays.toString(decryptedBytes));

        return List.of(decryptedBytes);
    }
}
