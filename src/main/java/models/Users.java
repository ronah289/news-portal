package models;

import java.util.Objects;

public class Users {

    private int id;
    private final String name;
    private final String position;
    private final String staff_role;


    public Users(String name, String position, String staff_role) {
        this.name = name;
        this.position = position;
        this.staff_role = staff_role;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Users users = (Users) object;
        return id == users.id &&
                Objects.equals(name, users.name) &&
                Objects.equals(position, users.position) &&
                Objects.equals(staff_role, users.staff_role) ;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, staff_role);
    }
}
