package roadaccident.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class HomeFrame extends JFrame {

    static {
        AppTheme.applyTheme();
    }

    private static final Color BACKGROUND =
            new Color(245, 248, 252);
    private static final Color NAVY =
            new Color(27, 54, 93);
    private static final Color BLUE =
            new Color(37, 99, 180);

    // Buttons
    JButton loginButton;
    JButton registrationButton;
    JButton reportAccidentButton;
    JButton checkStatusButton;
    JButton exitButton;

    // Constructor
    public HomeFrame() {

        // JFrame settings
        setTitle("Road Accident Management System");
        setSize(480, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        // Create the home page
        createHomePage();
    }

    // Create Home Page
    private void createHomePage() {

        // Main layout
        setLayout(new BorderLayout());

        // Add header
        add(createHeader(), BorderLayout.NORTH);

        // Keep the button panel at its original size as the header grows.
        JPanel buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setBackground(BACKGROUND);
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setPreferredSize(new Dimension(480, 410));
        buttonContainer.add(buttonPanel);
        add(buttonContainer, BorderLayout.CENTER);

        // Add footer
        add(createFooter(), BorderLayout.SOUTH);
    }

    // Create Header
    private JPanel createHeader() {

        JPanel panel = new JPanel();
        panel.setBackground(NAVY);
        panel.setBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        URL logoResource = getClass().getResource(
                "/roadaccident/resources/rams.png"
        );
        ImageIcon logoIcon = logoResource == null
                ? new ImageIcon("src/roadaccident/resources/rams.png")
                : new ImageIcon(logoResource);
        if (logoIcon.getIconWidth() > 0) {
            logoLabel.setIcon(new ImageIcon(
                    logoIcon.getImage().getScaledInstance(
                            120,
                            120,
                            Image.SCALE_SMOOTH
                    )
            ));
        } else {
            System.err.println(
                    "Could not load src/roadaccident/resources/rams.png"
            );
        }

        // Title
        JLabel titleLabel =
                new JLabel("Road Accident");

        titleLabel.setHorizontalAlignment(
                JLabel.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        // Title 2
        JLabel titleLabel2 =
                new JLabel("Management System");

        titleLabel2.setHorizontalAlignment(
                JLabel.CENTER);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel2.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel2.setForeground(Color.WHITE);

        // Subtitle
        JLabel subtitleLabel =
                new JLabel(
                        "Report, track, and respond to accidents in real time");

        subtitleLabel.setHorizontalAlignment(
                JLabel.CENTER);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(225, 233, 243));

        // Add labels
        panel.add(logoLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(3));
        panel.add(titleLabel2);
        panel.add(Box.createVerticalStrut(3));
        panel.add(subtitleLabel);

        return panel;
    }

    // Create Button Panel
    private JPanel createButtonPanel() {

        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);

        // Grid layout
        panel.setLayout(
                new GridLayout(5, 1, 7, 7));
        panel.setBorder(
        BorderFactory.createEmptyBorder(18, 30, 18, 30));

        // Create buttons
        loginButton =
                new JButton("Login");

        registrationButton =
                new JButton("New Registration");

        reportAccidentButton =
                new JButton("Report Accident");

        checkStatusButton =
                new JButton("Check Status");

        exitButton =
                new JButton("Exit");

        for (JButton button : new JButton[]{
                loginButton,
                registrationButton,
                reportAccidentButton,
                checkStatusButton,
                exitButton
        }) {
            button.setBackground(BLUE);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.PLAIN, 35));
        }

        // Add buttons to panel
        panel.add(loginButton);
        panel.add(registrationButton);
        panel.add(reportAccidentButton);
        panel.add(checkStatusButton);
        panel.add(exitButton);

        // Event handling
        loginButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        openLoginPage();
                    }
                });

        registrationButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        openRegistrationPage();
                    }
                });

        reportAccidentButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        openReportAccidentPage();
                    }
                });

        checkStatusButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        openCheckStatusPage();
                    }
                });

        exitButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });

        return panel;
    }

    // Create Footer
    private JPanel createFooter() {

        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);

        JLabel footerLabel =
                new JLabel(
                        "Emergency response");
        footerLabel.setForeground(new Color(90, 100, 115));

        panel.add(footerLabel);

        return panel;
    }

    // Login
    private void openLoginPage() {

        setVisible(false);
        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    // Registration
    private void openRegistrationPage() {

        new RegisterFrame().setVisible(true);

        dispose();
    }

    // Report Accident
    private void openReportAccidentPage() {

        new QuickAccidentReportFrame().setVisible(true);

        dispose();
    }

    // Check Status
    private void openCheckStatusPage() {
        setVisible(false);
        new CheckStatusFrame(this).setVisible(true);
    }

    // Main method
    public static void main(String[] args) {

        HomeFrame frame =
                new HomeFrame();

        frame.setVisible(true);
    }
}