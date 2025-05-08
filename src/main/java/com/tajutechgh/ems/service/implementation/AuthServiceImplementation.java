package com.tajutechgh.ems.service.implementation;

import com.tajutechgh.ems.dto.JwtAuthResponse;
import com.tajutechgh.ems.dto.LoginDto;
import com.tajutechgh.ems.dto.RegisterDto;
import com.tajutechgh.ems.entity.Role;
import com.tajutechgh.ems.entity.User;
import com.tajutechgh.ems.exception.EMSAPIException;
import com.tajutechgh.ems.repository.RoleRepository;
import com.tajutechgh.ems.repository.UserRepository;
import com.tajutechgh.ems.security.jwt.JwtTokenProvider;
import com.tajutechgh.ems.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @Override
    public JwtAuthResponse loginUser(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        String role = null;

        if (userOptional.isPresent()){

            User loggedInUser = userOptional.get();

            Optional<Role> roleOptional = loggedInUser.getRoles().stream().findFirst();

            if (roleOptional.isPresent()){

                Role userRole = roleOptional.get();

                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);

        return jwtAuthResponse;
    }
}
