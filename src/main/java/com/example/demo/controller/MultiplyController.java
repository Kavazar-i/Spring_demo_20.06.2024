package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MultiplyController {
//    @Autowired // This annotation is not needed with constructor injection which is the preferred method of injection
    private final UserService userService;

    public MultiplyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showForm() {
        return "form";
    }

    @PostMapping("/multiply")
    public String multiplyNumber(@RequestParam("number") int number, Model model) {
        int result = number * 5;
        model.addAttribute("result", result);
        return "result";
    }

    @PostMapping("/list_of_users")
    public String saveName(@RequestParam("firstname") String firstname, Model model) {
        userService.saveUser(new User(firstname));
        String listOfUsers = userService.findAll().toString();
        model.addAttribute("result", listOfUsers);
        return "list_of_users";
    }
}
