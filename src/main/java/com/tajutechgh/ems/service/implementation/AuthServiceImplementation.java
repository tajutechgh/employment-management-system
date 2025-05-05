package com.tajutechgh.ems.service.implementation;

import com.tajutechgh.ems.dto.RegisterDto;
import com.tajutechgh.ems.entity.Role;
import com.tajutechgh.ems.entity.User;
import com.tajutechgh.ems.exception.EMSAPIException;
import com.tajutechgh.ems.repository.RoleRepository;
import com.tajutechgh.ems.repository.UserRepository;
import com.tajutechgh.ems.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail())) {

            throw new EMSAPIException(HttpStatus.BAD_REQUEST, "This email " + registerDto.getEmail() + " already exists");

        } else if (userRepository.existsByUsername(registerDto.getUsername())) {

            throw new EMSAPIException(HttpStatus.BAD_REQUEST, "This username " + registerDto.getUsername() + " already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName("User");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
