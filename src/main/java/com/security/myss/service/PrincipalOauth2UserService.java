package com.security.myss.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest: org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@19544816
        // 구글로 부터 받은 데이터를 후처리 하는 메소드
        System.out.println("userRequest: "+userRequest);
        System.out.println("getAccessToken: "+userRequest.getAccessToken());
        System.out.println("getClientRegistration: "+userRequest.getClientRegistration());
        System.out.println("getClientId: "+userRequest.getClientRegistration().getClientId());
        System.out.println("getClientName: "+userRequest.getClientRegistration().getClientName());
        System.out.println("getClientSecret: "+userRequest.getClientRegistration().getClientSecret());
        // 구글 로그인 클릭 -> 구글로그인창 -> 로그인진행 완료 -> 코드 리턴(OAuth-Client lib) -> access token 요청
        // user request 정보 -> loaduser 호출 -> 구글로부터 회원 프로필 받음
        System.out.println("super.loadUser(userRequest): "+super.loadUser(userRequest)); 

        OAuth2User oauth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }

    
}
