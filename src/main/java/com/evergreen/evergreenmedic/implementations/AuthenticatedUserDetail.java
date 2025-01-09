package com.evergreen.evergreenmedic.implementations;

import com.evergreen.evergreenmedic.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
public class AuthenticatedUserDetail implements UserDetails {
    @Getter
    private String email;
    private String username;
    private UserRole role;
    private List<GrantedAuthority> grantedAuthorities = List.of();
    private short id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }


}
