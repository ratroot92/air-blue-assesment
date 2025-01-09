package com.evergreen.evergreenmedic.implementations;

import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email " + email);
        } else {
            return userEntity;
        }

    }
}
