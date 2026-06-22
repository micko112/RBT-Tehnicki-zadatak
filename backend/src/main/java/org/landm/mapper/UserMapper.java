package org.landm.mapper;

import org.landm.dto.UserDto;
import org.landm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().toString())
                .build();
    }

    public User toUser(UserDto dto){
        User user = new User();
        return user;
    }
}
