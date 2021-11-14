package com.example.producer.endpoints;

import com.example.producer.converters.UserConverter;
import com.example.producer.entities.User;
import com.example.producer.services.UserService;
import com.example.producer.settings.WsSettings;
import com.example.ws.users.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class UserServiceEndpoints {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserServiceEndpoints(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PayloadRoot(namespace = WsSettings.NAMESPACE_URI, localPart = "getUserByIDRequest")
    @ResponsePayload
    public GetUserByIDResponse getUserByID(@RequestPayload GetUserByIDRequest request) {
        GetUserByIDResponse response = new GetUserByIDResponse();
        User user = userService.findById(request.getId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null) {
            response.setUser(null);
            serviceStatus.setStatusCode("404");
            serviceStatus.setMessage("User with id=" + request.getId() + " not found");
        } else {
            serviceStatus.setStatusCode("200");
            serviceStatus.setMessage("Get user success");
            response.setUser(userConverter.toDto(user));
            response.setServiceStatus(serviceStatus);
        }

        return response;
    }

    @PayloadRoot(namespace = WsSettings.NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<User> users = userService.findAll();
        List<UserDto> userDtoList = users.stream().map(userConverter::toDto).collect(Collectors.toList());
        response.getUser().addAll(userDtoList);

        return response;

    }

    @PayloadRoot(namespace = WsSettings.NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        UserDto userDto = new UserDto();
        userDto.setName(request.getName());
        userDto.setAge(request.getAge());
        User user = userConverter.toEntity(userDto);
        User savedUser = userService.addUser(user);

        if (savedUser != null) {
            response.setUser(userConverter.toDto(savedUser));
            serviceStatus.setStatusCode("200");
            serviceStatus.setMessage("User added");
        } else {
            response.setUser(null);
            serviceStatus.setStatusCode("400");
            serviceStatus.setMessage("Error when adding new user");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = WsSettings.NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse response = new UpdateUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        User user = userConverter.toEntity(request.getUser());

        User updatedUser = userService.updateUser(user);
        if (updatedUser == null) {
            serviceStatus.setStatusCode("404");
            serviceStatus.setMessage("User with id=" + user.getId() + " not found");
        } else {
            serviceStatus.setStatusCode("200");
            serviceStatus.setMessage("User updated");
        }

        response.setServiceStatus(serviceStatus);

        return response;

    }

    @PayloadRoot(namespace = WsSettings.NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        try {
            userService.deleteById(request.getId());
            serviceStatus.setStatusCode("200");
            serviceStatus.setMessage("User deleted");
        } catch (NullPointerException e) {
            serviceStatus.setStatusCode("404");
            serviceStatus.setMessage("User with id=" + request.getId() + " not found");

        }


        response.setServiceStatus(serviceStatus);

        return response;
    }
}
