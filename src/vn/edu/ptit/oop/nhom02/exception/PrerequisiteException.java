package vn.edu.ptit.oop.nhom02.exception;

import java.util.Collections;
import java.util.List;

public class PrerequisiteException extends RegistrationException {
    private static final long serialVersionUID = 1L;

    private final String courseCode;
    private final List<String> missingPrereqCodes;

    public PrerequisiteException(String courseCode, List<String> missingPrereqCodes) {
        super(String.format("Chưa thỏa mãn điều kiện tiên quyết cho môn '%s'! Cần hoàn thành các môn: %s.",
              courseCode, String.join(", ", missingPrereqCodes)));
        this.courseCode = courseCode;
        this.missingPrereqCodes = missingPrereqCodes;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getMissingPrereqCodes() {
        return Collections.unmodifiableList(missingPrereqCodes);
    }
}
