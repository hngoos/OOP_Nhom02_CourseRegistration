package vn.edu.ptit.oop.nhom02.model;

public class Lecturer extends Person {
    private static final long serialVersionUID = 1L;

    private String degree;
    private String department;

    public Lecturer(String id, String fullName, String email, String degree, String department) {
        super(id, fullName, email);
        setDegree(degree);
        setDepartment(department);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        if (degree == null || degree.trim().isEmpty()) {
            throw new IllegalArgumentException("Học vị không được để trống!");
        }
        this.degree = degree.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bộ môn/Khoa không được để trống!");
        }
        this.department = department.trim();
    }

    @Override
    public String getRoleDescription() {
        return "Giảng viên " + degree + " - Khoa " + department;
    }
}
