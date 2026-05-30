package com.wwwgame.converter;

import com.wwwgame.dto.UserDto;
import com.wwwgame.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;

    }

}
