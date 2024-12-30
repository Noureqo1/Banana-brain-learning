package BananaBrain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String teacher;
    private String price;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCourse> enrolledUsers = new HashSet<>();

    // Helper methods
    public void enrollUser(MyAppUser user) {
        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(this);
        enrolledUsers.add(userCourse);
        user.getEnrolledCourses().add(userCourse);
    }

    public void unenrollUser(MyAppUser user) {
        enrolledUsers.removeIf(userCourse -> {
            if (userCourse.getUser().equals(user)) {
                user.getEnrolledCourses().remove(userCourse);
                return true;
            }
            return false;
        });
    }
}