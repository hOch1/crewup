package com.example.crewup.config.oauth2;

import com.example.crewup.config.security.PrincipalDetails;
import com.example.crewup.entity.member.Member;
import com.example.crewup.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, attributes);

        Member member = getOrSave(oAuth2UserInfo, registrationId);

        return new PrincipalDetails(member, attributes);
    }

    private Member getOrSave(OAuth2UserInfo oAuth2UserInfo, String provider) {
        Member member = memberRepository.findByEmail(oAuth2UserInfo.email())
                .orElseGet(() -> oAuth2UserInfo.toEntity(provider));

        return memberRepository.save(member);
    }
}
