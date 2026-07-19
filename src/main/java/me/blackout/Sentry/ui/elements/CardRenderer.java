package me.blackout.Sentry.ui.elements;

import me.blackout.Sentry.utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
        setBorder(new EmptyBorder(6, 4, 6, 4));
        setOpaque(false);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        title.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 14f));
        title.setAlignmentY(SwingConstants.CENTER);
        title.setForeground(textColor);

        avatar.setOpaque(false);
        avatar.setForeground(textColor);
        avatar.setSize(new Dimension(38, 38));
        avatar.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        avatar.setFont(Utils.spaceGrotesk.deriveFont(Font.BOLD, 15f));

        textPanel.add(avatar);
        textPanel.add(title);

        add(wrapAvatar(), BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
    }

    private JPanel wrapAvatar() {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(0, 8, 0, 0));
        pnl.add(avatar, BorderLayout.CENTER);
        return pnl;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Utils.Entry> list, Utils.Entry entry, int index, boolean isSelected, boolean cellHasFocus) {
        title.setText(entry.title());

        avatar.setText(entry.title().substring(0, 1).toUpperCase());
        avatar.setBackground(color(entry.title()));

        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(new LineBorder(border, 8, new EmptyBorder(0, 0, 0, 0)));
        card.setBackground(isSelected ? hover : background);
        card.add(this, BorderLayout.CENTER);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(4, 0, 4, 0));
        outer.add(card, BorderLayout.CENTER);

        return outer;
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