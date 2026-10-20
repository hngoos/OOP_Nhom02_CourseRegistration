package vn.edu.ptit.oop.nhom02.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import vn.edu.ptit.oop.nhom02.exception.CreditLimitException;
import vn.edu.ptit.oop.nhom02.exception.PrerequisiteException;
import vn.edu.ptit.oop.nhom02.exception.RegistrationException;
import vn.edu.ptit.oop.nhom02.exception.ScheduleConflictException;
import vn.edu.ptit.oop.nhom02.exception.SectionFullException;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Course;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.model.TimeSlot;

public class RegistrationService {

    public RegistrationService() {
    }

    public int calculateEnrolledCredits(Student student, List<ClassSection> registeredSections) {
        if (student == null || registeredSections == null) return 0;
        int total = 0;
        for (ClassSection sec : registeredSections) {
            total += sec.getCourse().getCredits();
        }
        return total;
    }

    public void register(Student student, ClassSection targetSection, List<ClassSection> registeredSections)
            throws RegistrationException {
        if (student == null) {
            throw new IllegalArgumentException("Thông tin sinh viên không được null!");
        }
        if (targetSection == null) {
            throw new IllegalArgumentException("Lớp học phần không được null!");
        }

        if (registeredSections.contains(targetSection)) {
            throw new RegistrationException("Sinh viên đã đăng ký lớp học phần '" + targetSection.getSectionId() + "' này rồi!");
        }

        if (targetSection.isFull()) {
            throw new SectionFullException(targetSection.getSectionId(), targetSection.getCapacity());
        }

        for (ClassSection sec : registeredSections) {
            if (sec.getCourse().getCode().equalsIgnoreCase(targetSection.getCourse().getCode())) {
                throw new RegistrationException("Sinh viên đã đăng ký môn '" + targetSection.getCourse().getName() + "' ở lớp khác (" + sec.getSectionId() + ")!");
            }
        }

        for (ClassSection registeredSec : registeredSections) {
            for (TimeSlot mySlot : targetSection.getTimeSlots()) {
                for (TimeSlot otherSlot : registeredSec.getTimeSlots()) {
                    if (mySlot.overlaps(otherSlot)) {
                        String conflictDetail = String.format("%s, Tiết %d-%d trùng với %s, Tiết %d-%d",
                                mySlot.getDayOfWeekString(), mySlot.getStartPeriod(), mySlot.getEndPeriod(),
                                otherSlot.getDayOfWeekString(), otherSlot.getStartPeriod(), otherSlot.getEndPeriod());
                        throw new ScheduleConflictException(targetSection.getSectionId(), registeredSec.getSectionId(), conflictDetail);
                    }
                }
            }
        }

        int currentCredits = calculateEnrolledCredits(student, registeredSections);
        int addedCredits = targetSection.getCourse().getCredits();
        if (currentCredits + addedCredits > student.getMaxCredits()) {
            throw new CreditLimitException(currentCredits, addedCredits, student.getMaxCredits());
        }

        Course course = targetSection.getCourse();
        if (course.hasPrerequisites()) {
            List<String> missingPrereqs = new ArrayList<>();
            for (String reqCode : course.getPrerequisiteCodes()) {
                if (!student.hasCompletedCourse(reqCode)) {
                    missingPrereqs.add(reqCode);
                }
            }
            if (!missingPrereqs.isEmpty()) {
                throw new PrerequisiteException(course.getCode(), missingPrereqs);
            }
        }

        boolean enrolled = targetSection.enroll(student);
        if (!enrolled) {
            throw new RegistrationException("Không thể ghi danh vào lớp học phần!");
        }
        registeredSections.add(targetSection);
    }

    public void unregister(Student student, ClassSection targetSection, List<ClassSection> registeredSections)
            throws RegistrationException {
        if (student == null || targetSection == null || registeredSections == null) {
            throw new IllegalArgumentException("Thông tin không hợp lệ!");
        }

        if (!registeredSections.contains(targetSection)) {
            throw new RegistrationException("Sinh viên chưa đăng ký lớp học phần này nên không thể hủy!");
        }

        targetSection.unenroll(student);
        registeredSections.remove(targetSection);
    }
}
