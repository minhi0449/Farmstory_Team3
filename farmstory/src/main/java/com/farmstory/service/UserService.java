package com.farmstory.service;

import com.farmstory.dto.UserDTO;
import com.farmstory.entity.User;
import com.farmstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO) {
        // 회원가입
        // String encoded = passEncoder.encode(userDTO.getPass()); // 비밀번호 암호화
        // userDTO.setPass(encoded);

        User user = userDTO.toEntity();
        userRepository.save(user);
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

}
