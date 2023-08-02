package com.proj.authservice.Auth;

import com.proj.authservice.Dao.UserRepository;
import com.proj.authservice.Model.Entity.Role;
import com.proj.authservice.Model.Entity.User;
import com.proj.authservice.Model.Entity.UserRole;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .map(role -> new SimpleGrantedAuthority( "ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));

//
        List<Role> roles = user.getUserRoles().stream() // 获取User的所有UserRole
                .map(UserRole -> UserRole.getRole()) // 对每个UserRole，获取其Role
                .collect(Collectors.toList()); // 将结果收集为一个List
//
//
//        Hibernate.initialize(userDetails.getAuthorities());
//        Hibernate.initialize(user.getUserRoles());
//        List<Role> roles = new ArrayList<>();
//        for (UserRole userRole : user.getUserRoles()) {
//            Hibernate.initialize(userRole.getRole());
//            roles.add(userRole.getRole());
//        }

        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword())
                .authorities(mapRolesToAuthorities(roles)).build();
    }
}
