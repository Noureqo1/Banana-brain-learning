package BananaBrain.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }
    @GetMapping("/courseList")
    public String courses(){
        return "courseList";
    }

    @GetMapping("/courseRegister")
    public String Register(){
        return "courseRegister";
    }

    @GetMapping("/myCourses")
    public String MyCourses(){
        return "myCourses";
    }
}
