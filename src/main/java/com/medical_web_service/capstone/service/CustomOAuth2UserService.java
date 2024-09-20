package com.medical_web_service.capstone.service;

import com.medical_web_service.capstone.entity.Role;
import com.medical_web_service.capstone.entity.User;
import com.medical_web_service.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 네이버와 카카오 로그인 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 네이버는 "response" 내부에 정보가 있음
        if ("naver".equals(registrationId)) {
            attributes = (Map<String, Object>) attributes.get("response");
        }

        User user = extractUserInfo(attributes, registrationId);
        saveOrUpdate(user);

        return oAuth2User;
    }

    private User extractUserInfo(Map<String, Object> attributes, String registrationId) {
        String name = (String) attributes.get("name");
        String nickname = (String) attributes.get("nickname");
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("profile_image");
        String gender = (String) attributes.get("gender");
        String age = (String) attributes.get("age");

        return User.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .picture(picture)
                .gender(gender)
                .age(age)
                .role(Role.USER)
                .build();
    }

    private void saveOrUpdate(User user) {
        Optional<User> existingUserOptional = userRepository.findByEmail(user.getEmail());

        existingUserOptional.map(existingUser -> {
            existingUser.update(user.getNickname(), user.getPicture());
            return userRepository.save(existingUser);
        }).orElseGet(() -> userRepository.save(user));
    }
}