package com.farmstory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 유정님 - Article, event
@Controller
public class ArticleController {
    // article
    @GetMapping("/croptalk/story")
    public String story() {
        return "/croptalk/story";
    }
    @GetMapping("/community/notice")
    public String notice() {
        return "/community/notice";
    }
    // event
    @GetMapping("/event/event")
    public String event() {
        return "/event/event";
    }

}
