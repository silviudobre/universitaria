package be.kdg.programming5.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final long userId;
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, long id) {
        super(username, password, authorities);
        this.userId = id;
    }

    public long getUserId() {
        return userId;
    }
}
