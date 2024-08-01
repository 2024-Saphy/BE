package saphy.saphy.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;

public class AccessTokenUtils {

    public static String isPermission() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        throw SaphyException.from(ErrorCode.NEED_AUTH_TOKEN);
    }
}
