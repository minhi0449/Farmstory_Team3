package com.farmstory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // user
    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }
    @GetMapping("/user/register")
    public String register() {
        return "/user/register";
    }
    @GetMapping("/user/term")
    public String term() {
        return "/user/term";
    }
}
