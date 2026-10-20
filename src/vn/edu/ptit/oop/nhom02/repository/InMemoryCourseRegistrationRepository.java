package vn.edu.ptit.oop.nhom02.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Course;
import vn.edu.ptit.oop.nhom02.model.Lecturer;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.model.TimeSlot;

public class InMemoryCourseRegistrationRepository implements CourseRegistrationRepository {

    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, ClassSection> sections = new HashMap<>();
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Lecturer> lecturers = new HashMap<>();

    private final Map<String, List<ClassSection>> studentRegistrations = new HashMap<>();

    public InMemoryCourseRegistrationRepository() {
        initSampleData();
    }

    private void initSampleData() {

        Lecturer gv1 = new Lecturer("GV001", "TS. Lê Văn Tuấn", "tuanlv@ptithcm.edu.vn", "Tiến sĩ", "Công nghệ Thông tin");
        Lecturer gv2 = new Lecturer("GV002", "ThS. Phạm Thị Mai", "maipt@ptithcm.edu.vn", "Thạc sĩ", "Khoa học Máy tính");
        Lecturer gv3 = new Lecturer("GV003", "PGS.TS. Trần Quốc Bảo", "baotq@ptithcm.edu.vn", "Phó Giáo sư", "Khoa học Cơ bản");

        lecturers.put(gv1.getId(), gv1);
        lecturers.put(gv2.getId(), gv2);
        lecturers.put(gv3.getId(), gv3);

        Course c1 = new Course("INT1154", "Tin học cơ sở 2", 3, "CNTT");

        Course c2 = new Course("INT1332", "Lập trình hướng đối tượng", 3, "CNTT");
        c2.addPrerequisite("INT1154");

        Course c3 = new Course("INT1340", "Cấu trúc dữ liệu & Giải thuật", 3, "CNTT");
        c3.addPrerequisite("INT1154");

        Course c4 = new Course("INT1339", "Cơ sở dữ liệu", 3, "CNTT");
        Course c5 = new Course("INT1319", "Kiến trúc máy tính", 3, "CNTT");
        Course c6 = new Course("BAS1203", "Giải tích 1", 3, "Khoa học Cơ bản");
        Course c7 = new Course("BAS1224", "Vật lý đại cương 1", 3, "Khoa học Cơ bản");
        Course c8 = new Course("SKD1105", "Kỹ năng thuyết trình", 2, "Kỹ năng");

        courses.put(c1.getCode(), c1);
        courses.put(c2.getCode(), c2);
        courses.put(c3.getCode(), c3);
        courses.put(c4.getCode(), c4);
        courses.put(c5.getCode(), c5);
        courses.put(c6.getCode(), c6);
        courses.put(c7.getCode(), c7);
        courses.put(c8.getCode(), c8);

        Student sv1 = new Student("N24DCAT001", "Nguyễn Văn A", "n24dcat001@student.ptithcm.edu.vn", "An Toàn Thông tin", "D24", 24);
        sv1.addCompletedCourse("INT1154");

        Student sv2 = new Student("N24DCAT010", "Trần Minh B", "n24dcat010@student.ptithcm.edu.vn", "An Toàn Thông tin", "D24", 20);

        Student sv3 = new Student("N24DCAT016", "Lê Thị C", "n24dcat016@student.ptithcm.edu.vn", "An toàn Thông tin", "D24", 22);
        sv3.addCompletedCourse("INT1154");

        students.put(sv1.getId(), sv1);
        students.put(sv2.getId(), sv2);
        students.put(sv3.getId(), sv3);

        studentRegistrations.put(sv1.getId(), new ArrayList<>());
        studentRegistrations.put(sv2.getId(), new ArrayList<>());
        studentRegistrations.put(sv3.getId(), new ArrayList<>());

        List<TimeSlot> slots1 = List.of(new TimeSlot(2, 1, 4, "201-A2"));
        ClassSection sec1 = new ClassSection("HP01", "D24CQAT01-N", c3, gv1, 60, slots1);

        List<TimeSlot> slots2 = List.of(new TimeSlot(3, 1, 4, "302-A2"));
        ClassSection sec2 = new ClassSection("HP02", "D24CQAT01-N", c4, gv2, 60, slots2);

        List<TimeSlot> slots3 = List.of(new TimeSlot(4, 1, 4, "105-A1"));
        ClassSection sec3 = new ClassSection("HP03", "D24CQAT01-N", c6, gv3, 70, slots3);

        List<TimeSlot> slots4 = List.of(new TimeSlot(5, 1, 4, "Lab3-CNTT"));
        ClassSection sec4 = new ClassSection("HP04", "D24CQAT01-N", c2, gv1, 50, slots4);

        List<TimeSlot> slots5 = List.of(new TimeSlot(3, 1, 4, "204-A2"));
        ClassSection sec5 = new ClassSection("HP05", "D24CQAT01-N", c5, gv2, 60, slots5);

        List<TimeSlot> slots6 = List.of(new TimeSlot(6, 7, 10, "202-A1"));
        ClassSection sec6 = new ClassSection("HP06", "D24CQAT01-N", c7, gv3, 60, slots6);

        List<TimeSlot> slots7 = List.of(new TimeSlot(7, 1, 4, "Hội trường 2"));
        ClassSection sec7 = new ClassSection("HP07", "D24CQAT01-N", c8, gv2, 80, slots7);

        List<TimeSlot> slots8 = List.of(new TimeSlot(2, 7, 10, "Lab1-CNTT"));
        ClassSection sec8 = new ClassSection("HP08", "D24CQAT01-N", c1, gv1, 45, slots8);

        List<TimeSlot> slots9 = List.of(new TimeSlot(6, 1, 4, "102-A2"));
        ClassSection sec9 = new ClassSection("HP09", "D24CQAT01-N", c4, gv2, 1, slots9);
        sec9.enroll(sv3);

        sections.put(sec1.getSectionId(), sec1);
        sections.put(sec2.getSectionId(), sec2);
        sections.put(sec3.getSectionId(), sec3);
        sections.put(sec4.getSectionId(), sec4);
        sections.put(sec5.getSectionId(), sec5);
        sections.put(sec6.getSectionId(), sec6);
        sections.put(sec7.getSectionId(), sec7);
        sections.put(sec8.getSectionId(), sec8);
        sections.put(sec9.getSectionId(), sec9);
    }

    @Override
    public List<Course> findAllCourses() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public Course findCourseByCode(String code) {
        if (code == null) return null;
        return courses.get(code.trim().toUpperCase());
    }

    @Override
    public List<ClassSection> findAllSections() {
        return new ArrayList<>(sections.values());
    }

    @Override
    public ClassSection findSectionById(String sectionId) {
        if (sectionId == null) return null;
        return sections.get(sectionId.trim().toUpperCase());
    }

    @Override
    public List<Student> findAllStudents() {
        return new ArrayList<>(students.values());
    }

    @Override
    public Student findStudentById(String studentId) {
        if (studentId == null) return null;
        return students.get(studentId.trim().toUpperCase());
    }

    @Override
    public List<Lecturer> findAllLecturers() {
        return new ArrayList<>(lecturers.values());
    }

    @Override
    public Lecturer findLecturerById(String lecturerId) {
        if (lecturerId == null) return null;
        return lecturers.get(lecturerId.trim().toUpperCase());
    }

    @Override
    public List<ClassSection> getRegisteredSectionsForStudent(Student student) {
        if (student == null) return Collections.emptyList();
        List<ClassSection> list = studentRegistrations.get(student.getId());
        if (list == null) {
            list = new ArrayList<>();
            studentRegistrations.put(student.getId(), list);
        }
        return list;
    }

    @Override
    public List<ClassSection> findSectionsByFacultyAndKeyword(String faculty, String keyword) {
        List<ClassSection> result = new ArrayList<>();
        String kw = (keyword == null) ? "" : keyword.trim().toLowerCase();
        boolean checkFaculty = (faculty != null && !faculty.equalsIgnoreCase("Tất cả") && !faculty.trim().isEmpty());

        for (ClassSection sec : sections.values()) {
            boolean matchFaculty = true;
            if (checkFaculty) {
                matchFaculty = sec.getCourse().getFaculty().equalsIgnoreCase(faculty.trim());
            }

            boolean matchKeyword = true;
            if (!kw.isEmpty()) {
                String fullText = (sec.getSectionId() + " " + sec.getCourse().getCode() + " " +
                                  sec.getCourse().getName() + " " + sec.getLecturer().getFullName()).toLowerCase();
                matchKeyword = fullText.contains(kw);
            }

            if (matchFaculty && matchKeyword) {
                result.add(sec);
            }
        }
        return result;
    }

    @Override
    public List<ClassSection> findSectionsTaughtBy(Lecturer lecturer) {
        List<ClassSection> result = new ArrayList<>();
        if (lecturer == null) return result;
        for (ClassSection sec : sections.values()) {
            if (sec.getLecturer().equals(lecturer)) {
                result.add(sec);
            }
        }
        return result;
    }
}
