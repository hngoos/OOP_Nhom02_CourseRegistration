package vn.edu.ptit.oop.nhom02.repository;

import java.util.List;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Course;
import vn.edu.ptit.oop.nhom02.model.Lecturer;
import vn.edu.ptit.oop.nhom02.model.Student;

public interface CourseRegistrationRepository {

    List<Course> findAllCourses();
    Course findCourseByCode(String code);

    List<ClassSection> findAllSections();
    ClassSection findSectionById(String sectionId);

    List<Student> findAllStudents();
    Student findStudentById(String studentId);

    List<Lecturer> findAllLecturers();
    Lecturer findLecturerById(String lecturerId);

    List<ClassSection> getRegisteredSectionsForStudent(Student student);

    List<ClassSection> findSectionsByFacultyAndKeyword(String faculty, String keyword);

    List<ClassSection> findSectionsTaughtBy(Lecturer lecturer);
}
