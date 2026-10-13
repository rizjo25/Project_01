package roadaccident.ui;

import roadaccident.dao.AmbulanceDriverDAO;
import roadaccident.model.AccidentAlert;
import roadaccident.model.Hospital;
import roadaccident.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AmbulanceDriverFrame extends JFrame {

    static {
        AppTheme.applyTheme();
    }

    private final User user;
    private final AmbulanceDriverDAO driverDAO =
            new AmbulanceDriverDAO();

    private final DefaultTableModel accidentTableModel =
            new DefaultTableModel(
                    new String[]{
                            "Accident ID",
                            "Location",
                            "Reporter Phone",
                            "Severity",
                            "Victims",
                            "Description",
                            "Status",
                            "Pickup Time"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final DefaultTableModel hospitalTableModel =
            new DefaultTableModel(
                    new String[]{
                            "Hospital ID",
                            "Hospital",
                            "Zone",
                            "Available Beds",
                            "Phone"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable accidentTable =
            new JTable(accidentTableModel);
    private final JTable hospitalTable =
            new JTable(hospitalTableModel);

    public AmbulanceDriverFrame(User user) {
        this.user = user;

        setTitle("Ambulance Driver Dashboard");
        setSize(1100, 720);
        setMinimumSize(new Dimension(800, 520));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel welcomeLabel = new JLabel(
                "Welcome " + user.getFullName()
                        + " | Zone: " + user.getZone(),
                SwingConstants.LEFT
        );

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshDashboard());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logoutToHome());

        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.add(welcomeLabel, BorderLayout.CENTER);

        JPanel headerButtons =
                new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerButtons.add(refreshButton);
        headerButtons.add(logoutButton);
        header.add(headerButtons, BorderLayout.EAST);

        accidentTable.setRowHeight(26);
        accidentTable.setAutoCreateRowSorter(true);
        TableLayout.wrapCellsAndFitRows(accidentTable);
        JScrollPane accidentsPane = new JScrollPane(accidentTable);
        accidentsPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        accidentsPane.setBorder(BorderFactory.createTitledBorder(
                "Accident Reports in Your Zone"
        ));

        hospitalTable.setRowHeight(26);
        hospitalTable.setAutoCreateRowSorter(true);
        hospitalTable.getColumnModel().getColumn(0).setMinWidth(0);
        hospitalTable.getColumnModel().getColumn(0).setMaxWidth(0);
        hospitalTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        TableLayout.wrapCellsAndFitRows(hospitalTable);
        JScrollPane hospitalsPane = new JScrollPane(hospitalTable);
        hospitalsPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        hospitalsPane.setBorder(BorderFactory.createTitledBorder(
                "Hospitals with Available Beds in Your Zone"
        ));

        JSplitPane dataSplitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                accidentsPane,
                hospitalsPane
        );
        dataSplitPane.setResizeWeight(0.58);
        dataSplitPane.setContinuousLayout(true);

        JButton pickupButton =
                new JButton("Victim Picked Up - Inform Hospital");
        pickupButton.addActionListener(e -> pickUpVictim());

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 8, 0)
        );
        actionPanel.add(pickupButton);

        panel.add(header, BorderLayout.NORTH);
        panel.add(dataSplitPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        refreshDashboard();
    }

    private void refreshDashboard() {
        try {
            List<AccidentAlert> accidents =
                    driverDAO.getAccidentsByZone(user.getZone());
            List<Hospital> hospitals =
                    driverDAO.getHospitalsByZone(user.getZone());

            accidentTableModel.setRowCount(0);
            for (AccidentAlert accident : accidents) {
                accidentTableModel.addRow(new Object[]{
                        accident.getAccidentId(),
                        accident.getLocation(),
                        accident.getReporterPhone(),
                        accident.getSeverity(),
                        accident.getVictimCount(),
                        accident.getDescription(),
                        accident.getStatus(),
                        accident.getPickupTime()
                });
            }

            hospitalTableModel.setRowCount(0);
            for (Hospital hospital : hospitals) {
                hospitalTableModel.addRow(new Object[]{
                        hospital.getHospitalId(),
                        hospital.getHospitalName(),
                        hospital.getZone(),
                        hospital.getAvailableBeds(),
                        hospital.getPhoneNumber()
                });
            }
        } catch (SQLException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load dashboard data.\n" + ex.getMessage(),
                    "Dashboard Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void pickUpVictim() {
        int selectedAccidentViewRow =
                accidentTable.getSelectedRow();
        int selectedHospitalViewRow =
                hospitalTable.getSelectedRow();

        if (selectedAccidentViewRow < 0) {
            showSelectionWarning("Select an accident report first.");
            return;
        }

        if (selectedHospitalViewRow < 0) {
            showSelectionWarning("Select a hospital with available beds.");
            return;
        }

        int accidentModelRow =
                accidentTable.convertRowIndexToModel(
                        selectedAccidentViewRow
                );
        int hospitalModelRow =
                hospitalTable.convertRowIndexToModel(
                        selectedHospitalViewRow
                );

        int accidentId = (Integer) accidentTableModel.getValueAt(
                accidentModelRow,
                0
        );
        int hospitalId = (Integer) hospitalTableModel.getValueAt(
                hospitalModelRow,
                0
        );

        String hospitalName = hospitalTableModel.getValueAt(
                hospitalModelRow,
                1
        ).toString();

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Confirm victim pickup for accident ACC-" + accidentId
                        + " and destination " + hospitalName + "?",
                "Confirm Pickup",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Timestamp pickupTime =
                    driverDAO.recordPickupAndNotifyHospital(
                            accidentId,
                            user.getUserId(),
                            hospitalId
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Victim pickup recorded.\n"
                            + "Pickup time: " + pickupTime
                            + "\n\nHospital notification:\n"
                            + "Accident ACC-" + accidentId
                            + ": a victim has been picked up and is being "
                            + "taken to " + hospitalName
                            + ". Pickup time: " + pickupTime,
                    "Pickup Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );
            refreshDashboard();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not record victim pickup.\n" + ex.getMessage(),
                    "Pickup Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showSelectionWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Select Items",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void logoutToHome() {
        HomeFrame homeFrame = new HomeFrame();
        homeFrame.setVisible(true);
        dispose();
    }
}
