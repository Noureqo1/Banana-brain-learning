package controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signupPage(){
        return "signup";
    }

//    @GetMapping("/index")
//    public String home(){
//        return "index";
//    }
}
