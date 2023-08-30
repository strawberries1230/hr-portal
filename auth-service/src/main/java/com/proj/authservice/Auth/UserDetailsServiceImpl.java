package com.proj.authservice.Auth;

import com.proj.authservice.Dao.UserRepository;
import com.proj.authservice.Model.Entity.Role;
import com.proj.authservice.Model.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private List<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {


        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> userOptional = userRepository.findByEmail(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));

        List<Role> roles = user.getUserRoles().stream()
                .map(UserRole -> UserRole.getRole())
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword())
                .authorities(mapRolesToAuthorities(roles)).build();
    }
}
