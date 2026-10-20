package vn.edu.ptit.oop.nhom02.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import vn.edu.ptit.oop.nhom02.model.ClassSection;

public class SectionTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final String[] columnNames = {
        "Lớp", "Mã Môn", "Tên Môn Học", "Tín Chỉ", "Khoa/Bộ Môn", "Giảng Viên", "Sĩ Số", "Lịch Học & Phòng"
    };

    private List<ClassSection> sections;

    public SectionTableModel() {
        this.sections = new ArrayList<>();
    }

    public SectionTableModel(List<ClassSection> sections) {
        this.sections = (sections != null) ? new ArrayList<>(sections) : new ArrayList<>();
    }

    public void setSections(List<ClassSection> newSections) {
        this.sections = (newSections != null) ? new ArrayList<>(newSections) : new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return sections.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= sections.size()) return null;
        ClassSection sec = sections.get(rowIndex);

        switch (columnIndex) {
            case 0: return sec.getClassName();
            case 1: return sec.getCourse().getCode();
            case 2: return sec.getCourse().getName();
            case 3: return sec.getCourse().getCredits();
            case 4: return sec.getCourse().getFaculty();
            case 5: return sec.getLecturer().getFullName();
            case 6: return sec.getEnrolledCount() + " / " + sec.getCapacity();
            case 7: return sec.getTimeSlotsSummary();
            default: return "";
        }
    }

    public ClassSection getSectionAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < sections.size()) {
            return sections.get(rowIndex);
        }
        return null;
    }
}
