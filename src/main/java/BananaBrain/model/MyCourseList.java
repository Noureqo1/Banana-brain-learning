package BananaBrain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="MyCourses")
public class MyCourseList {

    @Id
    private int id;
    private String name;
    private String Teacher;
    private String price;
    public MyCourseList() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MyCourseList(int id, String name, String Teacher, String price) {
        super();
        this.id = id;
        this.name = name;
        this.Teacher = Teacher;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTeacher() {
        return Teacher;
    }
    public void setTeacher(String Teacher) {
        this.Teacher = Teacher;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
