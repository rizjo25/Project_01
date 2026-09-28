package roadaccident.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class HomeFrame extends JFrame {

    // ---- Modern color palette ----
    private static final Color COLOR_BG            = new Color(0x0F172A); // slate-900
    private static final Color COLOR_PANEL         = new Color(0x1E293B); // slate-800
    private static final Color COLOR_ACCENT        = new Color(0x38BDF8); // sky-400
    private static final Color COLOR_ACCENT_DARK   = new Color(0x0EA5E9); // sky-500
    private static final Color COLOR_DANGER        = new Color(0xF87171); // red-400
    private static final Color COLOR_DANGER_DARK   = new Color(0xEF4444); // red-500
    private static final Color COLOR_TEXT_PRIMARY  = new Color(0xF8FAFC); // slate-50
    private static final Color COLOR_TEXT_MUTED    = new Color(0x94A3B8); // slate-400
    private static final Color COLOR_BUTTON_NEUTRAL= new Color(0x334155); // slate-700
    private static final Color COLOR_BUTTON_HOVER  = new Color(0x475569); // slate-600

    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON   = new Font("Segoe UI", Font.BOLD, 15);

    private RoundedButton loginButton;
    private RoundedButton registrationButton;
    private RoundedButton reportAccidentButton;
    private RoundedButton checkStatusButton;
    private RoundedButton exitButton;

    public HomeFrame() {
        setTitle("Road Accident Management System");
        setSize(480, 620);
        setMinimumSize(new Dimension(420, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        createHomePage();
    }

    private void createHomePage() {
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildButtonPanel(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    // ---------------- Header ----------------

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(COLOR_BG);
        header.setBorder(new EmptyBorder(48, 40, 24, 40));

        JLabel badge = new JLabel("EMERGENCY RESPONSE");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setForeground(COLOR_ACCENT);
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);
        badge.setIconTextGap(8);
        badge.setIcon(new DotIcon(COLOR_ACCENT, 7));
        badge.setHorizontalTextPosition(SwingConstants.RIGHT);

        JLabel titleLabel = new JLabel("Road Accident");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel2 = new JLabel("Management System");
        titleLabel2.setFont(FONT_TITLE);
        titleLabel2.setForeground(COLOR_TEXT_PRIMARY);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
                "<html><div style='text-align:center;width:280px;'>"
                        + "Report, track, and respond to accidents in real time</div></html>"
        );
        subtitleLabel.setFont(FONT_SUBTITLE);
        subtitleLabel.setForeground(COLOR_TEXT_MUTED);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(badge);
        header.add(Box.createRigidArea(new Dimension(0, 10)));
        header.add(titleLabel);
        header.add(titleLabel2);
        header.add(Box.createRigidArea(new Dimension(0, 8)));
        header.add(subtitleLabel);

        return header;
    }

    // ---------------- Buttons ----------------

    private JPanel buildButtonPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(COLOR_BG);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 48, 10, 48));

        loginButton = new RoundedButton("Login", IconType.ARROW, COLOR_ACCENT, COLOR_ACCENT_DARK, COLOR_BG);
        registrationButton = new RoundedButton("New Registration", IconType.PLUS, COLOR_BUTTON_NEUTRAL, COLOR_BUTTON_HOVER, COLOR_TEXT_PRIMARY);
        reportAccidentButton = new RoundedButton("Report Accident", IconType.WARNING, COLOR_DANGER, COLOR_DANGER_DARK, COLOR_BG);
        checkStatusButton = new RoundedButton("Check Status", IconType.CHECK, COLOR_BUTTON_NEUTRAL, COLOR_BUTTON_HOVER, COLOR_TEXT_PRIMARY);
        exitButton = new RoundedButton("Exit", IconType.CROSS, null, null, COLOR_TEXT_MUTED);
        exitButton.setIsGhost(true);

        for (RoundedButton b : new RoundedButton[]{
                loginButton, registrationButton, reportAccidentButton, checkStatusButton
        }) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            buttonPanel.add(b);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        }

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        buttonPanel.add(exitButton);

        wrapper.add(buttonPanel);

        loginButton.addActionListener(e -> openLoginPage());
        registrationButton.addActionListener(e -> openRegistrationPage());
        reportAccidentButton.addActionListener(e -> openReportAccidentPage());
        checkStatusButton.addActionListener(e -> openCheckStatusPage());
        exitButton.addActionListener(e -> System.exit(0));

        return wrapper;
    }

    // ---------------- Footer ----------------

    private JPanel buildFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(COLOR_BG);
        footer.setBorder(new EmptyBorder(0, 0, 24, 0));

        JLabel footerLabel = new JLabel("24/7 Emergency Hotline Available");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(COLOR_TEXT_MUTED);

        footer.add(footerLabel);
        return footer;
    }

    // ---------------- Actions ----------------

    private void openLoginPage() {
        showComingSoon("Login page will open here.");
        // Later:
        // new LoginFrame().setVisible(true);
        // dispose();
    }

    private void openRegistrationPage() {
        showComingSoon("Registration page will open here.");
        // Later:
        // new RegistrationFrame().setVisible(true);
        // dispose();
    }

    private void openReportAccidentPage() {
        showComingSoon("Accident report page will open here.");
        // Later:
        // new QuickAccidentReportFrame().setVisible(true);
        // dispose();
    }

    private void openCheckStatusPage() {
        showComingSoon("Status checking page will open here.");
        // Later:
        // new CheckStatusFrame().setVisible(true);
        // dispose();
    }

    private void showComingSoon(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Coming Soon",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ---------------- Vector icon types (no font-glyph dependency) ----------------

    private enum IconType { ARROW, PLUS, WARNING, CHECK, CROSS }

    // ---------------- Custom rounded, hover-aware button ----------------

    private static class RoundedButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private final Color textColor;
        private final IconType iconType;
        private boolean hovered = false;
        private boolean isGhost = false;

        RoundedButton(String text, IconType iconType, Color baseColor, Color hoverColor, Color textColor) {
            super(text);
            this.iconType = iconType;
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.textColor = textColor;

            setFont(FONT_BUTTON);
            setForeground(textColor);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setHorizontalAlignment(SwingConstants.CENTER);
            setIconTextGap(10);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }

        void setIsGhost(boolean ghost) {
            this.isGhost = ghost;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 14;
            RoundRectangle2D shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc);

            if (!isGhost) {
                g2.setColor(hovered ? hoverColor : baseColor);
                g2.fill(shape);
            } else if (hovered) {
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fill(shape);
            }

            // Compute icon + gap + text as a single centered unit (don't use the
            // default text painting — it centers the text on its own and ignores
            // the icon, which is why the gap never lined up visually).
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(getText());
            int iconSize = 14;
            int gap = getIconTextGap();
            int totalWidth = iconSize + gap + textWidth;

            int startX = (getWidth() - totalWidth) / 2;
            int cy = getHeight() / 2;

            int iconX = startX;
            int textX = startX + iconSize + gap;
            int textY = cy + (fm.getAscent() - fm.getDescent()) / 2;

            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(textColor);
            drawIcon(g2, iconType, iconX, cy, iconSize);

            g2.setColor(textColor);
            g2.drawString(getText(), textX, textY);

            g2.dispose();
        }

        private void drawIcon(Graphics2D g2, IconType type, int x, int cy, int size) {
            int half = size / 2;
            switch (type) {
                case ARROW: {
                    int y = cy;
                    g2.drawLine(x, y, x + size - 3, y);
                    g2.drawLine(x + size - 7, y - 5, x + size - 2, y);
                    g2.drawLine(x + size - 7, y + 5, x + size - 2, y);
                    break;
                }
                case PLUS: {
                    g2.drawLine(x + half, cy - half, x + half, cy + half);
                    g2.drawLine(x, cy, x + size, cy);
                    break;
                }
                case WARNING: {
                    int[] xs = {x + half, x, x + size};
                    int[] ys = {cy - half - 2, cy + half, cy + half};
                    g2.drawPolygon(xs, ys, 3);
                    g2.fillOval(x + half - 1, cy + half - 5, 3, 3);
                    g2.drawLine(x + half, cy - 3, x + half, cy + 3);
                    break;
                }
                case CHECK: {
                    g2.drawLine(x, cy, x + half - 1, cy + half - 2);
                    g2.drawLine(x + half - 1, cy + half - 2, x + size, cy - half + 2);
                    break;
                }
                case CROSS: {
                    g2.drawLine(x, cy - half, x + size, cy + half);
                    g2.drawLine(x + size, cy - half, x, cy + half);
                    break;
                }
            }
        }

        @Override
        public boolean contains(int x, int y) {
            return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14).contains(x, y);
        }
    }

    // ---------------- Small filled-dot icon (used in the header badge) ----------------

    private static class DotIcon implements Icon {
        private final Color color;
        private final int size;

        DotIcon(Color color, int size) {
            this.color = color;
            this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillOval(x, y + 2, size, size);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new HomeFrame().setVisible(true);
        });
    }
}