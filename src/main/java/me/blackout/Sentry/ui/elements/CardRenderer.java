package me.blackout.Sentry.ui.elements;

import me.blackout.Sentry.Main;
import me.blackout.Sentry.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class CardRenderer extends JPanel implements ListCellRenderer<Utils.Entry> {
    private final JLabel title = new JLabel();
    private final JLabel avatar = new JLabel();

    public final Color background, hover, textColor, border;

    public CardRenderer(Color textColor, Color background, Color hover, Color border) {
        this.background = background;
        this.hover = hover;
        this.textColor = textColor;
        this.border = border;

        setLayout(new BorderLayout(15, 0));
        setBorder(new EmptyBorder(10, 4, 10, 4));
        setOpaque(false);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        title.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 14f));
        title.setAlignmentY(SwingConstants.CENTER);
        title.setForeground(textColor);

        avatar.setOpaque(true);
        avatar.setForeground(textColor);
        avatar.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        avatar.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 20f));

        textPanel.add(avatar);
        textPanel.add(title);

        add(wrapAvatar(), BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
    }

    private JPanel wrapAvatar() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(avatar, BorderLayout.WEST);
        return pnl;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Utils.Entry> list, Utils.Entry entry, int index, boolean isSelected, boolean cellHasFocus) {
        title.setText(entry.title());

        avatar.setText(entry.title().substring(0, 1).toUpperCase());
        avatar.setBackground(isSelected ? hover : background);
        avatar.setPreferredSize(new Dimension(list.getWidth() / 10, list.getFixedCellHeight()));

        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(7, 10, 10, 10));
        card.setBackground(isSelected ? hover : background);
        card.add(this, BorderLayout.CENTER);
        card.add(setFavourite(isSelected));

        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(0, 0, 8, 0));
        outer.add(card, BorderLayout.CENTER);

        return outer;
    }

    private JLabel setFavourite(boolean isSelected) {
        JLabel icon = new JLabel();

        URL favouriteURL = Main.class.getResource(isSelected ? "/icons/light/filled/favourite_light.png" : "/icons/light/favourite_light.png");
        if (favouriteURL == null) return null;
        BufferedImage original = null;
        try {   original = ImageIO.read(favouriteURL);   } catch (IOException ignored) {}
        Image scaled = original.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        icon.setIcon(new ImageIcon(scaled));

        add(icon, BorderLayout.WEST);

        return icon;
    }

    private Color color(String seed) {
        int hash = Math.abs(seed.hashCode());
        Color[] palette = {
                new Color(39, 134, 192), new Color(162, 37, 37),
                new Color(67, 178, 36), new Color(192, 167, 39),
                new Color(89, 32, 234), new Color(37, 47, 162)
        };
        return palette[hash % palette.length];
    }
}