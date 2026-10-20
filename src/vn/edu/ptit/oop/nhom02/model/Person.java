package vn.edu.ptit.oop.nhom02.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String fullName;
    private String email;

    public Person(String id, String fullName, String email) {
        setId(id);
        setFullName(fullName);
        setEmail(email);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã định danh không được để trống!");
        }
        this.id = id.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên không được để trống!");
        }
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email không đúng định dạng hợp lệ!");
        }
        this.email = email.trim();
    }

    public abstract String getRoleDescription();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " - " + fullName;
    }
}
