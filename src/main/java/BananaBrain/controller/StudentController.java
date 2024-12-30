package BananaBrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController
{
    @GetMapping("/student/index")
    public String studentIndex(){
        return "student/index";
    }
}
