package org.landm.mapper;

import org.landm.dto.UserDto;
import org.landm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole().name());

        return dto;
    }
    public User toUser(UserDto dto){
        User user = new User();
        return user;
    }
}
