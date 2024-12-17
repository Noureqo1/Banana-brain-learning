package BananaBrain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String Teacher;
    private String price;
    public Course(int id, String name, String Teacher, String price) {
        super();
        this.id = id;
        this.name = name;
        this.Teacher = Teacher;
        this.price = price;
    }
    public Course() {
        super();

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