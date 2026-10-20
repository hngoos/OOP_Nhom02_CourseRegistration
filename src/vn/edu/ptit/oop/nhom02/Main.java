package vn.edu.ptit.oop.nhom02;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vn.edu.ptit.oop.nhom02.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
