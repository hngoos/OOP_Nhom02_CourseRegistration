package vn.edu.ptit.oop.nhom02.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public abstract class BaseWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    protected JMenuBar menuBar;
    protected JMenu fileMenu, viewMenu, helpMenu;
    protected JToolBar toolBar;
    protected JPanel statusBar;
    protected JLabel statusLabel;

    public BaseWindow(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadIcon();
        initMenuBar();
        initToolBar();
        initStatusBar();
        initUI();
    }

    protected abstract void initUI();

    protected void showAbout() {
        JOptionPane.showMessageDialog(this, getTitle(), "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setStatus(String msg) {
        if (statusLabel != null) {
            statusLabel.setText(" " + msg);
        }
    }

    public static ImageIcon loadImageIcon(String path) {
        URL url = BaseWindow.class.getResource(path);
        if (url == null) {

            java.io.File file = new java.io.File("resources" + (path.startsWith("/resources") ? path.substring(10) : path));
            if (file.exists()) {
                return new ImageIcon(file.getAbsolutePath());
            }
            java.io.File file2 = new java.io.File("OOP_Nhom02_CourseRegistration" + path);
            if (file2.exists()) {
                return new ImageIcon(file2.getAbsolutePath());
            }
            return null;
        }
        return new ImageIcon(url);
    }

    protected JButton addToolButton(String text, String iconPath, String tooltip, ActionListener action) {
        ImageIcon icon = loadImageIcon(iconPath);
        JButton btn = (icon != null) ? new JButton(text, icon) : new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setToolTipText(tooltip);
        btn.setFocusable(false);
        if (action != null) {
            btn.addActionListener(action);
        }
        toolBar.add(btn);
        toolBar.addSeparator(new Dimension(5, 24));
        return btn;
    }

    protected JButton addToolButton(String iconPath, String tooltip, ActionListener action) {
        return addToolButton(tooltip, iconPath, tooltip, action);
    }

    private void loadIcon() {
        ImageIcon icon = loadImageIcon("/resources/icons/app.png");
        if (icon != null) {
            setIconImage(icon.getImage());
        }
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        exitItem.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        fileMenu.add(exitItem);

        JMenuItem aboutItem = new JMenuItem("About...");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);
    }

    private void initStatusBar() {
        statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        statusLabel = new JLabel(" Sẵn sàng");
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar, BorderLayout.SOUTH);
    }
}
