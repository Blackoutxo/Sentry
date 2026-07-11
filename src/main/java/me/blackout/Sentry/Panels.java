package me.blackout.Sentry;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

public class Panels extends JFrame {
    // ---------------------------------------------------------------
    //                          Color palette
    // ---------------------------------------------------------------
    private static final Color BUTTON = new Color(32, 157, 234);
    private static final Color BUTTON_PRESS = new Color(39, 134, 192);
    private static final Color BUTTON_HOVER = new Color(39, 134, 192);

    private static final Color TEXT = new Color(253, 253, 253);
    private static final Color TEXT_MUTED = new Color(138, 136, 158);

    private static final Color PANEL_BG = new Color(52, 50, 50);
    private static final Color SIDEBAR_BG = new Color(43, 103, 178);
    private static final Color CARD_BORDER = new Color(255, 255, 255);

    // Field vars
    private final DefaultListModel<Entry> listModel = new DefaultListModel<>();
    private final JList<Entry> entryList = new JList<>(listModel);

    private final CardLayout cardDetail = new CardLayout();

    // Panel
    public Panels() throws IOException, FontFormatException {
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
        entryList.setCellRenderer(new EntryCardRenderer());

        // Scroll panel
        JScrollPane scroll = new JScrollPane(entryList);
        center.add(scroll);

        // Button
        Button addButton = new Button("+  ADD KEYS");

        // Button action
        addButton.addActionListener(e -> {
            openAddDialog();
        });

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomBar.setOpaque(false);
        bottomBar.setBorder(new EmptyBorder(16, 0, 0, 0)); // space above the button
        bottomBar.add(addButton);

        center.add(bottomBar, BorderLayout.SOUTH);
        return center;
    }

    public JPanel sidePanel() {
        JPanel sideBar = new JPanel();

        // Set layout
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(SIDEBAR_BG);
        sideBar.setPreferredSize(new Dimension(220, 0));
        sideBar.setBorder(new EmptyBorder(24, 20, 24, 20));

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
        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        buttonBar.setBackground(PANEL_BG);
        buttonBar.setBorder(new EmptyBorder(0, 0, 16, 20));

        // Cancel button
        Button cancel = new Button("CANCEL");

        cancel.addActionListener(e -> {
            dialog.dispose();
        });

        // Save button
        Button save = new Button("SAVE");

        buttonBar.add(cancel);
        buttonBar.add(save);

        //dialog.add(buttonBar);

        // Pack dialog box
        dialog.pack();
        dialog.setSize(Math.max(dialog.getWidth(), 380), dialog.getHeight());
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    // Record
    record Entry(String title, String password) {
    }

    // ---------------------------------------------------------------
    //  J-Elements modification
    // ---------------------------------------------------------------

    // Cell renderer
    static class EntryCardRenderer extends JPanel implements ListCellRenderer<Entry> {
        private final JLabel title = new JLabel();
        private final JLabel subTitle = new JLabel();
        //private final JLabel avatar = new JLabel();

        EntryCardRenderer() {
            setLayout(new BorderLayout(12, 0));
            setOpaque(false);

            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

            title.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 14f));
            title.setForeground(TEXT);

            subTitle.setFont(Utils.spaceGrotesk.deriveFont(12f));
            subTitle.setForeground(TEXT_MUTED);

            textPanel.add(title);
            textPanel.add(subTitle);

            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Entry> list, Entry entry, int index, boolean isSelected, boolean cellHasFocus) {

            title.setText(entry.title);

            return list;
        }
    }

    // Buttons
    static class Button extends JButton {
        public static int ARC = 12;

        Button(String text) {
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

    // Text field
    static class TextField extends JTextField {
        private final String placeholder;

        TextField(String placeholder) {
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
