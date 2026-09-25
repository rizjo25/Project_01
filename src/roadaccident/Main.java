package roadaccident;

import javax.swing.SwingUtilities;
import roadaccident.ui.QuickAccidentReportFrame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuickAccidentReportFrame frame =
                    new QuickAccidentReportFrame();

            frame.setVisible(true);
        });
    }
}