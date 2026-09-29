package roadaccident.ui;

import roadaccident.dao.AccidentDAO;
import roadaccident.model.Accident;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckStatusFrame extends JFrame {

    private JTextField idField;

    private JLabel resultLabel;

    public CheckStatusFrame() {

        setTitle("Check Accident Status");

        setSize(500, 500);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // -----------------------------
        // Title
        // -----------------------------

        JLabel title =
                new JLabel("Check Accident Status");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setHorizontalAlignment(
                JLabel.CENTER
        );

        add(title, BorderLayout.NORTH);

        // -----------------------------
        // Input Panel
        // -----------------------------

        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(
                new FlowLayout()
        );

        JLabel idLabel =
                new JLabel("Accident ID:");

        idField =
                new JTextField(10);

        JButton checkButton =
                new JButton("Check Status");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(checkButton);

        add(
                inputPanel,
                BorderLayout.CENTER
        );

        // -----------------------------
        // Result
        // -----------------------------

        resultLabel =
                new JLabel();

        resultLabel.setVerticalAlignment(
                JLabel.TOP
        );

        resultLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        add(
                resultLabel,
                BorderLayout.SOUTH
        );

        // -----------------------------
        // Button Event
        // -----------------------------

        checkButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        checkStatus();
                    }
                }
        );
    }

    // Check accident status
    private void checkStatus() {

        String idText =
                idField.getText().trim();

        // Check empty input
        if (idText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Accident ID.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id;

        // Convert String to int
        try {

            id = Integer.parseInt(idText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Accident ID must be a number.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Get accident from database
        Accident accident =
                AccidentDAO.getAccidentById(id);

        // If no accident found
        if (accident == null) {

            resultLabel.setText(
                    "<html>"
                    + "<h3>No accident found.</h3>"
                    + "Please check the Accident ID."
                    + "</html>"
            );

            return;
        }

        // Display accident details
        resultLabel.setText(
                "<html>"
                + "<h3>Accident Found</h3>"
                + "<b>Accident ID:</b> "
                + accident.getId()
                + "<br>"
                + "<b>Name:</b> "
                + accident.getName()
                + "<br>"
                + "<b>Date:</b> "
                + accident.getDate()
                + "<br>"
                + "<b>Time:</b> "
                + accident.getTime()
                + "<br>"
                + "<b>Location:</b> "
                + accident.getLocation()
                + "<br>"
                + "<b>Type:</b> "
                + accident.getAccidentType()
                + "<br>"
                + "<b>Severity:</b> "
                + accident.getSeverity()
                + "<br>"
                + "<b>Victims:</b> "
                + accident.getNumberOfVictims()
                + "<br>"
                + "<b>Description:</b> "
                + accident.getDescription()
                + "</html>"
        );
    }
}