import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Sentry"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL iconURL = Main.class.getResource("/logo.png");
        if (iconURL != null) {
            Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
            frame.setIconImage(icon);
        } else {
            System.err.println("Icon file not found in resources!");
        }



    }
}