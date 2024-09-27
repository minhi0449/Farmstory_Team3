package com.farmstory.oauth2;

import com.farmstory.entity.User;
import com.farmstory.repository.UserRepository;
import com.farmstory.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MyOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("LoadUser...1" + userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("LoadUser...2" + accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("LoadUser...3" + provider);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("LoadUser...4" + oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("LoadUser...5" + attributes);

        // 사용자 확인 및 회원가입 처리
        String email = (String) attributes.get("email");
        String uid = email.split("@")[0];
        String name = attributes.get("given_name").toString();
        String nick = attributes.get("name").toString();

        // User user = userRepository.findById(uid).orElse(null);
        Optional<User> optUser = userRepository.findById(uid);

        if (optUser.isPresent()) {
            // 회원 존재하면 시큐리티 인증처리 (로그인)
            User user = optUser.get();

            return MyUserDetails.builder()
                    .user(user)
                    .accessToken(accessToken)
                    .attributes(attributes)
                    .build();
        }else {
            // 회원이 존재하지 않으면 회원가입 처리
            // 구글로 받은 User 정보는 아래 3가지 밖에 없다
            User user = User.builder()
                            .uid(uid)
                            .nick(nick)
                            .email(email)
                            .name(name)
                            .build();

            userRepository.save(user);

            return MyUserDetails
                    .builder()
                    .accessToken(accessToken)
                    .attributes(attributes)
                    .build();
        }
    }
}
