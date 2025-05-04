package com.tajutechgh.ems.service.implementation;

import com.tajutechgh.ems.dto.RegisterDto;
import com.tajutechgh.ems.repository.RoleRepository;
import com.tajutechgh.ems.repository.UserRepository;
import com.tajutechgh.ems.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public AuthServiceImplementation(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public String register(RegisterDto registerDto) {
        return "";
    }
}
