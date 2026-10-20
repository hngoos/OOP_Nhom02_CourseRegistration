package vn.edu.ptit.oop.nhom02.ui.panels;

import javax.swing.JComponent;
import vn.edu.ptit.oop.nhom02.ui.MainWindow;

public interface RoleViewStrategy {

    void setupViews(MainWindow mainWindow);

    void refreshViews();
}
