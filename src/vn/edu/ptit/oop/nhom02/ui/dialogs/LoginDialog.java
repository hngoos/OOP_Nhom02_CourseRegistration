package vn.edu.ptit.oop.nhom02.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import vn.edu.ptit.oop.nhom02.model.Lecturer;
import vn.edu.ptit.oop.nhom02.model.Person;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.repository.CourseRegistrationRepository;

public class LoginDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final CourseRegistrationRepository repository;
    private Person selectedPerson = null;
    private boolean confirmed = false;

    private JRadioButton rbStudent;
    private JRadioButton rbLecturer;
    private JComboBox<Person> cbUsers;
    private JLabel lblDescription;

    public LoginDialog(Frame parent, CourseRegistrationRepository repository) {
        super(parent, "Đăng nhập Hệ thống - PTIT Course Registration", true);
        this.repository = repository;
        setSize(650, 320);
        setLocationRelativeTo(parent);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 86, 179));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        JLabel title = new JLabel("HỆ THỐNG ĐĂNG KÝ HỌC PHẦN - PTIT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.0;
        JLabel lblRole = new JLabel("Vai trò đăng nhập:");
        lblRole.setPreferredSize(new Dimension(140, 25));
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblRole, gbc);

        rbStudent = new JRadioButton("Sinh viên", true);
        rbStudent.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rbLecturer = new JRadioButton("Giảng viên", false);
        rbLecturer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbStudent);
        bg.add(rbLecturer);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        rolePanel.add(rbStudent);
        rolePanel.add(rbLecturer);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(rolePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel lblAccount = new JLabel("Chọn tài khoản:");
        lblAccount.setPreferredSize(new Dimension(140, 25));
        lblAccount.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblAccount, gbc);

        cbUsers = new JComboBox<>();
        cbUsers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbUsers.setPreferredSize(new Dimension(420, 30));
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbUsers, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel lblDetail = new JLabel("Thông tin chi tiết:");
        lblDetail.setPreferredSize(new Dimension(140, 25));
        lblDetail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(lblDetail, gbc);

        lblDescription = new JLabel("---");
        lblDescription.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDescription.setForeground(new Color(0, 102, 204));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        formPanel.add(lblDescription, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setPreferredSize(new Dimension(110, 32));

        JButton btnExit = new JButton("Thoát");
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnExit.setForeground(Color.BLACK);
        btnExit.setPreferredSize(new Dimension(80, 32));

        btnPanel.add(btnLogin);
        btnPanel.add(btnExit);
        add(btnPanel, BorderLayout.SOUTH);

        rbStudent.addActionListener(e -> updateUsersList());
        rbLecturer.addActionListener(e -> updateUsersList());

        cbUsers.addActionListener(e -> {
            Person p = (Person) cbUsers.getSelectedItem();
            if (p != null) {
                lblDescription.setText(p.getRoleDescription());
            }
        });

        btnLogin.addActionListener(e -> {
            selectedPerson = (Person) cbUsers.getSelectedItem();
            if (selectedPerson != null) {
                confirmed = true;
                dispose();
            }
        });

        btnExit.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        updateUsersList();
    }

    private void updateUsersList() {
        DefaultComboBoxModel<Person> model = new DefaultComboBoxModel<>();
        if (rbStudent.isSelected()) {
            List<Student> students = repository.findAllStudents();
            for (Student s : students) {
                model.addElement(s);
            }
        } else {
            List<Lecturer> lecturers = repository.findAllLecturers();
            for (Lecturer l : lecturers) {
                model.addElement(l);
            }
        }
        cbUsers.setModel(model);
        if (model.getSize() > 0) {
            cbUsers.setSelectedIndex(0);
            Person first = (Person) cbUsers.getSelectedItem();
            if (first != null) {
                lblDescription.setText(first.getRoleDescription());
            }
        }
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
