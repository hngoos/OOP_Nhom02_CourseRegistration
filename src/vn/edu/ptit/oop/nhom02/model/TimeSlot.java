package vn.edu.ptit.oop.nhom02.model;

import java.io.Serializable;
import java.util.Objects;

public class TimeSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    private int dayOfWeek;
    private int startPeriod;
    private int endPeriod;
    private String room;

    public TimeSlot(int dayOfWeek, int startPeriod, int endPeriod, String room) {
        setDayOfWeek(dayOfWeek);
        setPeriods(startPeriod, endPeriod);
        setRoom(room);
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        if (dayOfWeek < 2 || dayOfWeek > 8) {
            throw new IllegalArgumentException("Thứ phải nằm trong khoảng từ 2 (Thứ Hai) đến 8 (Chủ Nhật)!");
        }
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartPeriod() {
        return startPeriod;
    }

    public int getEndPeriod() {
        return endPeriod;
    }

    public void setPeriods(int startPeriod, int endPeriod) {
        if (startPeriod < 1 || startPeriod > 12 || endPeriod < 1 || endPeriod > 12) {
            throw new IllegalArgumentException("Tiết học phải từ tiết 1 đến tiết 12!");
        }
        if (startPeriod > endPeriod) {
            throw new IllegalArgumentException("Tiết bắt đầu không được lớn hơn tiết kết thúc!");
        }
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        if (room == null || room.trim().isEmpty()) {
            this.room = "Phòng học";
        } else {
            this.room = room.trim();
        }
    }

    public boolean overlaps(TimeSlot other) {
        if (other == null) return false;
        if (this.dayOfWeek != other.dayOfWeek) {
            return false;
        }
        return (this.startPeriod <= other.endPeriod && this.endPeriod >= other.startPeriod);
    }

    public String getDayOfWeekString() {
        if (dayOfWeek == 8) return "Chủ Nhật";
        return "Thứ " + dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return dayOfWeek == timeSlot.dayOfWeek &&
               startPeriod == timeSlot.startPeriod &&
               endPeriod == timeSlot.endPeriod &&
               Objects.equals(room, timeSlot.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, startPeriod, endPeriod, room);
    }

    @Override
    public String toString() {
        return getDayOfWeekString() + " (Tiết " + startPeriod + "-" + endPeriod + "), Phòng: " + room;
    }
}
