package vn.edu.ptit.oop.nhom02.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private int credits;
    private String faculty;
    private final List<String> prerequisiteCodes;

    public Course(String code, String name, int credits, String faculty) {
        setCode(code);
        setName(name);
        setCredits(credits);
        setFaculty(faculty);
        this.prerequisiteCodes = new ArrayList<>();
    }

    public Course(String code, String name, int credits, String faculty, List<String> prerequisites) {
        this(code, name, credits, faculty);
        if (prerequisites != null) {
            for (String p : prerequisites) {
                addPrerequisite(p);
            }
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã môn học không được để trống!");
        }
        this.code = code.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên môn học không được để trống!");
        }
        this.name = name.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Số tín chỉ phải lớn hơn 0!");
        }
        this.credits = credits;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        if (faculty == null || faculty.trim().isEmpty()) {
            this.faculty = "Cơ bản";
        } else {
            this.faculty = faculty.trim();
        }
    }

    public void addPrerequisite(String prereqCode) {
        if (prereqCode != null && !prereqCode.trim().isEmpty()) {
            String clean = prereqCode.trim().toUpperCase();
            if (!prerequisiteCodes.contains(clean)) {
                prerequisiteCodes.add(clean);
            }
        }
    }

    public List<String> getPrerequisiteCodes() {
        return Collections.unmodifiableList(prerequisiteCodes);
    }

    public boolean hasPrerequisites() {
        return !prerequisiteCodes.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code + " - " + name + " (" + credits + " TC)";
    }
}
