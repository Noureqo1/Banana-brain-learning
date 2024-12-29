package BananaBrain.service;

import java.util.List;

import BananaBrain.model.MyCourseList;
import BananaBrain.repository.MyCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MyCourseListService {

    @Autowired
    private MyCourseRepository mycourse;

    public void saveMyCourse(MyCourseList course) {
        mycourse.save(course);
    }

    public List<MyCourseList> getAllMyCourse(){
        return mycourse.findAll();
    }

    public void deleteById(int id) {
        mycourse.deleteById(id);
    }

}
