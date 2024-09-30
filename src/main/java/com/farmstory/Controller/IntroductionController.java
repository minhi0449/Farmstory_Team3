package com.farmstory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// introduction 페이지 - 2개 / 메인 페이지 - 2개
@Controller
public class IntroductionController {
    // introduction
    @GetMapping("/introduction/direction")
    public String direction() {
        return "/introduction/direction";
    }
    @GetMapping("/introduction/introduction")
    public String introduction() {
        return "/introduction/introduction";
    }

}
