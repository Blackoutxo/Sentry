package me.blackout.Sentry;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static java.security.CryptoPrimitive.SECURE_RANDOM;

public class FileManager {
    public String DATA_FILE = "Sentry.txt";
    public String SALT_FILE = "SalTY.txt";

    public Key key;

    public SecureRandom secRandom = new SecureRandom();

    /**
     * Create File
     * */
    public void create() throws IOException, GeneralSecurityException {
        File file = new File(DATA_FILE);
        File saltyFile = new File(SALT_FILE);

        // Generate key
        key = Utils.generateKey(Main.input);

        // Check for existing file
        if (file.exists() && saltyFile.exists()) return;

        // Create file
        file.createNewFile();
        saltyFile.createNewFile();
    }

    /**
     * Read file
     */
    public String read(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            reader.close();
            return line;
        }
    }

    /**
     * Load the file
     */
    public List<Utils.Entry> load(String file) throws IOException, GeneralSecurityException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",", 2);
                if (parts.length != 2) continue; // Skip malformed parts

                // Decrypt title & passwords
                String title = decryptField(parts[0]);
                String password = decryptField(parts[1]);

                // Add to entry
                Utils.allEntries.add(new Utils.Entry(title, password));
            }
        }

        return Utils.allEntries;
    }

    /**
     *  Writing  & Saving
     */
    public void save(String input) throws GeneralSecurityException, IOException {
        // String into bytes
        String IPT = encryptField(input);

        // Write the input into the save file
        try (FileWriter writer = new FileWriter(DATA_FILE, true)) { // Make ts to append (I kept overwriting the files as it wasn't append)......Bravo!
            writer.write(IPT);
            writer.write(System.lineSeparator());
        }
    }

    public void write(byte[] input, String file) throws IOException {
        try (FileOutputStream IStream = new FileOutputStream(file, true)) {
            IStream.write(input);
        }
    }

    /**
     * Encryption & Decryption
     */
    private String encryptField(String token) throws GeneralSecurityException {
        byte[] iv = new byte[12];
        secRandom.nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] cipherBytes = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + cipherBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(cipherBytes, 0, combined, iv.length, cipherBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    private String decryptField(String token) throws GeneralSecurityException {
        byte[] combined = Base64.getDecoder().decode(token);

        byte[] iv = new byte[12];
        byte[] cipherBytes = new byte[combined.length - 12];
        System.arraycopy(combined, 0, iv, 0, 12);
        System.arraycopy(combined, 12, cipherBytes, 0, cipherBytes.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        byte[] plainBytes = cipher.doFinal(cipherBytes);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }
}
