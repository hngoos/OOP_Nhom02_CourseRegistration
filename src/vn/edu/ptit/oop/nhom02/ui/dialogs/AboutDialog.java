package vn.edu.ptit.oop.nhom02.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public AboutDialog(Frame parent) {
        super(parent, "Thông tin ứng dụng - Nhóm 02", true);
        setSize(480, 360);
        setLocationRelativeTo(parent);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 86, 179));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel("HỆ THỐNG ĐĂNG KÝ HỌC PHẦN (PTIT)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JTextArea infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoText.setBackground(contentPanel.getBackground());
        infoText.setText(
            "• Môn học: Lập trình hướng đối tượng (INT1332)\n" +
            "• Đề tài: Đăng ký Học phần (Course Registration)\n" +
            "• Nhóm thực hiện: Nhóm 02\n" +
            "• Đơn vị: Học viện Công nghệ Bưu chính Viễn thông - Cơ sở TP.HCM\n\n" +
            "Kiến trúc kỹ thuật:\n" +
            "- Java Swing (JFrame, JDialog, JTable, Graphics2D)\n" +
            "- Trực quan hóa dữ liệu với JFreeChart 1.5.4\n" +
            "- 4 nguyên lý OOP: Đóng gói, Kế thừa, Đa hình, Trừu tượng\n" +
            "- Cây ngoại lệ tự định nghĩa: RegistrationException\n"
        );
        contentPanel.add(new JScrollPane(infoText));
        add(contentPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JButton closeBtn = new JButton("Đóng");
        closeBtn.setPreferredSize(new Dimension(90, 30));
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
