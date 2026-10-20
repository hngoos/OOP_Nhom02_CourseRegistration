package vn.edu.ptit.oop.nhom02.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Lecturer;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.ui.MainWindow;
import vn.edu.ptit.oop.nhom02.ui.SectionTableModel;

public class LecturerViewStrategy implements RoleViewStrategy {

    private LecturerPanel lecturerPanel;

    @Override
    public void setupViews(MainWindow mainWindow) {
        JTabbedPane tabbedPane = mainWindow.getMainTabbedPane();
        tabbedPane.removeAll();

        lecturerPanel = new LecturerPanel(mainWindow);
        tabbedPane.addTab("Quản Lý Lớp Giảng Dạy & Sinh Viên", lecturerPanel);

        TimetablePanel teachingSchedule = new TimetablePanel(mainWindow);
        tabbedPane.addTab("Lịch Giảng Dạy Tuần", teachingSchedule);
    }

    @Override
    public void refreshViews() {
        if (lecturerPanel != null) {
            lecturerPanel.refreshData();
        }
    }

    public static class LecturerPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private final MainWindow mainWindow;
        private SectionTableModel taughtSectionsModel;
        private JTable taughtSectionsTable;

        private DefaultTableModel studentListModel;
        private JTable studentListTable;
        private JLabel lblStudentsHeader;

        public LecturerPanel(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
            setLayout(new BorderLayout(8, 8));
            setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            initUI();
        }

        private void initUI() {

            taughtSectionsModel = new SectionTableModel();
            taughtSectionsTable = new JTable(taughtSectionsModel);
            taughtSectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            taughtSectionsTable.setRowHeight(24);
            taughtSectionsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

            JPanel topPanel = new JPanel(new BorderLayout(5, 5));
            topPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Lớp Học Phần do Giảng viên phụ trách"));
            topPanel.add(new JScrollPane(taughtSectionsTable), BorderLayout.CENTER);

            String[] studentCols = {"Mã Sinh Viên", "Họ và Tên", "Khóa", "Ngành Học", "Email Liên Hệ"};
            studentListModel = new DefaultTableModel(studentCols, 0) {
                @Override public boolean isCellEditable(int row, int col) { return false; }
            };
            studentListTable = new JTable(studentListModel);
            studentListTable.setRowHeight(24);
            studentListTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

            JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
            lblStudentsHeader = new JLabel(" Danh sách sinh viên đã đăng ký vào lớp: (Chọn lớp ở bảng trên để xem)");
            lblStudentsHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblStudentsHeader.setForeground(new Color(0, 51, 102));
            bottomPanel.add(lblStudentsHeader, BorderLayout.NORTH);
            bottomPanel.add(new JScrollPane(studentListTable), BorderLayout.CENTER);

            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
            splitPane.setResizeWeight(0.5);
            add(splitPane, BorderLayout.CENTER);

            taughtSectionsTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    loadStudentsForSelectedSection();
                }
            });

            refreshData();
        }

        private void loadStudentsForSelectedSection() {
            int selectedRow = taughtSectionsTable.getSelectedRow();
            studentListModel.setRowCount(0);
            if (selectedRow < 0) {
                lblStudentsHeader.setText(" Danh sách sinh viên đã đăng ký vào lớp: (Chọn lớp ở bảng trên để xem)");
                return;
            }

            ClassSection sec = taughtSectionsModel.getSectionAt(selectedRow);
            if (sec != null) {
                lblStudentsHeader.setText(String.format(" Danh sách sinh viên lớp: %s - %s (%d / %d sinh viên)",
                        sec.getSectionId(), sec.getCourse().getName(), sec.getEnrolledCount(), sec.getCapacity()));

                for (Student st : sec.getEnrolledStudents()) {
                    studentListModel.addRow(new Object[]{
                        st.getId(), st.getFullName(), st.getCohort(), st.getMajor(), st.getEmail()
                    });
                }
            }
        }

        public void refreshData() {
            if (mainWindow.getCurrentUser() instanceof Lecturer) {
                Lecturer gv = (Lecturer) mainWindow.getCurrentUser();
                List<ClassSection> sections = mainWindow.getRepository().findSectionsTaughtBy(gv);
                taughtSectionsModel.setSections(sections);
                if (!sections.isEmpty()) {
                    taughtSectionsTable.setRowSelectionInterval(0, 0);
                    loadStudentsForSelectedSection();
                } else {
                    studentListModel.setRowCount(0);
                }
            }
        }
    }
}
