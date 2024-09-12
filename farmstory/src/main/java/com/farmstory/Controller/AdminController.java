package com.farmstory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping(value={"/admin/","/admin/index"})
    public String index() {
        return "/admin/index";
    }


    @GetMapping("/admin/order/list")
    public String orderList() {
        return "/admin/order/list";
    }
    @GetMapping("/admin/user/list")
    public String userList() {
        return "/admin/user/list";
    }

}
