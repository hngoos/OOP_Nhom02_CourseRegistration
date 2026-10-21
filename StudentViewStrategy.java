package vn.edu.ptit.oop.nhom02.ui.panels;

import javax.swing.JTabbedPane;
import vn.edu.ptit.oop.nhom02.ui.MainWindow;

public class StudentViewStrategy implements RoleViewStrategy {

    private RegistrationPanel registrationPanel;
    private TimetablePanel timetablePanel;

    @Override
    public void setupViews(MainWindow mainWindow) {
        JTabbedPane tabbedPane = mainWindow.getMainTabbedPane();
        tabbedPane.removeAll();

        registrationPanel = new RegistrationPanel(mainWindow);
        timetablePanel = new TimetablePanel(mainWindow);

        tabbedPane.addTab("Đăng Ký Học Phần", registrationPanel);
        tabbedPane.addTab("Thời Khóa Biểu Tuần", timetablePanel);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == timetablePanel) {
                timetablePanel.refreshData();
            }
        });
    }

    @Override
    public void refreshViews() {
        if (registrationPanel != null) registrationPanel.refreshData();
        if (timetablePanel != null) timetablePanel.refreshData();
    }

    public RegistrationPanel getRegistrationPanel() {
        return registrationPanel;
    }

    public TimetablePanel getTimetablePanel() {
        return timetablePanel;
    }
}
