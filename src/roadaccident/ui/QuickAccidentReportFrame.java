package roadaccident.ui;

import javax.swing.*;
import java.awt.*;

public class QuickAccidentReportFrame
        extends JFrame {

    private JTextField reporterNameField;
    private JTextField locationField;
    private JComboBox<String> severityBox;
    private JTextField victimCountField;
    private JButton submitButton;

    public QuickAccidentReportFrame() {
        setTitle("Quick Accident Report");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        reporterNameField = new JTextField();
        locationField = new JTextField();
        victimCountField = new JTextField();

        severityBox = new JComboBox<>(
                new String[]{
                    "Minor",
                    "Moderate",
                    "Critical"
                }
        );

        submitButton = new JButton("Submit");

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        panel.add(new JLabel("Reporter Name:"));
        panel.add(reporterNameField);

        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        panel.add(new JLabel("Severity:"));
        panel.add(severityBox);

        panel.add(new JLabel("Victim Count:"));
        panel.add(victimCountField);

        panel.add(new JLabel());
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(e ->
                submitAccident()
        );
    }

    private void submitAccident() {

        String name =
                reporterNameField.getText().trim();

        String location =
                locationField.getText().trim();

        String severity =
                severityBox.getSelectedItem().toString();

        if (name.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Name and location are required."
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Accident report received."
        );
    }
}