package com.example.producer.converters;

import com.example.producer.entities.User;
import com.example.ws.users.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        // FIXME: Error if age is null
        if (user.getAge() == null) {
            userDto.setAge(0);
        } else {
            userDto.setAge(user.getAge());
        }

        return userDto;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setAge(dto.getAge());

        return user;
    }
}