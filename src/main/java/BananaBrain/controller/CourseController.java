package BananaBrain.controller;

import BananaBrain.model.Course;
import BananaBrain.model.MyAppUser;
import BananaBrain.model.UserCourse;
import BananaBrain.repository.CourseRepository;
import BananaBrain.repository.UserCourseRepository;
import BananaBrain.service.MyAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final MyAppUserService userService;

    @GetMapping("/courseList")
    @Transactional(readOnly = true)
    public String listCourses(Model model, Authentication authentication) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("course", courses);

        if (authentication != null) {
            MyAppUser user = (MyAppUser) userService.loadUserByUsername(authentication.getName());
            List<Integer> enrolledCourseIds = user.getEnrolledCourses().stream()
                    .map(uc -> uc.getCourse().getId())
                    .collect(Collectors.toList());
            model.addAttribute("enrolledCourseIds", enrolledCourseIds);
            model.addAttribute("currentUser", user);
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }

        return "courseList";
    }

    @GetMapping("/myCourses")
    @Transactional(readOnly = true)
    public String myCourses(Model model, Authentication authentication) {
        MyAppUser user = (MyAppUser) userService.loadUserByUsername(authentication.getName());
        List<Course> userCourses = user.getEnrolledCourses().stream()
                .map(UserCourse::getCourse)
                .collect(Collectors.toList());

        model.addAttribute("course", userCourses);
        return "myCourses";
    }

    @PostMapping("/mylist/{courseId}")
    @Transactional
    public String enrollInCourse(@PathVariable Integer courseId, Authentication authentication) {
        MyAppUser user = (MyAppUser) userService.loadUserByUsername(authentication.getName());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        if (!user.isEnrolledInCourse(course)) {
            user.enrollInCourse(course);
            userService.save(user);
        }

        return "redirect:/myCourses";
    }

    @GetMapping("/deleteMyList/{courseId}")
    @Transactional
    public String unenrollFromCourse(@PathVariable Integer courseId, Authentication authentication) {
        MyAppUser user = (MyAppUser) userService.loadUserByUsername(authentication.getName());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        UserCourse userCourse = userCourseRepository.findByUserAndCourse(user, course)
                .orElse(null);

        if (userCourse != null) {
            user.unenrollFromCourse(course);
            userCourseRepository.delete(userCourse);
            userService.save(user);
        }

        return "redirect:/myCourses";
    }

    @GetMapping("/courseRegister")
    public String showCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "courseRegister";
    }

    @PostMapping("/courseRegister")
    public String registerCourse(@ModelAttribute Course course) {
        courseRepository.save(course);
        return "redirect:/courseList";
    }

    @GetMapping("/editCourse/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        model.addAttribute("course", course);
        return "courseEdit";
    }

    @PostMapping("/editCourse/{id}")
    public String updateCourse(@PathVariable Integer id, @ModelAttribute Course course) {
        course.setId(id);
        courseRepository.save(course);
        return "redirect:/courseList";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseRepository.deleteById(id);
        return "redirect:/courseList";
    }
}
