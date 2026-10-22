# BÀI TẬP LỚN LẬP TRÌNH HƯỚNG ĐỐI TƯỢNG (INT1332)

## ĐỀ TÀI SỐ 06: HỆ THỐNG ĐĂNG KÝ HỌC PHẦN (COURSE REGISTRATION)

**Nhóm thực hiện**: Nhóm 02
**Học viện Công nghệ Bưu chính Viễn thông - Cơ sở TP. Hồ Chí Minh (PTIT)**

---

### THÔNG TIN NỘP ĐỒ ÁN
- **Link Git Repository**: https://github.com/hngoos/OOP_Nhom02_CourseRegistration

---

### 1. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

- **Ngôn ngữ**: Java (JDK 17 trở lên để biên dịch; Java 17 trở lên để chạy JAR)
- **Giao diện**: Java Swing (JFrame, JDialog, JTable, JPanel, Graphics2D)
- **Biểu đồ**: JFreeChart 1.5.4 (`lib/jfreechart-1.5.4.jar`)
- **IDE khuyến nghị**: Eclipse IDE (hoặc IntelliJ IDEA / NetBeans / VS Code)

---

### 2. CẤU TRÚC GÓI MÃ NGUỒN (PACKAGE STRUCTURE)

```text
OOP_Nhom02_CourseRegistration/
├── docs/
│   ├── UML_ClassDiagram.png               // Sơ đồ lớp UML xuất ra ảnh độ nét cao (4016x1913)
│   └── UML_ClassDiagram.puml              // Mã nguồn mô hình hóa PlantUML
├── lib/
│   └── jfreechart-1.5.4.jar               // Thư viện trực quan hóa dữ liệu JFreeChart
├── resources/
│   └── icons/                            // Bộ icon PNG 16x16, 32x32 cho ứng dụng
├── src/
│   └── vn/edu/ptit/oop/nhom02/
│       ├── Main.java                     // Điểm vào chạy trên Event Dispatch Thread (EDT)
│       ├── common/
│       │   └── BaseWindow.java           // Lớp trừu tượng khung sườn bắt buộc (Mục 5)
│       ├── model/
│       │   ├── Person.java               // Lớp trừu tượng Person (Đóng gói & Trừu tượng)
│       │   ├── Student.java              // Kế thừa Person, chứa thông tin SV PTIT phía Nam
│       │   ├── Lecturer.java             // Kế thừa Person, chứa thông tin Giảng viên
│       │   ├── Course.java               // Môn học, số tín chỉ & danh sách môn tiên quyết
│       │   ├── TimeSlot.java             // Ca học theo buổi (Sáng: 1-4, Chiều: 7-10) & overlaps()
│       │   └── ClassSection.java         // Lớp học phần, lớp niên chế, sĩ số, danh sách SV
│       ├── exception/
│       │   ├── RegistrationException.java         // Lớp ngoại lệ cha tự định nghĩa
│       │   ├── SectionFullException.java          // Ngoại lệ lớp đầy sĩ số
│       │   ├── ScheduleConflictException.java     // Ngoại lệ trùng lịch học
│       │   ├── CreditLimitException.java          // Ngoại lệ vượt quá số tín chỉ tối đa
│       │   └── PrerequisiteException.java         // Ngoại lệ chưa học môn tiên quyết
│       ├── repository/
│       │   ├── CourseRegistrationRepository.java  // Interface tự định nghĩa (Abstraction)
│       │   └── InMemoryCourseRegistrationRepository.java // Dữ liệu mẫu chuẩn PTIT Cơ sở phía Nam
│       ├── service/
│       │   └── RegistrationService.java           // Xử lý kiểm tra đầy đủ 4 ràng buộc đăng ký
│       ├── ui/
│       │   ├── MainWindow.java                    // Cửa sổ chính (extends BaseWindow)
│       │   ├── SectionTableModel.java             // TableModel tự viết kế thừa AbstractTableModel
│       │   ├── dialogs/
│       │   │   ├── LoginDialog.java               // Hộp thoại chọn Sinh viên / Giảng viên
│       │   │   ├── AboutDialog.java               // Hộp thoại thông tin nhóm (Mục 4)
│       │   │   └── ChartWindow.java               // Cửa sổ hiển thị biểu đồ (DISPOSE_ON_CLOSE)
│       │   └── panels/
│       │       ├── RegistrationPanel.java         // Panel đăng ký & tìm kiếm thời gian thực
│       │       ├── TimetablePanel.java            // Tự vẽ TKB (Graphics2D) & Xuất ảnh PNG
│       │       ├── RoleViewStrategy.java          // Interface Strategy Đa hình (BONUS)
│       │       ├── StudentViewStrategy.java       // Giao diện Đăng ký & TKB cho Sinh viên
│       │       └── LecturerViewStrategy.java      // Giao diện xem DS sinh viên cho Giảng viên (BONUS +5đ)
│       └── util/
│           └── ChartHelper.java                   // Tiện ích tạo BarChart & PieChart với font tiếng Việt
├── build.ps1                                     // Biên dịch Java 17 và đóng gói JAR
├── run.bat                                       // Khởi động ứng dụng trên Windows
├── OOP_Nhom02.jar                                 // JAR thực thi, dùng kèm thư mục lib/
└── README.md
```

---

### 3. THIẾT KẾ DỮ LIỆU & ĐẶC TẢ CA HỌC

- **Lớp niên chế**: `D26CQAT01-N` (năm 1), `D25CQAT01-N` (năm 2) và `D24CQAT01-N` (năm 3).
- **Cấu trúc bảng học phần**:
  `Lớp` | `Mã Môn` | `Tên Môn Học` | `Tín Chỉ` | `Khoa/Bộ Môn` | `Giảng Viên` | `Sĩ Số` | `Lịch Học & Phòng`
- **Quy chuẩn ca học theo buổi**:
  - **Buổi Sáng**: Từ **Tiết 1 đến Tiết 4**
  - **Buổi Chiều**: Từ **Tiết 7 đến Tiết 10**
- **Dữ liệu môn học**:
  - `INT1340` - Cấu trúc dữ liệu & Giải thuật: Thứ 2 (Tiết 1 - 4), Phòng 201-A2
  - `INT1339` - Cơ sở dữ liệu: Thứ 3 (Tiết 1 - 4), Phòng 302-A2
  - `BAS1203` - Giải tích 1: Thứ 4 (Tiết 1 - 4), Phòng 105-A1
  - `INT1332` - Lập trình hướng đối tượng (yêu cầu tiên quyết `INT1154`): Thứ 5 (Tiết 1 - 4), Phòng Lab3-CNTT
  - `INT1319` - Kiến trúc máy tính: Thứ 3 (Tiết 1 - 4), Phòng 204-A2 _(Trùng lịch sáng Thứ 3 với Cơ sở dữ liệu)_
  - `BAS1224` - Vật lý đại cương 1: Thứ 6 (Tiết 7 - 10), Phòng 202-A1
  - `SKD1105` - Kỹ năng thuyết trình: Thứ 7 (Tiết 1 - 4), Phòng Hội trường 2
  - `INT1154` - Tin học cơ sở 2: Thứ 2 (Tiết 7 - 10), Phòng Lab1-CNTT
  - `INT1339` (Lớp đầy sĩ số 1/1): Thứ 6 (Tiết 1 - 4), Phòng 102-A2

Dữ liệu hiện có **20 môn học, 21 lớp học phần, 5 giảng viên**. Năm môn chuyên ngành mới gồm Cơ sở an toàn thông tin (`INT1370`), Mật mã học cơ sở (`INT1372`), An toàn mạng máy tính (`INT1442`), Lập trình mạng (`INT1445`) và Đánh giá an toàn hệ thống (`INT1460`). Các môn có điều kiện tiên quyết tương ứng; đăng ký một môn trong học kỳ hiện tại không đồng nghĩa đã hoàn thành môn đó.

Trong **Mục đăng ký**, chọn **Đăng ký theo kế hoạch** để xem các lớp thuộc khóa đang đăng nhập: năm 1 có 6 lớp, năm 2 có 7 lớp, năm 3 có 8 lớp. Chọn **Đăng ký môn nguyện vọng** để xem toàn bộ 21 lớp, kết hợp bộ lọc khoa/bộ môn và từ khóa. Cả hai chế độ đều kiểm tra sĩ số, trùng lịch, giới hạn tín chỉ và môn tiên quyết khi đăng ký. Nút **Xóa bộ lọc** đưa về chế độ theo kế hoạch.

Ba tài khoản sinh viên mẫu là `N24DCAT001` (năm 3), `N25DCAT010` (năm 2) và `N26DCAT016` (năm 1). Mỗi tài khoản có giới hạn đăng ký 35 tín chỉ trong dữ liệu mẫu; giao diện cảnh báo khi chưa đủ 14 tín chỉ hoặc vượt 25 tín chỉ. `RegistrationService` vẫn chặn đăng ký vượt giới hạn riêng của từng sinh viên.

---

### 4. HƯỚNG DẪN IMPORT VÀ CHẠY DỰ ÁN TRONG ECLIPSE

1. Mở Eclipse IDE $\rightarrow$ **File** $\rightarrow$ **Open Projects from File System...**
2. Nhấn nút **Directory...** $\rightarrow$ Trỏ tới thư mục `OOP_Nhom02_CourseRegistration`.
3. Nhấn **Finish**.
4. **Cấu hình thư viện JFreeChart trong Eclipse**:
   - Chuột phải vào tên Project `OOP_Nhom02_CourseRegistration` $\rightarrow$ **Properties**.
   - Chọn mục **Java Build Path** $\rightarrow$ Thẻ **Libraries**.
   - Chọn mục **Classpath** $\rightarrow$ Nhấn nút **Add External JARs...**
   - Trỏ tới file `lib/jfreechart-1.5.4.jar` trong thư mục dự án.
   - Nhấn **Apply and Close**.
5. Mở file `src/vn/edu/ptit/oop/nhom02/Main.java` $\rightarrow$ Chuột phải chọn **Run As** $\rightarrow$ **Java Application**.

Dự án đặt mức biên dịch Java 17. Trong **Java Build Path**, dùng JDK hỗ trợ JavaSE-17 và thư viện JFreeChart; không thêm `OOP_Nhom02.jar` của chính dự án vào classpath. Nếu Eclipse còn báo lỗi sau khi cập nhật source, chọn **Project → Clean** để biên dịch lại.

### 4.1. BIÊN DỊCH VÀ CHẠY TRÊN WINDOWS

Mở PowerShell tại thư mục gốc của dự án, với JDK 17 trở lên đã có trong `PATH`:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\build.ps1
java -jar .\OOP_Nhom02.jar
```

Script tạo JAR mới từ toàn bộ mã nguồn và icon, với manifest khai báo `Class-Path: lib/jfreechart-1.5.4.jar`. Giữ thư mục `lib` cùng cấp với `OOP_Nhom02.jar` để chức năng biểu đồ hoạt động. Sau khi có JAR, có thể nhấp đúp `run.bat` để mở ứng dụng; không cần mở PowerShell đúng thư mục khi dùng `run.bat`.

---

### 5. KỊCH BẢN DEMO THUYẾT TRÌNH BẮT BUỘC (ĐẠT ĐIỂM TỐI ĐA)

Thực hiện theo đúng quy trình 7 bước giảng viên yêu cầu trong thẻ đề bài:

1. **Bước 1 - Đăng nhập Sinh viên**:
   - Khi ứng dụng khởi động, chọn vai trò **Sinh viên** $\rightarrow$ chọn `Trần Minh B - N25DCAT010` (đã hoàn thành `INT1154`) $\rightarrow$ bấm **Đăng nhập**.
   - Nếu đóng hộp đăng nhập bằng dấu X hoặc bấm **Thoát** lúc khởi động, ứng dụng kết thúc; chỉ mở màn hình chính sau khi đăng nhập thành công.
   - Chọn **Đăng ký môn nguyện vọng** để thấy cả môn năm 1 và năm 2 trong kịch bản dưới đây.
2. **Bước 2 - Đăng ký thành công 3 môn học phần hợp lệ**:
   - Chọn môn `INT1340 - Cấu trúc dữ liệu` $\rightarrow$ bấm nút **Đăng ký học phần**.
   - Chọn môn `INT1339 - Cơ sở dữ liệu` $\rightarrow$ bấm nút **Đăng ký học phần**.
   - Chọn môn `BAS1203 - Giải tích 1` $\rightarrow$ bấm nút **Đăng ký học phần**.
   - Bảng kết quả bên dưới và thanh trạng thái (Status bar) ở đáy cập nhật tổng số tín chỉ hiện có (9 TC, kèm cảnh báo chưa đạt mức tối thiểu 14 TC).
3. **Bước 3 - Demo bắt lỗi TRÙNG LỊCH HỌC**:
   - Chọn môn `INT1319 - Kiến trúc máy tính` (học Thứ 3 Tiết 1 - 4, trùng ca sáng Thứ 3 với môn Cơ sở dữ liệu vừa đăng ký).
   - Bấm nút **Đăng ký học phần** $\rightarrow$ Hệ thống bắt ngoại lệ và bung thông báo: `Trùng lịch học! Môn 'INT1319' bị trùng thời gian với môn 'INT1339' đã đăng ký`, kèm chi tiết ca học.
4. **Bước 4 - Demo bắt lỗi THIẾU TIÊN QUYẾT**:
   - Trên Toolbar, bấm nút **"Đổi tài khoản"**.
   - Chọn tài khoản sinh viên **chưa hoàn thành môn Tin học cơ sở 2**: `Lê Thị C - N26DCAT016`.
   - Chọn lại **Đăng ký môn nguyện vọng** để thấy môn Lập trình hướng đối tượng thuộc năm 2.
   - Chọn môn `INT1332 - Lập trình hướng đối tượng`.
   - Bấm **Đăng ký học phần** $\rightarrow$ Hệ thống bung thông báo: `[PrerequisiteException]: Chưa thỏa mãn điều kiện tiên quyết cho môn 'INT1332'! Cần hoàn thành các môn: INT1154`.
5. **Bước 5 - Xem Thời khóa biểu tự vẽ**:
   - Đổi lại tài khoản `N25DCAT010` để xem các môn đã đăng ký ở bước 2.
   - Chuyển sang tab **"Thời Khóa Biểu Tuần"**: Quan sát lưới ma trận tuần 7 cột $\times$ 12 tiết được vẽ trực quan bằng `Graphics2D`. Môn học phủ trọn buổi (Tiết 1-4 hoặc Tiết 7-10), có màu sắc nổi bật, hiển thị tên môn, phòng học, tiết học.
6. **Bước 6 - Xuất Thời khóa biểu ra ảnh PNG**:
   - Bấm nút **"Xuất TKB ra ảnh PNG"** $\rightarrow$ Chọn nơi lưu file trên máy $\rightarrow$ Kiểm tra file ảnh đã được xuất thành công, rõ nét.
7. **Bước 7 - Mở Biểu đồ JFreeChart & Demo Chế độ Giảng viên (BONUS +5đ)**:
   - Trên Toolbar, bấm nút **"Biểu đồ thống kê"** $\rightarrow$ Cửa sổ `ChartWindow` mở ra gồm:
     - Tab 1: **BarChart** tỷ lệ lấp đầy theo mã môn học: tổng sinh viên ghi danh ở các lớp của môn / tổng sức chứa các lớp × 100%. Ví dụ môn Cơ sở dữ liệu có hai lớp với sĩ số 1/60 và 1/1 thì hiển thị 2/61 ≈ 3,28%, không ghi đè thành 100% từ lớp cuối.
     - Tab 2: **PieChart** phân bố số tín chỉ sinh viên đã đăng ký theo khối kiến thức.
       Nhãn và tooltip hiển thị tên khoa, số tín chỉ và tỷ lệ phần trăm. Để xem dữ liệu của bước 2, đổi về `N25DCAT010` trước khi mở biểu đồ.
   - **Demo BONUS Giảng viên**: Bấm **"Đổi tài khoản"** $\rightarrow$ Chọn vai trò **Giảng viên** $\rightarrow$ Chọn giảng viên bất kỳ (ví dụ: `TS. Lê Văn Tuấn`) $\rightarrow$ Giao diện tự động chuyển đổi bằng nguyên lý **Đa hình (Polymorphism)** sang màn hình Quản lý lớp giảng dạy và xem danh sách sinh viên ghi danh trong lớp của giảng viên đó.

### 6. QUY TRÌNH ĐÓNG GÓP CỦA THÀNH VIÊN

1. Mỗi thành viên dùng nhánh được phân công và chọn đúng nhánh trước khi upload trên GitHub.
2. Trong thư mục phân chia của mình, kéo **chỉ thư mục `src`** vào **Add file → Upload files** tại gốc repository. Giữ nguyên các thư mục package bên trong `src`.
3. Các file `.java` nằm rời cùng cấp `src` là bản để đọc, không upload thêm vào gốc repo. Các file hướng dẫn và RAR dùng để trao đổi trong nhóm.
4. Tạo Pull Request từ nhánh cá nhân vào `main`; nhóm trưởng kiểm tra và merge. Không upload toàn bộ dự án cũ để thay thế `main`.

Mỗi phần chia chỉ chứa các class được giao, cần tích hợp vào dự án đầy đủ để chạy. Khi có thay đổi chung, đối chiếu với source mới trước khi upload để tránh đưa lại phiên bản cũ. Sau khi tích hợp, chạy `build.ps1` để JAR khớp source.
