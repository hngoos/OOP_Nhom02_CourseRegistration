package vn.edu.ptit.oop.nhom02.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.util.ChartHelper;

public class ChartWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    public ChartWindow(Frame parent, List<ClassSection> allSections, Student student, List<ClassSection> registeredSections) {
        super("Thống kê & Trực quan hóa dữ liệu (JFreeChart) - Nhóm 02");
        setSize(950, 620);
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI(allSections, student, registeredSections);
    }

    private void initUI(List<ClassSection> allSections, Student student, List<ClassSection> registeredSections) {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JFreeChart barChart = ChartHelper.createFillRateBarChart(allSections);
        ChartPanel barPanel = new ChartPanel(barChart);
        barPanel.setPreferredSize(new Dimension(900, 550));
        tabbedPane.addTab("Tỷ lệ lấp đầy Môn học phần (BarChart)", barPanel);

        JFreeChart pieChart = ChartHelper.createCreditsDistributionPieChart(student, registeredSections);
        ChartPanel piePanel = new ChartPanel(pieChart);
        piePanel.setPreferredSize(new Dimension(900, 550));
        tabbedPane.addTab("Phân bố Tín chỉ theo Khối kiến thức (PieChart)", piePanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
