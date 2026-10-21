package vn.edu.ptit.oop.nhom02.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import vn.edu.ptit.oop.nhom02.common.BaseWindow;
import vn.edu.ptit.oop.nhom02.exception.RegistrationException;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.repository.CourseRegistrationRepository;
import vn.edu.ptit.oop.nhom02.service.RegistrationService;
import vn.edu.ptit.oop.nhom02.ui.MainWindow;
import vn.edu.ptit.oop.nhom02.ui.SectionTableModel;

public class RegistrationPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final MainWindow mainWindow;
    private final CourseRegistrationRepository repository;
    private final RegistrationService registrationService;

    private SectionTableModel availableTableModel;
    private JTable availableTable;
    private TableRowSorter<SectionTableModel> sorter;

    private SectionTableModel registeredTableModel;
    private JTable registeredTable;

    private JComboBox<String> cbFacultyFilter;
    private JTextField txtSearch;
    private JLabel lblRegisteredSummary;

    public RegistrationPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.repository = mainWindow.getRepository();
        this.registrationService = mainWindow.getRegistrationService();
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        initUI();
    }

    private void initUI() {

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm lớp học phần"));

        filterPanel.add(new JLabel("Khoa/Bộ môn:"));
        cbFacultyFilter = new JComboBox<>(new String[]{"Tất cả", "CNTT", "Khoa học Cơ bản", "Kỹ năng"});
        cbFacultyFilter.setPreferredSize(new Dimension(140, 26));
        filterPanel.add(cbFacultyFilter);

        filterPanel.add(new JLabel("Từ khóa tìm kiếm:"));
        txtSearch = new JTextField(20);
        txtSearch.setToolTipText("Tìm theo mã lớp, mã môn, tên môn hoặc giảng viên...");
        filterPanel.add(txtSearch);

        JButton btnClear = new JButton("Xóa bộ lọc");
        filterPanel.add(btnClear);

        add(filterPanel, BorderLayout.NORTH);

        availableTableModel = new SectionTableModel(repository.findAllSections());
        availableTable = new JTable(availableTableModel);
        availableTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableTable.setRowHeight(24);
        availableTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        sorter = new TableRowSorter<>(availableTableModel);
        availableTable.setRowSorter(sorter);

        JPanel topTablePanel = new JPanel(new BorderLayout(5, 5));
        topTablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách Lớp Học Phần đang mở đăng ký"));
        topTablePanel.add(new JScrollPane(availableTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton btnRegister = new JButton("Đăng ký học phần", BaseWindow.loadImageIcon("/resources/icons/add.png"));
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegister.setForeground(Color.BLACK);
        btnRegister.setPreferredSize(new Dimension(170, 32));
        actionPanel.add(btnRegister);
        topTablePanel.add(actionPanel, BorderLayout.SOUTH);

        registeredTableModel = new SectionTableModel();
        registeredTable = new JTable(registeredTableModel);
        registeredTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        registeredTable.setRowHeight(24);
        registeredTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JPanel bottomTablePanel = new JPanel(new BorderLayout(5, 5));
        bottomTablePanel.setBorder(BorderFactory.createTitledBorder("Kết quả Đăng Ký Học Phần của Sinh viên"));
        bottomTablePanel.add(new JScrollPane(registeredTable), BorderLayout.CENTER);

        JPanel bottomActionPanel = new JPanel(new BorderLayout());
        lblRegisteredSummary = new JLabel(" Tổng số tín chỉ đã đăng ký: 0 TC (Tối thiểu 14 TC, Tối đa 25 TC)");
        lblRegisteredSummary.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRegisteredSummary.setForeground(new Color(0, 102, 204));
        bottomActionPanel.add(lblRegisteredSummary, BorderLayout.WEST);

        JButton btnUnregister = new JButton("Hủy đăng ký", BaseWindow.loadImageIcon("/resources/icons/delete.png"));
        btnUnregister.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUnregister.setForeground(Color.BLACK);
        btnUnregister.setPreferredSize(new Dimension(140, 32));
        bottomActionPanel.add(btnUnregister, BorderLayout.EAST);
        bottomTablePanel.add(bottomActionPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topTablePanel, bottomTablePanel);
        splitPane.setResizeWeight(0.55);
        add(splitPane, BorderLayout.CENTER);

        DocumentListener searchListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        };
        txtSearch.getDocument().addDocumentListener(searchListener);
        cbFacultyFilter.addActionListener(e -> applyFilter());

        btnClear.addActionListener(e -> {
            cbFacultyFilter.setSelectedIndex(0);
            txtSearch.setText("");
        });

        btnRegister.addActionListener(e -> performRegister());

        btnUnregister.addActionListener(e -> performUnregister());

        refreshData();
    }

    private void applyFilter() {
        String kw = txtSearch.getText().trim();
        String faculty = (String) cbFacultyFilter.getSelectedItem();

        RowFilter<SectionTableModel, Object> rf;
        try {
            RowFilter<SectionTableModel, Object> textFilter = null;
            if (!kw.isEmpty()) {
                textFilter = RowFilter.regexFilter("(?i)" + Pattern.quote(kw), 0, 1, 2, 5);
            }

            RowFilter<SectionTableModel, Object> facultyFilter = null;
            if (faculty != null && !faculty.equals("Tất cả")) {
                facultyFilter = RowFilter.regexFilter("(?i)" + Pattern.quote(faculty), 4);
            }

            if (textFilter != null && facultyFilter != null) {
                rf = RowFilter.andFilter(List.of(textFilter, facultyFilter));
            } else if (textFilter != null) {
                rf = textFilter;
            } else if (facultyFilter != null) {
                rf = facultyFilter;
            } else {
                rf = null;
            }
            sorter.setRowFilter(rf);
        } catch (Exception ex) {
            sorter.setRowFilter(null);
        }
    }

    private void performRegister() {
        if (!(mainWindow.getCurrentUser() instanceof Student)) {
            JOptionPane.showMessageDialog(this,
                "Tài khoản hiện tại là Giảng viên. Vui lòng chuyển sang tài khoản Sinh viên để đăng ký!",
                "Cảnh báo vai trò", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student student = (Student) mainWindow.getCurrentUser();
        int selectedViewRow = availableTable.getSelectedRow();
        if (selectedViewRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 lớp học phần trên bảng để đăng ký!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int modelRow = availableTable.convertRowIndexToModel(selectedViewRow);
        ClassSection sectionToEnroll = availableTableModel.getSectionAt(modelRow);

        if (sectionToEnroll == null) return;

        try {
            List<ClassSection> registeredList = repository.getRegisteredSectionsForStudent(student);

            registrationService.register(student, sectionToEnroll, registeredList);

            int totalCredits = registrationService.calculateEnrolledCredits(student, registeredList);
            if (totalCredits > 25) {
                JOptionPane.showMessageDialog(this,
                    "Đăng ký thành công lớp '" + sectionToEnroll.getSectionId() + "' - " + sectionToEnroll.getCourse().getName() + "!\n" +
                    "⚠️ CẢNH BÁO: Tổng số tín chỉ hiện tại là " + totalCredits + " TC (Vượt quá giới hạn tối đa 25 TC theo quy chế)!",
                    "Cảnh báo học vụ", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Đăng ký thành công lớp '" + sectionToEnroll.getSectionId() + "' - " + sectionToEnroll.getCourse().getName() + "!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }

            refreshData();
            mainWindow.updateStatusBar();
        } catch (RegistrationException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi Đăng Ký Học Phần", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performUnregister() {
        if (!(mainWindow.getCurrentUser() instanceof Student)) {
            return;
        }

        Student student = (Student) mainWindow.getCurrentUser();
        int selectedRow = registeredTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 lớp học phần trong danh sách đã đăng ký để hủy!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ClassSection sectionToDrop = registeredTableModel.getSectionAt(selectedRow);
        if (sectionToDrop == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn hủy đăng ký lớp '" + sectionToDrop.getSectionId() + "'?",
            "Xác nhận hủy môn", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                List<ClassSection> registeredList = repository.getRegisteredSectionsForStudent(student);
                registrationService.unregister(student, sectionToDrop, registeredList);
                JOptionPane.showMessageDialog(this, "Đã hủy đăng ký thành công!");
                refreshData();
                mainWindow.updateStatusBar();
            } catch (RegistrationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshData() {
        availableTableModel.setSections(repository.findAllSections());

        if (mainWindow.getCurrentUser() instanceof Student) {
            Student s = (Student) mainWindow.getCurrentUser();
            List<ClassSection> mySections = repository.getRegisteredSectionsForStudent(s);
            registeredTableModel.setSections(mySections);

            int totalCredits = registrationService.calculateEnrolledCredits(s, mySections);
            String note;
            if (totalCredits < 14) {
                note = " (Cảnh báo: Chưa đạt mức tối thiểu 14 TC)";
                lblRegisteredSummary.setForeground(new Color(204, 102, 0));
            } else if (totalCredits <= 25) {
                note = " (Hợp lệ: 14 - 25 TC)";
                lblRegisteredSummary.setForeground(new Color(0, 153, 76));
            } else {
                note = " (Cảnh báo: Vượt quá 25 TC)";
                lblRegisteredSummary.setForeground(new Color(204, 0, 0));
            }
            lblRegisteredSummary.setText(String.format(
                " Tổng số tín chỉ đã đăng ký: %d TC%s | Số môn: %d",
                totalCredits, note, mySections.size()
            ));
        } else {
            registeredTableModel.setSections(List.of());
            lblRegisteredSummary.setText(" (Chế độ giảng viên - Không có dữ liệu đăng ký)");
        }
    }
}
