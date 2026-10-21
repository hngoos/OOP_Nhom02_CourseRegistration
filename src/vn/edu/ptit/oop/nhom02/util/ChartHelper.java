package vn.edu.ptit.oop.nhom02.util;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Student;

public class ChartHelper {

    static {

        applyVietnameseChartTheme();
    }

    public static void applyVietnameseChartTheme() {
        StandardChartTheme theme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();
        theme.setExtraLargeFont(new Font("Segoe UI", Font.BOLD, 17));
        theme.setLargeFont(new Font("Segoe UI", Font.BOLD, 13));
        theme.setRegularFont(new Font("Segoe UI", Font.PLAIN, 12));
        ChartFactory.setChartTheme(theme);
    }

    public static JFreeChart createFillRateBarChart(List<ClassSection> sections) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (sections != null) {
            for (ClassSection sec : sections) {
                double fillPercent = (sec.getCapacity() > 0)
                        ? ((double) sec.getEnrolledCount() / sec.getCapacity()) * 100.0
                        : 0.0;
                dataset.addValue(fillPercent, "Tỷ lệ lấp đầy (%)", sec.getCourse().getCode());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "THỐNG KÊ TỶ LỆ LẤP ĐẦY CÁC MÔN HỌC PHẦN",
                "Mã Môn Học",
                "Tỷ lệ lấp đầy (%)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(248, 250, 252));
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 100.0);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(41, 128, 185));
        renderer.setMaximumBarWidth(0.08);

        return chart;
    }

    public static JFreeChart createCreditsDistributionPieChart(Student student, List<ClassSection> registeredSections) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        Map<String, Integer> facultyCredits = new HashMap<>();
        if (registeredSections != null && !registeredSections.isEmpty()) {
            for (ClassSection sec : registeredSections) {
                String faculty = sec.getCourse().getFaculty();
                int credits = sec.getCourse().getCredits();
                facultyCredits.put(faculty, facultyCredits.getOrDefault(faculty, 0) + credits);
            }
        }

        if (facultyCredits.isEmpty()) {
            dataset.setValue("Chưa đăng ký môn nào", 1);
        } else {
            for (Map.Entry<String, Integer> entry : facultyCredits.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        }

        String studentName = (student != null) ? student.getFullName() : "";
        JFreeChart chart = ChartFactory.createPieChart(
                "PHÂN BỐ TÍN CHỈ THEO KHỐI KIẾN THỨC (" + studentName + ")",
                dataset,
                true,
                true,
                false
        );

        @SuppressWarnings("unchecked")
        PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
        plot.setBackgroundPaint(new Color(248, 250, 252));
        plot.setOutlineVisible(false);

        // Hiển thị rõ ràng tên khoa, số tín chỉ và phần trăm chính xác (Ví dụ: CNTT: 9 TC (60.0%))
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} TC ({2})"));
        plot.setLabelFont(new Font("Segoe UI", Font.BOLD, 12));

        // Tooltip khi rê chuột không còn bị dấu phẩy dính số (Ví dụ: CNTT: 9 TC - Chiếm 60%)
        plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}: {1} TC - Chiếm {2} tổng số tín chỉ"));

        return chart;
    }
}
