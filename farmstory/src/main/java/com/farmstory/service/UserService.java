package com.farmstory.service;

import com.farmstory.dto.TermsDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.entity.Terms;
import com.farmstory.entity.User;
import com.farmstory.repository.TermsRepository;
import com.farmstory.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // user
//    public void insertUser(UserDTO userDTO) {
//
//        String encoded = passwordEncoder.encode(userDTO.getPass());
//        userDTO.setPass(encoded);
//        userRepository.save(userDTO.toEntity());
//    }

    public void insertUser(UserDTO userDTO){
        log.info("userDTO : " + userDTO);
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);
        userRepository.save(modelMapper.map(userDTO, User.class));
    }


    public UserDTO loginUser(UserDTO userDTO) {
        Optional<User> opt = userRepository
                .findUserByUidAndPass(userDTO.getUid(), userDTO.getPass());

        if (opt.isPresent()) {
            User user = opt.get();
            return user.toDTO();
        }
        return null;
    }


    public User selectUserEntity(String uid) {
        Optional<User> optUser = userRepository.findById(uid);
        if(optUser.isPresent()) {
            User user = optUser.get();
            return user;
        }
        return null;
    }


    public UserDTO selectUser(String uid) {
        Optional<User> optUser = userRepository.findById(uid);
        if(optUser.isPresent()) {
            User user = optUser.get();
            return user.toDTO();
        }
        return null;
    }

    public List<UserDTO> selectAllUsers() {
        List<User> users = userRepository.findAll();
        return null;
    }

    // terms
    public TermsDTO selectTerms(){
        List<Terms> termsList = termsRepository.findAll();
        return termsList.get(0).toDTO();
    }

    public int selectCountUser(String type, String value){

        int count = 0;

        if(type.equals("uid")){
            count = userRepository.countByUid(value);
        }else if(type.equals("nick")){
            count = userRepository.countByNick(value);
        }else if(type.equals("hp")){
            count = userRepository.countByHp(value);
        }else if(type.equals("email")){
            count = userRepository.countByEmail(value);
        }
        return count;
    }


    public void updateUser(){

    }

    public void deleteUser(){

    }




    /*
        - build.gradle 파일에 spring-boot-starter-mail 의존성 추가 할것
        - application.yml 파일 spring email 관련 설정
     */
    @Value("${spring.mail.username}")
    private String sender;
    public void sendEmailCode(HttpSession session, String receiver){

        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));
        log.info("code : " + code);

        String title = "farmstory 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            // 메일 발송
            javaMailSender.send(message);
        }catch(Exception e){
            log.error("sendEmailConde : " + e.getMessage());
        }
    }


    /**
     * 로그인 로직
     * @param uid 사용자 아이디
     * @param pass 사용자 비밀번호
     * @return UserDTO (성공 시) 또는 null (실패 시)
     */
    public UserDTO login(String uid, String pass) {
        // uid로 사용자 조회
        User user = userRepository.findByUid(uid);

        if (user != null) {
            // 비밀번호 검증
            if (passwordEncoder.matches(pass, user.getPass())) {
                log.info("로그인 성공 - uid: " + uid);
                return modelMapper.map(user, UserDTO.class); // User 엔티티를 UserDTO로 변환하여 반환
            } else {
                log.warn("비밀번호 불일치 - uid: " + uid);
            }
        } else {
            log.warn("존재하지 않는 사용자 - uid: " + uid);
        }
        return null; // 로그인 실패 시 null 반환
    }

}
