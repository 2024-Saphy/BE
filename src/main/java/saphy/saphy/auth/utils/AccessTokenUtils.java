package saphy.saphy.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;

public class AccessTokenUtils {
    // 사용자가 인증되었는지 확인하고, 사용자 정보를 반환하는 메서드
    public static String isPermission() {
        // 현재 인증 정보를 SecurityContext에서 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 객체에서 사용자 정보를 가져옴
        Object principal = authentication.getPrincipal();

        // 사용자 정보(principal)가 UserDetails의 인스턴스인지 확인
        if (principal instanceof UserDetails) {
            // UserDetails의 경우, 인증된 사용자의 이름(아이디)을 반환
            return ((UserDetails) principal).getUsername();
        }
        // principal이 문자열(String)인 경우
        else if (principal instanceof String) {
            // 문자열인 경우, 사용자 이름을 그대로 반환
            return (String) principal;
        }
        // principal이 UserDetails나 String이 아니면 인증이 제대로 되지 않은 상태이므로
        // 인증 토큰이 필요하다는 사용자 정의 예외를 던짐
        throw SaphyException.from(ErrorCode.NEED_AUTH_TOKEN);
    }
}
