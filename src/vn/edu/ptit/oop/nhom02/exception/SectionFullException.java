package vn.edu.ptit.oop.nhom02.exception;

public class SectionFullException extends RegistrationException {
    private static final long serialVersionUID = 1L;

    private final String courseCode;
    private final int capacity;

    public SectionFullException(String courseCode, int capacity) {
        super(String.format("Môn học '%s' đã đầy sĩ số tối đa (%d/%d sinh viên)!", courseCode, capacity, capacity));
        this.courseCode = courseCode;
        this.capacity = capacity;
    }

    public String getSectionId() {
        return courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getCapacity() {
        return capacity;
    }
}
