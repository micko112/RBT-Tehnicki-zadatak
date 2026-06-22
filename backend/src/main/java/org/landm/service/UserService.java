package org.landm.service;

import org.landm.dto.UserDto;
import org.landm.dto.auth.RegisterUserRequestDto;

public interface UserService {

    public UserDto register(RegisterUserRequestDto request);
}
