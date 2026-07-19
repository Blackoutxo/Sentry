package me.blackout.Sentry;


import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException, FontFormatException {
        Sentry sentry = new Sentry();
        sentry.run();
    }
}
