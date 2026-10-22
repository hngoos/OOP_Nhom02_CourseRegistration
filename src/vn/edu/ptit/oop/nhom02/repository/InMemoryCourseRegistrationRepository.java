package vn.edu.ptit.oop.nhom02.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import vn.edu.ptit.oop.nhom02.model.ClassSection;
import vn.edu.ptit.oop.nhom02.model.Course;
import vn.edu.ptit.oop.nhom02.model.Lecturer;
import vn.edu.ptit.oop.nhom02.model.Student;
import vn.edu.ptit.oop.nhom02.model.TimeSlot;

public class InMemoryCourseRegistrationRepository implements CourseRegistrationRepository {

    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, ClassSection> sections = new LinkedHashMap<>();
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Lecturer> lecturers = new LinkedHashMap<>();

    private final Map<String, List<ClassSection>> studentRegistrations = new LinkedHashMap<>();

    public InMemoryCourseRegistrationRepository() {
        initSampleData();
    }

    private void initSampleData() {
        Lecturer gv1 = new Lecturer("GV001", "TS. Lê Văn Tuấn", "tuanlv@ptithcm.edu.vn", "Tiến sĩ", "Công nghệ Thông tin");
        Lecturer gv2 = new Lecturer("GV002", "ThS. Phạm Thị Mai", "maipt@ptithcm.edu.vn", "Thạc sĩ", "Khoa học Máy tính");
        Lecturer gv3 = new Lecturer("GV003", "PGS.TS. Trần Quốc Bảo", "baotq@ptithcm.edu.vn", "Phó Giáo sư", "Khoa học Cơ bản");
        Lecturer gv4 = new Lecturer("GV004", "TS. Vũ Hoài Nam", "namvh@ptithcm.edu.vn", "Tiến sĩ", "An toàn Thông tin");
        Lecturer gv5 = new Lecturer("GV005", "ThS. Đỗ Thị Bích Ngọc", "ngocdtb@ptithcm.edu.vn", "Thạc sĩ", "Mạng & Hệ thống");

        lecturers.put(gv1.getId(), gv1);
        lecturers.put(gv2.getId(), gv2);
        lecturers.put(gv3.getId(), gv3);
        lecturers.put(gv4.getId(), gv4);
        lecturers.put(gv5.getId(), gv5);

        // --- KHỐI NĂM 1 (Đại cương & Nền tảng) ---
        Course c1 = new Course("INT1154", "Tin học cơ sở 2", 3, "CNTT");
        Course c6 = new Course("BAS1203", "Giải tích 1", 3, "Khoa học Cơ bản");
        Course c7 = new Course("BAS1224", "Vật lý đại cương 1", 3, "Khoa học Cơ bản");
        Course c14 = new Course("BAS1105", "Triết học Mác - Lênin", 3, "Khoa học Cơ bản");
        Course c8 = new Course("SKD1105", "Kỹ năng thuyết trình", 2, "Kỹ năng");
        Course c15 = new Course("SKD1102", "Kỹ năng làm việc nhóm", 2, "Kỹ năng");

        // --- KHỐI NĂM 2 (Cơ sở ngành) ---
        Course c2 = new Course("INT1332", "Lập trình hướng đối tượng", 3, "CNTT");
        c2.addPrerequisite("INT1154");

        Course c3 = new Course("INT1340", "Cấu trúc dữ liệu & Giải thuật", 3, "CNTT");
        c3.addPrerequisite("INT1154");

        Course c4 = new Course("INT1339", "Cơ sở dữ liệu", 3, "CNTT");
        c4.addPrerequisite("INT1154");

        Course c5 = new Course("INT1319", "Kiến trúc máy tính", 3, "CNTT");

        Course c13 = new Course("BAS1226", "Xác suất thống kê", 3, "Khoa học Cơ bản");
        c13.addPrerequisite("BAS1203");

        Course c9 = new Course("INT13145", "Mạng máy tính", 3, "An toàn Thông tin");
        c9.addPrerequisite("INT1154");

        // --- KHỐI NĂM 3 (Chuyên ngành An toàn Thông tin & Ứng dụng) ---
        Course c10 = new Course("INT1306", "Hệ điều hành", 3, "CNTT");
        c10.addPrerequisite("INT1319");

        Course c11 = new Course("INT1412", "Trí tuệ nhân tạo", 3, "CNTT");
        c11.addPrerequisite("INT1340");

        Course c12 = new Course("INT1434", "Phát triển ứng dụng Web", 3, "CNTT");
        c12.addPrerequisite("INT1332");

        Course c16 = new Course("INT1370", "Cơ sở an toàn thông tin", 3, "An toàn Thông tin");
        c16.addPrerequisite("INT13145");

        Course c17 = new Course("INT1372", "Mật mã học cơ sở", 3, "An toàn Thông tin");
        c17.addPrerequisite("BAS1226");

        Course c18 = new Course("INT1442", "An toàn mạng máy tính", 3, "An toàn Thông tin");
        c18.addPrerequisite("INT13145");

        Course c19 = new Course("INT1445", "Lập trình mạng", 3, "CNTT");
        c19.addPrerequisite("INT1332");

        Course c20 = new Course("INT1460", "Đánh giá an toàn hệ thống", 3, "An toàn Thông tin");
        c20.addPrerequisite("INT1370");

        courses.put(c1.getCode(), c1);
        courses.put(c6.getCode(), c6);
        courses.put(c7.getCode(), c7);
        courses.put(c14.getCode(), c14);
        courses.put(c8.getCode(), c8);
        courses.put(c15.getCode(), c15);

        courses.put(c2.getCode(), c2);
        courses.put(c3.getCode(), c3);
        courses.put(c4.getCode(), c4);
        courses.put(c5.getCode(), c5);
        courses.put(c13.getCode(), c13);
        courses.put(c9.getCode(), c9);

        courses.put(c10.getCode(), c10);
        courses.put(c11.getCode(), c11);
        courses.put(c12.getCode(), c12);
        courses.put(c16.getCode(), c16);
        courses.put(c17.getCode(), c17);
        courses.put(c18.getCode(), c18);
        courses.put(c19.getCode(), c19);
        courses.put(c20.getCode(), c20);

        // Sinh viên Năm 3: Khóa D24 (Đã hoàn thành các môn Năm 1 & Năm 2)
        Student sv1 = new Student("N24DCAT001", "Nguyễn Văn A", "n24dcat001@student.ptithcm.edu.vn", "An Toàn Thông tin", "D24 (Năm 3)", 35);
        sv1.addCompletedCourse("INT1154");
        sv1.addCompletedCourse("BAS1203");
        sv1.addCompletedCourse("BAS1224");
        sv1.addCompletedCourse("BAS1105");
        sv1.addCompletedCourse("INT1332");
        sv1.addCompletedCourse("INT1340");
        sv1.addCompletedCourse("INT1339");
        sv1.addCompletedCourse("INT1319");
        sv1.addCompletedCourse("BAS1226");
        sv1.addCompletedCourse("INT13145");

        // Sinh viên Năm 2: Khóa D25 (Đã hoàn thành các môn Năm 1)
        Student sv2 = new Student("N25DCAT010", "Trần Minh B", "n25dcat010@student.ptithcm.edu.vn", "An Toàn Thông tin", "D25 (Năm 2)", 35);
        sv2.addCompletedCourse("INT1154");
        sv2.addCompletedCourse("BAS1203");
        sv2.addCompletedCourse("BAS1224");
        sv2.addCompletedCourse("BAS1105");

        // Sinh viên Năm 1: Khóa D26 (Mới nhập học, chưa hoàn thành môn nào)
        Student sv3 = new Student("N26DCAT016", "Lê Thị C", "n26dcat016@student.ptithcm.edu.vn", "An Toàn Thông tin", "D26 (Năm 1)", 35);

        students.put(sv1.getId(), sv1);
        students.put(sv2.getId(), sv2);
        students.put(sv3.getId(), sv3);

        studentRegistrations.put(sv1.getId(), new ArrayList<>());
        studentRegistrations.put(sv2.getId(), new ArrayList<>());
        studentRegistrations.put(sv3.getId(), new ArrayList<>());

        // --- CÁC LỚP HỌC PHẦN DÀNH CHO NĂM 1 (Lớp: D26CQAT01-N) ---
        List<TimeSlot> slots8 = List.of(new TimeSlot(2, 7, 10, "Lab1-CNTT"));
        ClassSection sec8 = new ClassSection("HP08", "D26CQAT01-N", c1, gv1, 45, slots8);

        List<TimeSlot> slots3 = List.of(new TimeSlot(4, 1, 4, "105-A1"));
        ClassSection sec3 = new ClassSection("HP03", "D26CQAT01-N", c6, gv3, 70, slots3);

        List<TimeSlot> slots6 = List.of(new TimeSlot(6, 7, 10, "202-A1"));
        ClassSection sec6 = new ClassSection("HP06", "D26CQAT01-N", c7, gv3, 60, slots6);

        List<TimeSlot> slots7 = List.of(new TimeSlot(7, 1, 4, "Hội trường 2"));
        ClassSection sec7 = new ClassSection("HP07", "D26CQAT01-N", c8, gv2, 80, slots7);

        List<TimeSlot> slots15 = List.of(new TimeSlot(8, 1, 4, "Hội trường 1"));
        ClassSection sec15 = new ClassSection("HP15", "D26CQAT01-N", c14, gv3, 80, slots15);

        List<TimeSlot> slots16 = List.of(new TimeSlot(8, 7, 10, "203-A2"));
        ClassSection sec16 = new ClassSection("HP16", "D26CQAT01-N", c15, gv2, 60, slots16);

        // --- CÁC LỚP HỌC PHẦN DÀNH CHO NĂM 2 (Lớp: D25CQAT01-N) ---
        List<TimeSlot> slots1 = List.of(new TimeSlot(2, 1, 4, "201-A2"));
        ClassSection sec1 = new ClassSection("HP01", "D25CQAT01-N", c3, gv1, 60, slots1);

        List<TimeSlot> slots2 = List.of(new TimeSlot(3, 1, 4, "302-A2"));
        ClassSection sec2 = new ClassSection("HP02", "D25CQAT01-N", c4, gv2, 60, slots2);

        List<TimeSlot> slots5 = List.of(new TimeSlot(3, 1, 4, "204-A2"));
        ClassSection sec5 = new ClassSection("HP05", "D25CQAT01-N", c5, gv4, 60, slots5);

        List<TimeSlot> slots10 = List.of(new TimeSlot(3, 7, 10, "301-A2"));
        ClassSection sec10 = new ClassSection("HP10", "D25CQAT01-N", c9, gv5, 60, slots10);

        List<TimeSlot> slots4 = List.of(new TimeSlot(5, 1, 4, "Lab3-CNTT"));
        ClassSection sec4 = new ClassSection("HP04", "D25CQAT01-N", c2, gv1, 50, slots4);

        List<TimeSlot> slots9 = List.of(new TimeSlot(6, 1, 4, "102-A2"));
        ClassSection sec9 = new ClassSection("HP09", "D25CQAT01-N", c4, gv2, 1, slots9);
        Student svKhac = new Student("N23DCAT099", "Đặng Hải Nam", "namdh@student.ptithcm.edu.vn", "An Toàn Thông tin", "D23", 30);
        sec9.enroll(svKhac);

        List<TimeSlot> slots14 = List.of(new TimeSlot(7, 7, 10, "101-A1"));
        ClassSection sec14 = new ClassSection("HP14", "D25CQAT01-N", c13, gv3, 70, slots14);

        // --- CÁC LỚP HỌC PHẦN DÀNH CHO NĂM 3 (Lớp: D24CQAT01-N) ---
        List<TimeSlot> slots17 = List.of(new TimeSlot(2, 1, 4, "305-A2"));
        ClassSection sec17 = new ClassSection("HP17", "D24CQAT01-N", c16, gv4, 60, slots17);

        List<TimeSlot> slots18 = List.of(new TimeSlot(3, 7, 10, "201-A2"));
        ClassSection sec18 = new ClassSection("HP18", "D24CQAT01-N", c17, gv4, 60, slots18);

        List<TimeSlot> slots11 = List.of(new TimeSlot(4, 7, 10, "205-A2"));
        ClassSection sec11 = new ClassSection("HP11", "D24CQAT01-N", c10, gv4, 60, slots11);

        List<TimeSlot> slots19 = List.of(new TimeSlot(5, 1, 4, "Lab1-CNTT"));
        ClassSection sec19 = new ClassSection("HP19", "D24CQAT01-N", c18, gv5, 60, slots19);

        List<TimeSlot> slots12 = List.of(new TimeSlot(5, 7, 10, "Lab2-CNTT"));
        ClassSection sec12 = new ClassSection("HP12", "D24CQAT01-N", c11, gv1, 55, slots12);

        List<TimeSlot> slots13 = List.of(new TimeSlot(6, 1, 4, "Lab4-CNTT"));
        ClassSection sec13 = new ClassSection("HP13", "D24CQAT01-N", c12, gv5, 50, slots13);

        List<TimeSlot> slots20 = List.of(new TimeSlot(7, 1, 4, "102-A2"));
        ClassSection sec20 = new ClassSection("HP20", "D24CQAT01-N", c19, gv1, 60, slots20);

        List<TimeSlot> slots21 = List.of(new TimeSlot(8, 1, 4, "304-A2"));
        ClassSection sec21 = new ClassSection("HP21", "D24CQAT01-N", c20, gv4, 60, slots21);

        // Đưa vào kho theo thứ tự Năm 1 -> Năm 2 -> Năm 3
        sections.put(sec8.getSectionId(), sec8);
        sections.put(sec3.getSectionId(), sec3);
        sections.put(sec6.getSectionId(), sec6);
        sections.put(sec7.getSectionId(), sec7);
        sections.put(sec15.getSectionId(), sec15);
        sections.put(sec16.getSectionId(), sec16);

        sections.put(sec1.getSectionId(), sec1);
        sections.put(sec2.getSectionId(), sec2);
        sections.put(sec5.getSectionId(), sec5);
        sections.put(sec10.getSectionId(), sec10);
        sections.put(sec4.getSectionId(), sec4);
        sections.put(sec9.getSectionId(), sec9);
        sections.put(sec14.getSectionId(), sec14);

        sections.put(sec17.getSectionId(), sec17);
        sections.put(sec18.getSectionId(), sec18);
        sections.put(sec11.getSectionId(), sec11);
        sections.put(sec19.getSectionId(), sec19);
        sections.put(sec12.getSectionId(), sec12);
        sections.put(sec13.getSectionId(), sec13);
        sections.put(sec20.getSectionId(), sec20);
        sections.put(sec21.getSectionId(), sec21);
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
