package saphy.saphy.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import saphy.saphy.member.domain.Member;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {

        this.member = member;
        System.out.println("Member password: " + member.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
            }
        });

        // 권한 문자열 확인
        for (GrantedAuthority authority : authorities) {
            System.out.println("Authority: " + authority.getAuthority());
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        System.out.println("Getting password: " + member.getPassword());
        return member.getPassword();
    }

    @Override
    public String getUsername() {

        return member.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}