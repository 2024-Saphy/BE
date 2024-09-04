package saphy.saphy.auth.service;

import saphy.saphy.auth.dto.request.OAuthJoinRequest;

// 여러 소셜로그인의 확장성을 위해 인터페이스 구현
public interface SecurityService {
    void saveUserInSecurityContext(OAuthJoinRequest socialLoginDTO);
}