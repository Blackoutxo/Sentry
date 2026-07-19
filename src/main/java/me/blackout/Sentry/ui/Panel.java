package me.blackout.Sentry.ui;

import me.blackout.Sentry.Main;
import me.blackout.Sentry.ui.elements.CardRenderer;
import me.blackout.Sentry.utils.file.FileManager;
import me.blackout.Sentry.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Optional;

import static me.blackout.Sentry.utils.Utils.allEntries;
import static me.blackout.Sentry.utils.Utils.listModel;

public class Panel extends JFrame {
    // ---------------------------------------------------------------
    //                          Color palette
    // ---------------------------------------------------------------
    private static final Color BUTTON = new Color(93, 109, 201);
    private static final Color BUTTON_PRESS = new Color(79, 92, 171);
    private static final Color BUTTON_HOVER = new Color(83, 97, 182);

    private static final Color TEXT = new Color(253, 253, 253);
    //private static final Color TEXT_MUTED = new Color(138, 136, 158);

    private static final Color PANEL_BG = new Color(0x1C, 0x1B, 0x1F);
    private static final Color SIDEBAR_BG = new Color(93, 109, 201);

    private static final Color CARD_BG = new Color(43, 103, 178);
    private static final Color CARD_HOVER = new Color(38, 90, 154);
    private static final Color CARD_BORDER = new Color(255, 255, 255);

    // Field vars
    private final JList<Utils.Entry> entryList = new JList<>(listModel);

    // Panel
    public Panel() throws IOException, FontFormatException {
        super("Sentry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 600);
        setMinimumSize(new Dimension(760, 440));

        Utils.registerFont();

        getContentPane().setBackground(PANEL_BG);

        add(sidePanel(), BorderLayout.WEST);
        add(mainPanel(), BorderLayout.CENTER);
    }

    // ---------------------------------------------------------------
    // panels
    // ---------------------------------------------------------------
    private JPanel mainPanel() {
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(PANEL_BG);
        center.setBorder(new EmptyBorder(24, 24, 24, 16));

        // Search bar
        TextField search = new TextField("Search for items.....");
        search.setPreferredSize(new Dimension(0, 38));
        search.setEditable(true);

        // Top panel
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 16, 0));
        top.add(search, BorderLayout.CENTER);
        center.add(top, BorderLayout.NORTH);

        // Entry card
        entryList.setCellRenderer(new CardRenderer(TEXT, CARD_BG, CARD_HOVER, CARD_BORDER));
        entryList.setBackground(PANEL_BG);
        entryList.setFixedCellHeight(64);
        entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && entryList.getSelectedValue() != null) {
                openDetailDialog();
            }
        });

        // Scroll panel
        JScrollPane scroll = new JScrollPane(entryList);
        scroll.setBorder(null);
        center.add(scroll);

        // Button
        Button addButton = new Button("+  ADD KEYS", TEXT);

        // Button action
        addButton.addActionListener(e ->
            openAddDialog()
        );

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(16, 0, 0, 0)); // space above the button
        bottomBar.add(addButton);

        center.add(bottomBar, BorderLayout.SOUTH);
        return center;
    }

    public JPanel sidePanel() throws IOException {
        JPanel sideBar = new JPanel();

        // Set layout
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(SIDEBAR_BG);
        sideBar.setPreferredSize(new Dimension(220, 0));
        sideBar.setBorder(new EmptyBorder(24, 0, 0, 0));

        // Logo
        JLabel logoLabel = new JLabel("SENTRY");
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(new EmptyBorder(0, 0, 20, 30));

        logoLabel.setForeground(Color.BLACK);
        logoLabel.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 30f));

        URL iconURL = Main.class.getResource("/Sentry.png");
        if (iconURL != null) {
            BufferedImage original = ImageIO.read(iconURL);
            Image scaled = original.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        }

        // Home
        JButton home = new Button("Home", Color.BLACK);
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.setBorder(new EmptyBorder(10, 16, 10, 16));

        home.setFont(Utils.spaceGrotesk.deriveFont(20f));

        URL homeIcon = Main.class.getResource("/icons/home_dark.png");
        if (homeIcon != null) {
            BufferedImage original = ImageIO.read(homeIcon);
            Image scaled = original.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // pick your display size
            home.setIcon(new ImageIcon(scaled));
        }

        // Favourite
        JButton favourite = new Button("Favourite", Color.BLACK);
        favourite.setAlignmentX(Component.CENTER_ALIGNMENT);
        favourite.setBorder(new EmptyBorder(10, 16, 10, 16));

        favourite.setFont(Utils.spaceGrotesk.deriveFont(20f));

        URL favouriteIcon = Main.class.getResource("/icons/star_dark.png");
        if (favouriteIcon != null) {
            BufferedImage original = ImageIO.read(favouriteIcon);
            Image scaled = original.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // pick your display size
            favourite.setIcon(new ImageIcon(scaled));
        }

        // Settings
        JButton settings = new Button("Settings", Color.BLACK);
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        favourite.setBorder(new EmptyBorder(10, 16, 10, 16));

        settings.setFont(Utils.spaceGrotesk.deriveFont(20f));

        URL settingIcon = Main.class.getResource("/icons/settings_dark.png");
        if (settingIcon != null) {
            BufferedImage original = ImageIO.read(settingIcon);
            Image scaled = original.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // pick your display size

            settings.setIcon(new ImageIcon(scaled));
        }

        sideBar.add(logoLabel);
        sideBar.add(home, BorderLayout.CENTER);
        sideBar.add(favourite, BorderLayout.CENTER);
        sideBar.add(settings, BorderLayout.CENTER);
        sideBar.add(Box.createVerticalStrut(24));

        return sideBar;
    }

    // Dialog panel
    public void openAddDialog() {
        JDialog dialog = new JDialog(this, "Add new key", true);
        JPanel form = new JPanel(new GridBagLayout());

        dialog.setBackground(PANEL_BG);
        form.setBackground(PANEL_BG);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleL = new JLabel("TITLE");
        JTextField title = new TextField("");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; // For title label
        titleL.setFont(Utils.spaceGrotesk.deriveFont(14f));
        titleL.setForeground(TEXT);
        form.add(titleL, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1; // For title text field
        form.add(title, gbc);

        // Passkey
        JLabel passL = new JLabel("PASSWORD");
        JPasswordField password = new PasswordField();

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        passL.setFont(Utils.spaceGrotesk.deriveFont(14f));
        passL.setForeground(TEXT);
        form.add(passL, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        form.add(password, gbc);

        dialog.add(form, BorderLayout.CENTER);

        // Button
        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonBar.setBackground(PANEL_BG);
        buttonBar.setBorder(new EmptyBorder(0, 0, 16, 0));

        // Cancel button
        Button cancel = new Button("CANCEL", TEXT);

        cancel.addActionListener(e ->
            dialog.dispose()
        );

        // Save button
        Button save = new Button("SAVE", TEXT);

        save.addActionListener(e -> {
            FileManager file = new FileManager();

            String strTitle = title.getText().trim();
            String passKey = new String(password.getPassword());

            if (strTitle.isEmpty() || passKey.isEmpty()) return;

            Optional<Utils.Entry> option = Utils.findByTitle(strTitle);

            // Check if title is re-used
            if (option.isPresent()) {
                int choice = JOptionPane.showConfirmDialog(
                        dialog, "Entry named " + strTitle + " is already in use, do you want to overwrite it?",
                        "Duplicate Entry", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
                );

                // Check choice made
                if (choice != JOptionPane.OK_OPTION) return;

                // Remove former entry
                allEntries.remove(option.get());
                listModel.removeElement(option.get());
            }

            try {
                allEntries.add(new Utils.Entry(strTitle, passKey));
                listModel.addElement(new Utils.Entry(strTitle, passKey));

                file.saveEntries(); // Save file
                dialog.dispose();

            } catch (IOException | GeneralSecurityException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonBar.add(cancel);
        buttonBar.add(save);
        dialog.add(buttonBar, BorderLayout.SOUTH);

        // Pack dialog box
        dialog.pack();
        dialog.setSize(Math.max(dialog.getWidth(), 380), dialog.getHeight());
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private void openDetailDialog() {
        JDialog dialog = new JDialog(this, "Detail", true);
        JPanel form = new JPanel(new GridBagLayout());

        Utils.Entry entry = entryList.getSelectedValue();

        dialog.setBackground(PANEL_BG);
        form.setBackground(PANEL_BG);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleL = new JLabel("TITLE");
        JTextField title = new TextField(entry.title());

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; // For title label
        titleL.setFont(Utils.spaceGrotesk.deriveFont(14f));
        titleL.setForeground(TEXT);
        form.add(titleL, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1; // For title text field
        form.add(title, gbc);

        // Passkey
        JLabel passL = new JLabel("PASSWORD");
        JPasswordField password = new PasswordField();

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; // For passkey label
        passL.setFont(Utils.spaceGrotesk.deriveFont(14f));
        passL.setForeground(TEXT);
        form.add(passL, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; // For passkey text field
        password.setText(entry.password());
        form.add(password, gbc);

        dialog.add(form, BorderLayout.CENTER);

        // Button
        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonBar.setBackground(PANEL_BG);
        buttonBar.setBorder(new EmptyBorder(0, 0, 16, 0));

        // Cancel button
        Button delete = new Button("DELETE", TEXT);

        delete.addActionListener(e -> {
            try {
                deleteEntry(entry);
                dialog.dispose();
            } catch (GeneralSecurityException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Save button
        Button show = new Button("SHOW", TEXT);

        final int[] ps = {0};
        show.addActionListener(e -> {
            if (ps[0] == 0) {
                password.setEchoChar((char) 0);
                ps[0] += 1;
            } else {
                password.setEchoChar('\u2022');
                ps[0] = 0;
            }
        });

        // Add to button bar
        buttonBar.add(delete);
        buttonBar.add(show);
        dialog.add(buttonBar, BorderLayout.SOUTH);

        // Pack dialog box
        dialog.pack();
        dialog.setSize(Math.max(dialog.getWidth(), 380), dialog.getHeight());
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    // Delete selected
    private void deleteEntry(Utils.Entry entry) throws GeneralSecurityException, IOException {
        // Confirm dialog panel
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete \"" + entry.title() + "\"?", "Confirm delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            allEntries.remove(entry);
            listModel.removeElement(entry);
            new FileManager().saveEntries();
        }
    }

    // ---------------------------------------------------------------
    //  J-Elements modification
    // ---------------------------------------------------------------

    static class Button extends JButton {
        public static int ARC = 12;
        public final Color textColor;

        Button(String text, Color textColor) {
            super(text);
            this.textColor = textColor;

            setForeground(textColor);
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

            Color fill = getModel().isPressed() ? BUTTON_PRESS
                    : getModel().isRollover() ? BUTTON_HOVER
                    : BUTTON;

            g2.setColor(fill);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC));

            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {/*  No borders */ }
    }

    class TextField extends JTextField {
        private final String placeholder;

        public TextField(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setBorder(new EmptyBorder(8, 14, 8, 14));
            setFont(Utils.spaceGrotesk.deriveFont(13f));
            setForeground(TEXT);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Box
            g2.setColor(PANEL_BG);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));

            // Box border
            g2.setColor(CARD_BORDER);
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 2, getHeight() - 2, 12, 12));

            g2.dispose();
            super.paintComponent(g);

            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D pg = (Graphics2D) g.create();
                pg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //pg.setColor(TEXT);
                pg.setFont(Utils.spaceGrotesk.deriveFont(13f));

                FontMetrics fm = pg.getFontMetrics();
                pg.drawString(placeholder, getInsets().left + 10, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                pg.dispose();
            }
        }
    }

    // Password field
    static class PasswordField extends JPasswordField {

        PasswordField() {
            setOpaque(false);
            setBorder(new EmptyBorder(8, 14, 8, 14));
            setFont(Utils.spaceGrotesk.deriveFont(13f));
            setForeground(Color.WHITE);
            setCaretColor(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Box
            g2.setColor(PANEL_BG);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));

            // Box border
            g2.setColor(CARD_BORDER);
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 2, getHeight() - 2, 12, 12));

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
