package me.blackout.Sentry;

import java.io.*;

public class FileManager {
    public String path;

    public void createFile() throws IOException {
        File file = new File("Sentry.txt");

        if (file.exists()) return;

        file.createNewFile();
    }

    public void readFile() {

    }

    public String readKey() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader("Sentry.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            return line;
        }
        reader.close();

        return "";
    }
}
