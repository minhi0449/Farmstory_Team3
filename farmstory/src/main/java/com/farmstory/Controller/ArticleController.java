package com.farmstory.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 유정님 - Article, event
@Log4j2
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

    @GetMapping("/article/list/{cate}/{type}")
    public String article(Model model, @PathVariable("cate") String cate, @PathVariable("type") String type) {
        log.info("cate : "+cate+" type:"+type);
        model.addAttribute(cate, type);

        return "/article/list";
    }



    // event
    @GetMapping("/event/event")
    public String event() {
        return "/event/event";
    }



}
