package BananaBrain.repository;

import BananaBrain.model.Course;
import BananaBrain.model.MyAppUser;
import BananaBrain.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    List<UserCourse> findByUser(MyAppUser user);
    List<UserCourse> findByCourse(Course course);
    Optional<UserCourse> findByUserAndCourse(MyAppUser user, Course course);
    boolean existsByUserAndCourse(MyAppUser user, Course course);
    void deleteByUserAndCourse(MyAppUser user, Course course);
}
