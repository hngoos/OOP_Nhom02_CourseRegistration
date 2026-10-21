package vn.edu.ptit.oop.nhom02.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClassSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sectionId;
    private String className;
    private Course course;
    private Lecturer lecturer;
    private int capacity;
    private final List<TimeSlot> timeSlots;
    private final List<Student> enrolledStudents;

    public ClassSection(String sectionId, String className, Course course, Lecturer lecturer, int capacity, List<TimeSlot> slots) {
        setSectionId(sectionId);
        setClassName(className);
        setCourse(course);
        setLecturer(lecturer);
        setCapacity(capacity);
        this.timeSlots = new ArrayList<>();
        if (slots != null) {
            this.timeSlots.addAll(slots);
        }
        this.enrolledStudents = new ArrayList<>();
    }

    public ClassSection(String sectionId, Course course, Lecturer lecturer, int capacity, List<TimeSlot> slots) {
        this(sectionId, "D24CQAT01-N", course, lecturer, capacity, slots);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = (className == null || className.trim().isEmpty()) ? "D24CQAT01-N" : className.trim();
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        if (sectionId == null || sectionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp học phần không được để trống!");
        }
        this.sectionId = sectionId.trim().toUpperCase();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Môn học không được null!");
        }
        this.course = course;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            throw new IllegalArgumentException("Giảng viên không được null!");
        }
        this.lecturer = lecturer;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Sĩ số tối đa phải lớn hơn 0!");
        }
        this.capacity = capacity;
    }

    public void addTimeSlot(TimeSlot slot) {
        if (slot != null && !timeSlots.contains(slot)) {
            timeSlots.add(slot);
        }
    }

    public List<TimeSlot> getTimeSlots() {
        return Collections.unmodifiableList(timeSlots);
    }

    public List<Student> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }

    public int getEnrolledCount() {
        return enrolledStudents.size();
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public boolean hasAvailableSeat() {
        return enrolledStudents.size() < capacity;
    }

    public boolean isStudentEnrolled(Student student) {
        if (student == null) return false;
        return enrolledStudents.contains(student);
    }

    public boolean enroll(Student student) {
        if (student == null || isFull() || isStudentEnrolled(student)) {
            return false;
        }
        return enrolledStudents.add(student);
    }

    public boolean unenroll(Student student) {
        if (student == null) return false;
        return enrolledStudents.remove(student);
    }

    public boolean conflictsWith(ClassSection other) {
        if (other == null || this.equals(other)) return false;
        for (TimeSlot mySlot : this.timeSlots) {
            for (TimeSlot otherSlot : other.getTimeSlots()) {
                if (mySlot.overlaps(otherSlot)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getTimeSlotsSummary() {
        if (timeSlots.isEmpty()) return "Chưa có lịch";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < timeSlots.size(); i++) {
            if (i > 0) sb.append("; ");
            sb.append(timeSlots.get(i).toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassSection)) return false;
        ClassSection that = (ClassSection) o;
        return Objects.equals(sectionId, that.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId);
    }

    @Override
    public String toString() {
        return course.getCode() + " - " + course.getName() + " (" + getEnrolledCount() + "/" + capacity + ")";
    }
}
