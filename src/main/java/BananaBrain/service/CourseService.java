package BananaBrain.service;

import java.util.List;

import BananaBrain.model.Course;
import BananaBrain.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository cRepo;

    public void save(Course c) {
        cRepo.save(c);
    }

    public List<Course> getAllCourse(){
        return cRepo.findAll();
    }

    public Course getCourseById(int id) {
        return cRepo.findById(id).get();
    }
    public void deleteById(int id) {
        cRepo.deleteById(id);
    }
}