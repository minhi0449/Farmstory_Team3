package com.farmstory.Controller;

import com.farmstory.dto.UserDTO;
import com.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    // user
    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/user/login")
    public String login(UserDTO userDTO, Model model) {
        UserDTO user = userService.loginUser(userDTO);
        model.addAttribute("user", user);
        return "redirect:/user/login";
    }

//    @GetMapping("/user/success")
//    public String success(){
//        return "/user/success";
//    }


    @GetMapping("/user/register")
    public String register() {
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(UserDTO userDTO) {
        // userDTO.setPass(encoder.encode(userDTO.getPass()));
        userService.insertUser(userDTO);
        return "redirect:/user/register";
    }

//    @GetMapping("/user/term")
//    public String term() {
//        return "/user/term";
//    }

}
