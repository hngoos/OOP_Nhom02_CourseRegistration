package vn.edu.ptit.oop.nhom02.exception;

public class ScheduleConflictException extends RegistrationException {
    private static final long serialVersionUID = 1L;

    private final String attemptedCourseCode;
    private final String conflictingCourseCode;
    private final String conflictDetails;

    public ScheduleConflictException(String attemptedCourseCode, String conflictingCourseCode, String conflictDetails) {
        super(String.format("Trùng lịch học! Môn '%s' bị trùng thời gian với môn '%s' đã đăng ký (%s).",
              attemptCourse(attemptedCourseCode), attemptCourse(conflictingCourseCode), conflictDetails));
        this.attemptedCourseCode = attemptedCourseCode;
        this.conflictingCourseCode = conflictingCourseCode;
        this.conflictDetails = conflictDetails;
    }

    private static String attemptCourse(String code) {
        return code != null ? code : "";
    }

    public String getAttemptedSectionId() {
        return attemptedCourseCode;
    }

    public String getConflictingSectionId() {
        return conflictingCourseCode;
    }

    public String getConflictDetails() {
        return conflictDetails;
    }
}
