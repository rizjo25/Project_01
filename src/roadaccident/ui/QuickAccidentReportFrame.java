package roadaccident.ui;

import roadaccident.model.Accident;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class QuickAccidentReportFrame extends JFrame {

    // ---- Same palette as HomeFrame ----
    private static final Color COLOR_BG           = new Color(0x0F172A);
    private static final Color COLOR_PANEL        = new Color(0x1E293B);
    private static final Color COLOR_BORDER       = new Color(0x334155);
    private static final Color COLOR_ACCENT       = new Color(0x38BDF8);
    private static final Color COLOR_DANGER       = new Color(0xF87171);
    private static final Color COLOR_DANGER_DARK  = new Color(0xEF4444);
    private static final Color COLOR_TEXT         = new Color(0xF8FAFC);
    private static final Color COLOR_MUTED        = new Color(0x94A3B8);
    private static final Color COLOR_BUTTON       = new Color(0x334155);
    private static final Color COLOR_BUTTON_HOVER = new Color(0x475569);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_SUB   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BTN   = new Font("Segoe UI", Font.BOLD, 15);

    private static final Border FIELD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            new EmptyBorder(8, 12, 8, 12));
    private static final Border FOCUS_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACCENT, 1),
            new EmptyBorder(8, 12, 8, 12));

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private static final String[] ACCIDENT_TYPES = {
            "Collision", "Hit and Run", "Rollover", "Pedestrian Involved",
            "Motorcycle", "Multi-Vehicle Pileup", "Other"
    };
    private static final String[] SEVERITIES = {"Minor", "Moderate", "Serious", "Critical"};

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField locationField;
    private JComboBox<String> typeBox;
    private JComboBox<String> severityBox;
    private JSpinner victimSpinner;
    private JTextArea descriptionArea;

    public QuickAccidentReportFrame() {
        setTitle("Report Accident");
        setSize(560, 780);
        setMinimumSize(new Dimension(520, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildFormScroll(), BorderLayout.CENTER);
        add(buildButtonBar(), BorderLayout.SOUTH);
    }

    // ---------------- Header ----------------

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(COLOR_BG);
        header.setBorder(new EmptyBorder(28, 32, 12, 32));

        JLabel title = new JLabel("Report an Accident");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Fill in the details so help can be dispatched quickly.");
        sub.setFont(FONT_SUB);
        sub.setForeground(COLOR_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(Box.createRigidArea(new Dimension(0, 6)));
        header.add(sub);
        return header;
    }

    // ---------------- Form ----------------

    private JScrollPane buildFormScroll() {
        nameField = new JTextField();
        phoneField = new JTextField();
        dateField = new JTextField(LocalDate.now().format(DATE_FMT));
        timeField = new JTextField(LocalTime.now().format(TIME_FMT));
        locationField = new JTextField();

        styleField(nameField);
        styleField(phoneField);
        styleField(dateField);
        styleField(timeField);
        styleField(locationField);

        typeBox = createCombo(ACCIDENT_TYPES);
        severityBox = createCombo(SEVERITIES);
        victimSpinner = createSpinner();

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        styleField(descriptionArea);

        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(null);
        descScroll.getViewport().setBackground(COLOR_PANEL);
        descScroll.setPreferredSize(new Dimension(200, 110));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(COLOR_BG);
        form.setBorder(new EmptyBorder(8, 32, 8, 32));

        addRow(form, row(block("YOUR NAME", nameField), block("PHONE NUMBER", phoneField)));
        addRow(form, row(block("DATE (yyyy-MM-dd)", dateField), block("TIME (HH:mm)", timeField)));
        addRow(form, block("LOCATION", locationField));
        addRow(form, row(block("ACCIDENT TYPE", typeBox), block("SEVERITY", severityBox)));
        addRow(form, block("NUMBER OF VICTIMS", victimSpinner));
        addRow(form, block("DESCRIPTION", descScroll));

        JScrollPane scroll = new JScrollPane(form,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel block(String labelText, JComponent field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_MUTED);
        label.setBorder(new EmptyBorder(0, 2, 6, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

        p.add(label);
        p.add(field);
        return p;
    }

    private JPanel row(JComponent... items) {
        JPanel r = new JPanel(new GridLayout(1, items.length, 14, 0));
        r.setOpaque(false);
        for (JComponent item : items) {
            r.add(item);
        }
        return r;
    }

    private void addRow(JPanel form, JComponent row) {
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, row.getPreferredSize().height));
        form.add(row);
        form.add(Box.createRigidArea(new Dimension(0, 16)));
    }

    // ---------------- Styled inputs ----------------

    private void styleField(JTextComponent c) {
        c.setFont(FONT_INPUT);
        c.setBackground(COLOR_PANEL);
        c.setForeground(COLOR_TEXT);
        c.setCaretColor(COLOR_TEXT);
        c.setBorder(FIELD_BORDER);
        if (!(c instanceof JTextArea)) {
            c.setPreferredSize(new Dimension(200, 42));
        }
        c.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                c.setBorder(FOCUS_BORDER);
            }

            @Override
            public void focusLost(FocusEvent e) {
                c.setBorder(FIELD_BORDER);
            }
        });
    }

    private JComboBox<String> createCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                BasicArrowButton b = new BasicArrowButton(
                        BasicArrowButton.SOUTH, COLOR_PANEL, COLOR_PANEL, COLOR_MUTED, COLOR_PANEL);
                b.setBorder(BorderFactory.createEmptyBorder());
                return b;
            }

            @Override
            public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
                super.paintCurrentValue(g, bounds, false); // avoid the system "selected" blue
            }
        });
        combo.setFont(FONT_INPUT);
        combo.setBackground(COLOR_PANEL);
        combo.setForeground(COLOR_TEXT);
        combo.setBorder(FIELD_BORDER);
        combo.setPreferredSize(new Dimension(200, 42));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                l.setBorder(new EmptyBorder(6, 10, 6, 10));
                l.setFont(FONT_INPUT);
                l.setBackground(isSelected ? COLOR_ACCENT : COLOR_PANEL);
                l.setForeground(isSelected ? COLOR_BG : COLOR_TEXT);
                return l;
            }
        });
        return combo;
    }

    private JSpinner createSpinner() {
        JSpinner s = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));
        s.setFont(FONT_INPUT);
        s.setBackground(COLOR_PANEL);
        s.setBorder(FIELD_BORDER);
        s.setPreferredSize(new Dimension(200, 42));

        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) s.getEditor();
        editor.setBackground(COLOR_PANEL);
        JTextField tf = editor.getTextField();
        tf.setBackground(COLOR_PANEL);
        tf.setForeground(COLOR_TEXT);
        tf.setCaretColor(COLOR_TEXT);
        tf.setFont(FONT_INPUT);
        tf.setBorder(new EmptyBorder(0, 2, 0, 2));
        return s;
    }

    // ---------------- Buttons ----------------

    private JPanel buildButtonBar() {
        JPanel bar = new JPanel(new GridLayout(1, 2, 14, 0));
        bar.setBackground(COLOR_BG);
        bar.setBorder(new EmptyBorder(14, 32, 24, 32));

        FormButton backButton = new FormButton("Back", COLOR_BUTTON, COLOR_BUTTON_HOVER, COLOR_TEXT);
        FormButton submitButton = new FormButton("Submit Report", COLOR_DANGER, COLOR_DANGER_DARK, COLOR_BG);
        backButton.setPreferredSize(new Dimension(150, 48));
        submitButton.setPreferredSize(new Dimension(150, 48));

        backButton.addActionListener(e -> goHome());
        submitButton.addActionListener(e -> submitReport());

        bar.add(backButton);
        bar.add(submitButton);

        getRootPane().setDefaultButton(submitButton);
        return bar;
    }

    // ---------------- Actions ----------------

    private void submitReport() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim().replaceAll("[\\s-]", "");
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty()) {
            fail("Please enter your name.", nameField);
            return;
        }
        if (!phone.matches("\\+?[0-9]{7,15}")) {
            fail("Please enter a valid phone number (7 to 15 digits).", phoneField);
            return;
        }
        try {
            LocalDate.parse(date, DATE_FMT);
        } catch (DateTimeParseException ex) {
            fail("Date must be a real date in yyyy-MM-dd format.", dateField);
            return;
        }
        try {
            LocalTime.parse(time, TIME_FMT);
        } catch (DateTimeParseException ex) {
            fail("Time must be in 24-hour HH:mm format.", timeField);
            return;
        }
        if (location.isEmpty()) {
            fail("Please enter the accident location.", locationField);
            return;
        }
        if (description.isEmpty()) {
            fail("Please describe what happened.", descriptionArea);
            return;
        }

        Accident accident = new Accident(
                name,
                phone,
                date,
                time,
                location,
                (String) typeBox.getSelectedItem(),
                (String) severityBox.getSelectedItem(),
                (Integer) victimSpinner.getValue(),
                description
        );

        saveAccident(accident);

        JOptionPane.showMessageDialog(
                this,
                "Accident reported successfully.\n\nType: " + accident.getAccidentType()
                        + "\nSeverity: " + accident.getSeverity()
                        + "\nLocation: " + accident.getLocation(),
                "Report Submitted",
                JOptionPane.INFORMATION_MESSAGE
        );
        goHome();
    }

    private void saveAccident(Accident accident) {
        // Later: save to database / file, e.g. AccidentDAO.save(accident);
    }

    private void fail(String message, JComponent target) {
        JOptionPane.showMessageDialog(this, message, "Check the form", JOptionPane.WARNING_MESSAGE);
        target.requestFocusInWindow();
    }

    private void goHome() {
        new HomeFrame().setVisible(true);
        dispose();
    }

    // ---------------- Rounded, hover-aware button ----------------

    private static class FormButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private boolean hovered = false;

        FormButton(String text, Color baseColor, Color hoverColor, Color textColor) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;

            setFont(FONT_BTN);
            setForeground(textColor);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hovered ? hoverColor : baseColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}