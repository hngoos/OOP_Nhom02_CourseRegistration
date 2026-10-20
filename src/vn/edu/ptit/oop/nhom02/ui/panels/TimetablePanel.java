package vn.edu.ptit.oop.nhom02.ui.panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vn.edu.ptit.oop.nhom02.common.BaseWindow;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Person;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.model.TimeSlot;
import vn.edu.ptit.oop.nhom02.ui.MainWindow;

public class TimetablePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final MainWindow mainWindow;
    private final TimetableCanvas canvas;
    private JLabel lblStudentInfo;

    private static final Color[] SUBJECT_COLORS = {
        new Color(41, 128, 185),
        new Color(39, 174, 96),
        new Color(142, 68, 173),
        new Color(211, 84, 0),
        new Color(192, 57, 43),
        new Color(22, 160, 133),
        new Color(243, 156, 18),
        new Color(52, 73, 94)
    };

    public TimetablePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        lblStudentInfo = new JLabel("Thời khóa biểu tuần");
        lblStudentInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStudentInfo.setForeground(new Color(0, 51, 102));
        headerPanel.add(lblStudentInfo, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnExportPng = new JButton("Xuất TKB ra ảnh PNG", BaseWindow.loadImageIcon("/resources/icons/export.png"));
        btnExportPng.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportPng.setForeground(Color.BLACK);
        btnExportPng.setPreferredSize(new Dimension(180, 32));

        btnExportPng.addActionListener(e -> exportToPng());
        btnPanel.add(btnExportPng);

        headerPanel.add(btnPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        canvas = new TimetableCanvas();
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        refreshData();
    }

    public void refreshData() {
        Person user = mainWindow.getCurrentUser();
        if (user instanceof Student) {
            Student s = (Student) user;
            lblStudentInfo.setText("THỜI KHÓA BIỂU TUẦN - Sinh viên: " + s.getFullName() + " (" + s.getId() + ")");
            List<ClassSection> list = mainWindow.getRepository().getRegisteredSectionsForStudent(s);
            canvas.setSections(list);
        } else if (user != null) {
            lblStudentInfo.setText("LỊCH GIẢNG DẠY TUẦN - " + user.getFullName() + " (" + user.getId() + ")");
            List<ClassSection> list = mainWindow.getRepository().findAllSections();
            List<ClassSection> myTaughtSections = new ArrayList<>();
            for (ClassSection sec : list) {
                if (sec.getLecturer().getId().equals(user.getId())) {
                    myTaughtSections.add(sec);
                }
            }
            canvas.setSections(myTaughtSections);
        }
        canvas.repaint();
    }

    private void exportToPng() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (width <= 0 || height <= 0) {
            width = 1000;
            height = 700;
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        canvas.paint(g2d);
        g2d.dispose();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu Thời Khóa Biểu ra File Ảnh");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Ảnh PNG (*.png)", "png"));

        String defaultFileName = "ThoiKhoaBieu_" + (mainWindow.getCurrentUser() != null ? mainWindow.getCurrentUser().getId() : "PTIT") + ".png";
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }

            try {
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(this,
                    "Xuất ảnh thời khóa biểu thành công!\nĐường dẫn: " + fileToSave.getAbsolutePath(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class TimetableCanvas extends JPanel {
        private static final long serialVersionUID = 1L;

        private List<ClassSection> sections = new ArrayList<>();

        public TimetableCanvas() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(1020, 750));
        }

        public void setSections(List<ClassSection> sections) {
            this.sections = (sections != null) ? new ArrayList<>(sections) : new ArrayList<>();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int startX = 70;
            int startY = 40;
            int totalWidth = getWidth() - startX - 20;
            int totalHeight = getHeight() - startY - 20;

            if (totalWidth < 700) totalWidth = 700;
            if (totalHeight < 550) totalHeight = 550;

            int colWidth = totalWidth / 7;
            int rowHeight = totalHeight / 12;

            String[] days = {"Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"};
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            for (int i = 0; i < 7; i++) {
                int x = startX + i * colWidth;
                g2.setColor(new Color(230, 238, 248));
                g2.fillRect(x, 10, colWidth, startY - 10);
                g2.setColor(new Color(180, 200, 220));
                g2.drawRect(x, 10, colWidth, startY - 10);

                g2.setColor(new Color(0, 51, 102));
                FontMetrics fm = g2.getFontMetrics();
                int textX = x + (colWidth - fm.stringWidth(days[i])) / 2;
                int textY = 10 + ((startY - 10 - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(days[i], textX, textY);
            }

            for (int p = 1; p <= 12; p++) {
                int y = startY + (p - 1) * rowHeight;
                g2.setColor((p <= 6) ? new Color(245, 248, 252) : new Color(253, 248, 242));
                g2.fillRect(10, y, startX - 10, rowHeight);
                g2.setColor(new Color(210, 215, 220));
                g2.drawRect(10, y, startX - 10, rowHeight);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                String label = "Tiết " + p;
                FontMetrics fm = g2.getFontMetrics();
                int textX = 10 + (startX - 10 - fm.stringWidth(label)) / 2;
                int textY = y + (rowHeight - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(label, textX, textY);
            }

            g2.setColor(new Color(235, 235, 235));
            g2.setStroke(new BasicStroke(1));
            for (int r = 0; r < 12; r++) {
                for (int c = 0; c < 7; c++) {
                    int x = startX + c * colWidth;
                    int y = startY + r * rowHeight;
                    g2.drawRect(x, y, colWidth, rowHeight);
                }
            }

            for (int sIdx = 0; sIdx < sections.size(); sIdx++) {
                ClassSection sec = sections.get(sIdx);
                Color subjectColor = SUBJECT_COLORS[sIdx % SUBJECT_COLORS.length];

                for (TimeSlot slot : sec.getTimeSlots()) {
                    int dayIndex = slot.getDayOfWeek() - 2;
                    if (dayIndex < 0 || dayIndex > 6) continue;

                    int startPeriod = slot.getStartPeriod();
                    int endPeriod = slot.getEndPeriod();
                    if (startPeriod < 1 || startPeriod > 12) continue;

                    int x = startX + dayIndex * colWidth + 2;
                    int y = startY + (startPeriod - 1) * rowHeight + 2;
                    int w = colWidth - 4;
                    int h = (endPeriod - startPeriod + 1) * rowHeight - 4;

                    g2.setColor(subjectColor);
                    g2.fillRoundRect(x, y, w, h, 8, 8);

                    g2.setColor(subjectColor.darker());
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(x, y, w, h, 8, 8);

                    g2.setColor(Color.WHITE);
                    int currentTextY = y + 16;

                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    String courseName = sec.getCourse().getName();
                    if (g2.getFontMetrics().stringWidth(courseName) > w - 10) {
                        courseName = sec.getCourse().getCode();
                    }
                    g2.drawString(courseName, x + 6, currentTextY);
                    currentTextY += 15;

                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    g2.drawString("Lớp: " + sec.getSectionId(), x + 6, currentTextY);
                    currentTextY += 14;

                    g2.drawString("Phòng: " + slot.getRoom(), x + 6, currentTextY);
                    currentTextY += 14;
                    g2.drawString("Tiết: " + startPeriod + " - " + endPeriod, x + 6, currentTextY);
                }
            }

            g2.dispose();
        }
    }
}
