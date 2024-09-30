package com.farmstory.Controller;

import com.farmstory.dto.TermsDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

//    @PostMapping("/user/login")
//    public String login(UserDTO userDTO, Model model) {
//        UserDTO user = userService.loginUser(userDTO);
//        model.addAttribute("user", user);
//        return "redirect:/user/login";
//    }


    // 로그인
    @PostMapping("/user/login")
    public String login(HttpServletRequest req, @RequestParam("uid") String uid, @RequestParam("pass") String pass, Model model) {
        log.info("Login attempt: uid = " + uid);

        // UserService를 통해 로그인 처리
        UserDTO user = userService.login(uid, pass);

        if (user != null) {
            // 로그인 성공: 세션에 사용자 정보 저장
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            log.info("Login successful: " + user.getUid());
            return "redirect:/"; // 로그인 후 리다이렉트할 페이지
        } else {
            // 로그인 실패 시 에러 메시지와 함께 로그인 페이지로 이동
            model.addAttribute("loginError", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "/user/login?loginError=103";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // 세션 무효화
        session.invalidate();

        // 성공적으로 로그아웃되었다는 메시지를 리다이렉트 속성에 추가
        redirectAttributes.addFlashAttribute("success", 101);

        // 메인 페이지 또는 로그인 페이지로 리다이렉트
        return "redirect:/"; // 리다이렉트할 페이지로 이동
    }


    // Terms
    @GetMapping("/user/terms")
    public String terms(Model model) {
        TermsDTO termsDTO = userService.selectTerms();
        log.info(termsDTO);
        model.addAttribute("terms", termsDTO);
        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register() {
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(HttpServletRequest req, UserDTO userDTO) {
        log.info(userDTO.toString());

//        if(userDTO == null){
//            return "/user/register?success=202";
//        }

        String regip= req.getRemoteAddr();
        userDTO.setRegip(regip);

        userService.insertUser(userDTO);

        return "redirect:/user/login?success=200";
    }


    @ResponseBody
    @GetMapping("/user/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                       @PathVariable("type")  String type,
                                       @PathVariable("value") String value){

        log.info("type : " + type + ", value : " + value);

        int count = userService.selectCountUser(type, value);
        log.info("count : " + count);

        // 중복 없으면 이메일 인증코드 발송
        if(count == 0 && type.equals("email")){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);
    }

    // 이메일 인증 코드 검사
    @ResponseBody
    @PostMapping("/email")
    public ResponseEntity<?> checkEmail(HttpSession session, @RequestBody Map<String, String> jsonData){

        log.info("checkEmail code : " + jsonData);

        String receiveCode = jsonData.get("code");
        log.info("checkEmail receiveCode : " + receiveCode);

        String sessionCode = (String) session.getAttribute("code");

        if(sessionCode.equals(receiveCode)){
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }
    }







}
