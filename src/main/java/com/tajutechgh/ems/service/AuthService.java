package com.tajutechgh.ems.service;

import com.tajutechgh.ems.dto.JwtAuthResponse;
import com.tajutechgh.ems.dto.LoginDto;
import com.tajutechgh.ems.dto.RegisterDto;

public interface AuthService {

    String registerUser(RegisterDto registerDto);

    JwtAuthResponse loginUser(LoginDto loginDto);
}
