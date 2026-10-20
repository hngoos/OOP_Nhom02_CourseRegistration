package vn.edu.ptit.oop.nhom02.exception;

public class ScheduleConflictException extends RegistrationException {
    private static final long serialVersionUID = 1L;

    private final String attemptedSectionId;
    private final String conflictingSectionId;
    private final String conflictDetails;

    public ScheduleConflictException(String attemptedSectionId, String conflictingSectionId, String conflictDetails) {
        super(String.format("Trùng lịch học! Lớp '%s' bị trùng thời gian với lớp '%s' đã đăng ký (%s).",
              attemptedSectionId, conflictingSectionId, conflictDetails));
        this.attemptedSectionId = attemptedSectionId;
        this.conflictingSectionId = conflictingSectionId;
        this.conflictDetails = conflictDetails;
    }

    public String getAttemptedSectionId() {
        return attemptedSectionId;
    }

    public String getConflictingSectionId() {
        return conflictingSectionId;
    }

    public String getConflictDetails() {
        return conflictDetails;
    }
}
