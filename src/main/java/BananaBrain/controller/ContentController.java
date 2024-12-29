package BananaBrain.controller;

import BananaBrain.model.Course;
import BananaBrain.model.MyCourseList;
import BananaBrain.model.Roles;
import BananaBrain.service.CourseService;
import BananaBrain.service.RoleService;
import BananaBrain.service.MyCourseListService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ContentController
{
    @Autowired
    private CourseService service;

    @Autowired
    private MyCourseListService myCourseService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/quiz")
    public String quiz(){
        return "quiz";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/assignRole")
    public String role(){
        return "role";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/courseRegister")
    public String Register(){
        return "courseRegister";
    }

    @GetMapping("/myCourses")
    public String getMyCourses(Model model)
    {
        List<MyCourseList>list = myCourseService.getAllMyCourse();
        model.addAttribute("course",list);
        return "myCourses";
    }

    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Course c=service.getCourseById(id);
        MyCourseList mc=new MyCourseList(c.getId(),c.getName(),c.getTeacher(),c.getPrice());
        myCourseService.saveMyCourse(mc);
        return "redirect:/myCourses";
    }

    @PostMapping("/save")
    public String addCourse(@ModelAttribute Course c) {
        service.save(c);
        return "redirect:/courseList";
    }

    @GetMapping("/courseList")
    public ModelAndView getAllCourse() {
        List<Course> list=service.getAllCourse();
        return new ModelAndView("courseList","course",list);
    }

    @RequestMapping("/editCourse/{id}")
    public String editCourse(@PathVariable("id") int id, Model model) {
        Course c=service.getCourseById(id);
        model.addAttribute("course",c);
        return "CourseEdit";
    }

    @RequestMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id")int id) {
        service.deleteById(id);
        return "redirect:/courseList";
    }

    @Controller
    @RequestMapping("/student")
    public class StudentController {
        @GetMapping("/home")
        public String studentHome() {
            return "studentHome";
        }
    }

    @Controller
    @RequestMapping("/teacher")
    public class TeacherController {
        @GetMapping("/home")
        public String teacherHome() {
            return "teacherHome";
        }
    }

}
