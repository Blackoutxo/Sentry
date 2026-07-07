package me.blackout.Sentry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Panels extends JFrame {
    // ---------------------------------------------------------------
    //                          Color palette
    // ---------------------------------------------------------------
    private static final Color BUTTON = new Color(32, 157, 234);
    private static final Color TEXT = new Color(255, 255, 255);

    public Panels() {
        super("Sentry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 600);
        setMinimumSize(new Dimension(760, 440));
    }

    // ---------------------------------------------------------------
    //                           panels
    // ---------------------------------------------------------------

    public static void mainPanel(JFrame frame, String fileData) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel bottomPanel = new JPanel();

        JLabel title = new JLabel("PASS KEYS", SwingConstants.CENTER);
        title.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 25f));

        CSTButton addKey = new CSTButton("ADD NEW KEY");
        addKey.setForeground(BUTTON);

        // Button listner
        addKey.addActionListener(e -> {

        });

        panel.add(title);
        panel.add(bottomPanel);

        bottomPanel.add(addKey);

        frame.getContentPane().removeAll();
        frame.add(panel);
    }

    public static void addKeyPanel() {
        JPanel panel = new JPanel();

    }

    // ---------------------------------------------------------------
    //  J-Elements modification
    // ---------------------------------------------------------------

    static class CSTButton extends JButton {
        public static int ARC = 12;

        CSTButton(String text) {
            super(text);
            setForeground(Color.WHITE);
            setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 13f));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setBorder(new EmptyBorder(9, 18, 9, 18));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fill = TEXT;
            g2.setColor(fill);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
