package roadaccident.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main class
public class HomeFrame extends JFrame {

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
        setSize(480, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the home page
        createHomePage();
    }

    // Create Home Page
    private void createHomePage() {

        // Main layout
        setLayout(new BorderLayout());

        // Add header
        add(createHeader(), BorderLayout.NORTH);

        // Add buttons
        add(createButtonPanel(), BorderLayout.CENTER);

        // Add footer
        add(createFooter(), BorderLayout.SOUTH);
    }

    // Create Header
    private JPanel createHeader() {

        JPanel panel = new JPanel();

        // Layout
        panel.setLayout(new GridLayout(4, 1));

        // Emergency label
        JLabel emergencyLabel =
                new JLabel("");


        // Title
        JLabel titleLabel =
                new JLabel("Road Accident");

        titleLabel.setHorizontalAlignment(
                JLabel.CENTER);

        // Title 2
        JLabel titleLabel2 =
                new JLabel("Management System");

        titleLabel2.setHorizontalAlignment(
                JLabel.CENTER);

        // Subtitle
        JLabel subtitleLabel =
                new JLabel(
                        "Report, track, and respond to accidents in real time");

        subtitleLabel.setHorizontalAlignment(
                JLabel.CENTER);

        // Add labels
        panel.add(emergencyLabel);
        panel.add(titleLabel);
        panel.add(titleLabel2);
        panel.add(subtitleLabel);

        return panel;
    }

    // Create Button Panel
    private JPanel createButtonPanel() {

        JPanel panel = new JPanel();

        // Grid layout
        panel.setLayout(
                new GridLayout(5, 1, 7, 7));
        panel.setBorder(
        BorderFactory.createEmptyBorder(10, 30, 10, 30));

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

        JLabel footerLabel =
                new JLabel(
                        "Emergency response");

        panel.add(footerLabel);

        return panel;
    }

    // Login
    private void openLoginPage() {

        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
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

       new CheckStatusFrame().setVisible(true);
    }

    // Main method
    public static void main(String[] args) {

        HomeFrame frame =
                new HomeFrame();

        frame.setVisible(true);
    }
}