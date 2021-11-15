package models;

import java.util.Objects;

@SuppressWarnings("unused")
public class Departments {

    private final String name;
    private final String description;
    private int id;
    private int size;



    public Departments(String name, String description) {
        this.name = name;
        this.description = description;
        this.size = 0;
    }
    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Departments that = (Departments) object;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
