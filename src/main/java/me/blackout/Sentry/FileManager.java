package me.blackout.Sentry;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;

public class FileManager {
    public String DATA_FILE = "Sentry.dat";
    public String SALT_FILE = "SalTY.dat";
    private Key key;

    /**
     * Create File
     * */
    public void create() throws IOException {
        File file = new File(DATA_FILE);
        File saltyFile = new File(SALT_FILE);

        // Check for existing file
        if (file.exists() && saltyFile.exists()) return;

        file.createNewFile();
        saltyFile.createNewFile();
    }

    /**
     * Saving file
     */
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

    /**
     * Byte Write
     */
    public void write(byte[] input) throws IOException {
        FileOutputStream IStream = new FileOutputStream(SALT_FILE);
        IStream.write(input);
    }

    /**
     * Read selected file
     */
    public String read(String password, String file, boolean decipher) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        if (decipher) {
            key = Utils.generateKey(password);
            for (String str : decipher(file)) return str;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(file));
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
        for (String line : decipher(DATA_FILE)) {
             if (line.contains("masterkey|")) {
                 return Objects.equals(input, line.substring(10));
             }
         }

        return false;
    }

    public List<String> decipher(String file) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] fileBytes = Files.readAllBytes(Path.of(file));
        byte[] decryptedBytes = cipher.doFinal(fileBytes);

        String str = new String(decryptedBytes);

        return List.of(str);
    }
}
