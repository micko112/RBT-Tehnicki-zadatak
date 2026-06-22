package org.landm.service.impl;

import org.landm.dto.UserDto;
import org.landm.dto.auth.RegisterUserRequestDto;
import org.landm.entity.User;
import org.landm.entity.enums.Role;
import org.landm.mapper.UserMapper;
import org.landm.repository.UserRepository;
import org.landm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto register(RegisterUserRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email vec postoji");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(Role.USER)
                .build();

        User saved = userRepository.save(user);

        return userMapper.toDto(saved);
    }
}
