package vn.edu.ptit.oop.nhom02.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import vn.edu.ptit.oop.nhom02.common.BaseWindow;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Lecturer;
import vn.edu.ptit.oop.nhom02.model.Person;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.repository.CourseRegistrationRepository;
import vn.edu.ptit.oop.nhom02.repository.InMemoryCourseRegistrationRepository;
import vn.edu.ptit.oop.nhom02.service.RegistrationService;
import vn.edu.ptit.oop.nhom02.ui.dialogs.AboutDialog;
import vn.edu.ptit.oop.nhom02.ui.dialogs.ChartWindow;
import vn.edu.ptit.oop.nhom02.ui.dialogs.LoginDialog;
import vn.edu.ptit.oop.nhom02.ui.panels.LecturerViewStrategy;
import vn.edu.ptit.oop.nhom02.ui.panels.RoleViewStrategy;
import vn.edu.ptit.oop.nhom02.ui.panels.StudentViewStrategy;
import vn.edu.ptit.oop.nhom02.util.ChartHelper;

public class MainWindow extends BaseWindow {
    private static final long serialVersionUID = 1L;

    private CourseRegistrationRepository repository;
    private RegistrationService registrationService;
    private Person currentUser;
    private RoleViewStrategy currentRoleStrategy;

    private JLabel lblUserInfo;
    private JTabbedPane mainTabbedPane;

    public MainWindow() {
        super("Nhóm 02 – Đăng ký Học phần (Course Registration) - PTIT", 1150, 720);
    }

    @Override
    protected void initUI() {

        ChartHelper.applyVietnameseChartTheme();

        repository = new InMemoryCourseRegistrationRepository();
        registrationService = new RegistrationService();

        LoginDialog loginDialog = new LoginDialog(this, repository);
        loginDialog.setVisible(true);

        if (!loginDialog.isConfirmed() || loginDialog.getSelectedPerson() == null) {
            System.exit(0);
            return;
        }
        currentUser = loginDialog.getSelectedPerson();

        addToolButton("Đổi tài khoản", "/resources/icons/login.png", "Đổi tài khoản đăng nhập (Sinh viên/Giảng viên)", e -> switchUser());
        addToolButton("Biểu đồ thống kê", "/resources/icons/chart.png", "Xem biểu đồ thống kê JFreeChart", e -> openChartWindow());
        addToolButton("Làm mới dữ liệu", "/resources/icons/refresh.png", "Làm mới danh sách và bảng", e -> refreshCurrentView());
        addToolButton("Thông tin nhóm", "/resources/icons/about.png", "Thông tin nhóm & đồ án", e -> showAbout());

        JPanel topUserBar = new JPanel(new BorderLayout());
        topUserBar.setBackground(new Color(240, 244, 250));
        topUserBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        lblUserInfo = new JLabel();
        lblUserInfo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUserInfo.setForeground(new Color(0, 51, 102));
        updateUserHeader();
        topUserBar.add(lblUserInfo, BorderLayout.WEST);

        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(topUserBar, BorderLayout.NORTH);
        centerContainer.add(mainTabbedPane, BorderLayout.CENTER);

        add(centerContainer, BorderLayout.CENTER);

        setupRoleStrategy();

        updateStatusBar();
    }

    private void setupRoleStrategy() {
        if (currentUser instanceof Lecturer) {
            currentRoleStrategy = new LecturerViewStrategy();
        } else {
            currentRoleStrategy = new StudentViewStrategy();
        }
        currentRoleStrategy.setupViews(this);
    }

    public void updateUserHeader() {
        if (currentUser != null && lblUserInfo != null) {
            lblUserInfo.setText("Tài khoản đang đăng nhập: " + currentUser.getFullName() +
                                " (" + currentUser.getId() + ") | " + currentUser.getRoleDescription());
        }
    }

    public void updateStatusBar() {
        if (currentUser instanceof Student) {
            Student s = (Student) currentUser;
            int credits = registrationService.calculateEnrolledCredits(s, repository.getRegisteredSectionsForStudent(s));
            String statusNote;
            if (credits < 14) {
                statusNote = "(Cảnh báo: Chưa đạt mức tối thiểu 14 tín chỉ)";
            } else if (credits <= 25) {
                statusNote = "(Hợp lệ: 14 - 25 tín chỉ)";
            } else {
                statusNote = "(Cảnh báo: Vượt quá giới hạn tối đa 25 tín chỉ)";
            }
            setStatus("Sinh viên: " + s.getFullName() + " | Đã đăng ký: " + credits + " Tín chỉ " + statusNote);
        } else if (currentUser != null) {
            setStatus("Giảng viên: " + currentUser.getFullName() + " | Khoa: " + ((Lecturer) currentUser).getDepartment());
        }
    }

    private void switchUser() {
        LoginDialog loginDialog = new LoginDialog(this, repository);
        loginDialog.setVisible(true);
        if (loginDialog.isConfirmed() && loginDialog.getSelectedPerson() != null) {
            this.currentUser = loginDialog.getSelectedPerson();
            updateUserHeader();
            setupRoleStrategy();
            updateStatusBar();
        }
    }

    private void refreshCurrentView() {
        updateUserHeader();
        if (currentRoleStrategy != null) {
            currentRoleStrategy.refreshViews();
        }
        updateStatusBar();
    }

    private void openChartWindow() {
        Student currentStudent = (currentUser instanceof Student) ? (Student) currentUser : repository.findAllStudents().get(0);
        List<ClassSection> registered = repository.getRegisteredSectionsForStudent(currentStudent);

        ChartWindow chartWindow = new ChartWindow(this, repository.findAllSections(), currentStudent, registered);
        chartWindow.setVisible(true);
    }

    @Override
    protected void showAbout() {
        new AboutDialog(this).setVisible(true);
    }

    public CourseRegistrationRepository getRepository() {
        return repository;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public JTabbedPane getMainTabbedPane() {
        return mainTabbedPane;
    }
}
