package vn.edu.ptit.oop.nhom02.exception;

public class SectionFullException extends RegistrationException {
    private static final long serialVersionUID = 1L;

    private final String sectionId;
    private final int capacity;

    public SectionFullException(String sectionId, int capacity) {
        super(String.format("Lớp học phần '%s' đã đầy sĩ số tối đa (%d/%d sinh viên)!", sectionId, capacity, capacity));
        this.sectionId = sectionId;
        this.capacity = capacity;
    }

    public String getSectionId() {
        return sectionId;
    }

    public int getCapacity() {
        return capacity;
    }
}
