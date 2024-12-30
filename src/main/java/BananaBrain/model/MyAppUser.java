package BananaBrain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_app_user")
public class MyAppUser implements UserDetails {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<UserCourse> enrolledCourses = new HashSet<>();

    public void enrollInCourse(Course course) {
        if (!isEnrolledInCourse(course)) {
            UserCourse userCourse = new UserCourse();
            userCourse.setUser(this);
            userCourse.setCourse(course);
            enrolledCourses.add(userCourse);
        }
    }

    public void unenrollFromCourse(Course course) {
        enrolledCourses.removeIf(userCourse -> {
            if (userCourse.getCourse().equals(course)) {
                userCourse.setUser(null);
                userCourse.setCourse(null);
                return true;
            }
            return false;
        });
    }

    public boolean isEnrolledInCourse(Course course) {
        return enrolledCourses.stream()
                .anyMatch(userCourse -> userCourse.getCourse().equals(course));
    }

    public Set<Course> getEnrolledCourseSet() {
        return enrolledCourses.stream()
                .map(UserCourse::getCourse)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserCourse> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Set<UserCourse> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyAppUser that = (MyAppUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
