package me.blackout.Sentry;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileManager {
    public String DATA_FILE = "Sentry.dat";
    private Key key;

    // Create file
    public void create() throws IOException {
        File file = new File(DATA_FILE);

        // Check for existing file
        if (file.exists()) return;

        file.createNewFile();
    }

    // Write in the file
    public void save(String input, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        File file = new File(DATA_FILE);

        // String into bytes
        byte[] pTxt = (input).getBytes();
        Key secret = Utils.generateKey(password);

        // Encrypt the file
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedText = cipher.doFinal(pTxt);

        // Write the input into the save file
        FileOutputStream IStream = new FileOutputStream(file);
        IStream.write(encryptedText);
    }

    // Read existing file
    public String read(boolean decipher, String password) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        if (decipher) {
            key = Utils.generateKey(password);
            for (String str : decipher()) return str;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                return line;
            }
            reader.close();
        }

        return "";
    }

    // Check if user has password or not
    public boolean passKey(String input) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        key = Utils.generateKey(input);
        for (String line : decipher()) {
             if (line.contains("masterkey|")) {
                 return Objects.equals(input, line.substring(10));
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

        byte[] fileBytes = Files.readAllBytes(Path.of(DATA_FILE));
        byte[] decryptedBytes = cipher.doFinal(fileBytes);

        String str = new String(decryptedBytes);

        return List.of(str);
    }
}
