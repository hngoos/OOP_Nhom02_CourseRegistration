package vn.edu.ptit.oop.nhom02.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Person {
    private static final long serialVersionUID = 1L;

    private String major;
    private String cohort;
    private int maxCredits;
    private final List<String> completedCourseCodes;

    public Student(String id, String fullName, String email, String major, String cohort, int maxCredits) {
        super(id, fullName, email);
        setMajor(major);
        setCohort(cohort);
        setMaxCredits(maxCredits);
        this.completedCourseCodes = new ArrayList<>();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("Ngành học không được để trống!");
        }
        this.major = major.trim();
    }

    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        if (cohort == null || cohort.trim().isEmpty()) {
            throw new IllegalArgumentException("Khóa học không được để trống!");
        }
        this.cohort = cohort.trim();
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(int maxCredits) {
        if (maxCredits <= 0) {
            throw new IllegalArgumentException("Số tín chỉ tối đa phải lớn hơn 0!");
        }
        this.maxCredits = maxCredits;
    }

    public void addCompletedCourse(String courseCode) {
        if (courseCode != null && !completedCourseCodes.contains(courseCode.trim().toUpperCase())) {
            completedCourseCodes.add(courseCode.trim().toUpperCase());
        }
    }

    public boolean hasCompletedCourse(String courseCode) {
        if (courseCode == null) return false;
        return completedCourseCodes.contains(courseCode.trim().toUpperCase());
    }

    public List<String> getCompletedCourseCodes() {
        return Collections.unmodifiableList(completedCourseCodes);
    }

    @Override
    public String getRoleDescription() {
        return "Sinh viên khóa " + cohort + " - Ngành " + major;
    }
}
