package com.example.consumer;

import com.example.ws.users.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(UserClient userClient) {
        return args -> {
            long id = 6;
            System.out.println("--- Get user by Id: " + id);
            GetUserByIDResponse getUserByIDResponse = userClient.getUserByID(id);
            UserDto userDto = getUserByIDResponse.getUser();
            if (userDto == null) {
                System.out.println("User " + id + " not found");
            } else {
                System.out.println(userDto.getId() + ", " + userDto.getName()
                        + ", " + userDto.getAge());
            }

            System.out.println("--- Get all user ---");
            GetAllUserResponse getAllUserResponse = userClient.getAllUser();
            getAllUserResponse.getUser()
                    .forEach(e -> System.out.println(e.getId() + ", " + e.getName() + ", " + e.getAge()));

            String name = "An Nguyen";
            int age = 21;
            System.out.println("--- Add user: name=" + name + ", age=" + age);
            UserDto addedUser = new UserDto();
            addedUser.setName(name);
            addedUser.setAge(age);
            AddUserResponse addUserResponse = userClient.addUser(addedUser);
            userDto = addUserResponse.getUser();
            if (userDto != null) {
                System.out.println(userDto.getId() + ", " + userDto.getName()
                        + ", " + userDto.getAge());
            }
            ServiceStatus serviceStatus = addUserResponse.getServiceStatus();
            System.out.println("StatusCode: " + serviceStatus.getStatusCode() +
                    ", Message: " + serviceStatus.getMessage());

            id = 7;
            System.out.println("--- Update user: id=" + id);
            name = "An Nguyen";
            age = 11;
            System.out.println("With: name=" + name + ", age=" + age);
            UserDto updatedUser = new UserDto();
            updatedUser.setId(id);
            updatedUser.setName(name);
            updatedUser.setAge(age);
            UpdateUserResponse updateUserResponse = userClient.updateUser(updatedUser);
            serviceStatus = updateUserResponse.getServiceStatus();
            System.out.println("StatusCode: " + serviceStatus.getStatusCode() +
                    ", Message: " + serviceStatus.getMessage());
            id = 6;
            System.out.println("--- Delete user: id=" + id);
            DeleteUserResponse deleteArticleResponse = userClient.deleteUserResponse(id);
            serviceStatus = deleteArticleResponse.getServiceStatus();
            System.out.println("StatusCode: " + serviceStatus.getStatusCode() +
                    ", Message: " + serviceStatus.getMessage());
        };
    }
}
