package me.blackout.Sentry;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileManager {
    public String DATA_FILE = "Sentry.txt";
    public String SALT_FILE = "SalTY.txt";
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
        // String into bytes
        byte[] pTxt = (input).getBytes();
        Key secret = Utils.generateKey(password);

        // Encrypt the file
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedText = cipher.doFinal(pTxt);

        String str = new String(encryptedText);

        //System.out.println(Arrays.toString(str .toCharArray()));

        // Write the input into the save file

    }

    /**
     * Byte Write
     */
    public void write(byte[] input, String file) throws IOException {
        FileOutputStream IStream = new FileOutputStream(file);
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

        int cn = 0;
        for (String str : decipher(DATA_FILE)) {
            cn++;

            System.out.println(cn + " " + str);

            return Objects.equals(input, str.substring(10));
        }

        return false;
    }

    public List<String> decipher(String file) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while (reader.readLine() != null) {

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] fileBytes = Files.readAllBytes(Path.of(file));
            byte[] decryptedBytes = cipher.doFinal(fileBytes);

            String readableString = new String(decryptedBytes, StandardCharsets.UTF_8);

            return List.of(readableString);
        }

        // Close reader
        reader.close();

        return Collections.singletonList("");
    }
}
