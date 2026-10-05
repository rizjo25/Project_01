package roadaccident.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeFrame extends JFrame {

    JButton loginButton;
    JButton registrationButton;
    JButton reportAccidentButton;
    JButton checkStatusButton;
    JButton exitButton;

     public static void main(String[] args) {

        HomeFrame frame =
                new HomeFrame();

        frame.setVisible(true);
    }

    public HomeFrame() {

        setTitle("GROUP 17 PROJECT");
        setSize(480, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createHomePage();
    }

    private void createHomePage() {

        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);

        add(createButtonPanel(), BorderLayout.CENTER);

        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4, 1));
  
        JLabel emptyLabel =
                new JLabel("");

        JLabel titleLabel =
                new JLabel("Road Accident");

        titleLabel.setHorizontalAlignment(
                JLabel.CENTER);

        JLabel titleLabel2 =
                new JLabel("Management System");

        titleLabel2.setHorizontalAlignment(
                JLabel.CENTER);

        JLabel subtitleLabel =
                new JLabel(
                        "Report, track, and respond to accidents in real time");

        subtitleLabel.setHorizontalAlignment(
                JLabel.CENTER);

        panel.add(emptyLabel);
        panel.add(titleLabel);
        panel.add(titleLabel2);
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createButtonPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(5, 1, 7, 7));
        panel.setBorder(
        BorderFactory.createEmptyBorder(10, 30, 10, 30));

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

        panel.add(loginButton);
        panel.add(registrationButton);
        panel.add(reportAccidentButton);
        panel.add(checkStatusButton);
        panel.add(exitButton);

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

    private JPanel createFooter() {

        JPanel panel = new JPanel();

        JLabel footerLabel =
                new JLabel(
                        "Emergency response");

        panel.add(footerLabel);

        return panel;
    }

    private void openLoginPage() {

        JOptionPane.showMessageDialog(
                this,
                "Login page will open here.",
                "Coming Soon",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openRegistrationPage() {

        JOptionPane.showMessageDialog(
                this,
                "Registration page will open here.",
                "Coming Soon",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openReportAccidentPage() {

        new QuickAccidentReportFrame().setVisible(true);

        dispose();
    }

    private void openCheckStatusPage() {

       new CheckStatusFrame().setVisible(true);
    }

}