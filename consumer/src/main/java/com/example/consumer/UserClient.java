package com.example.consumer;

import com.example.ws.users.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class UserClient extends WebServiceGatewaySupport {
    private final String WS_URL = "http://localhost:8080/ws";

    public GetUserByIDResponse getUserByID(Long userID) {
        String soapAction = WS_URL + "/getUserByIDRequest";
        GetUserByIDRequest request = new GetUserByIDRequest();
        request.setId(userID);
        return (GetUserByIDResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(soapAction));
    }

    public GetAllUserResponse getAllUser() {
        String soapAction = WS_URL + "/getAllUserRequest";
        GetAllUserRequest request = new GetAllUserRequest();
        return (GetAllUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(soapAction));
    }

    public AddUserResponse addUser(UserDto userDto) {
        String soapAction = WS_URL + "/addUserRequest";
        AddUserRequest request = new AddUserRequest();
        request.setUser(userDto);
        return (AddUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(soapAction));
    }

    public UpdateUserResponse updateUser(UserDto userDto) {
        String soapAction = WS_URL + "/updateUserRequest";
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUser(userDto);
        return (UpdateUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(soapAction));
    }

    public DeleteUserResponse deleteUserResponse(Long id) {
        String soapAction = WS_URL + "/deleteUserRequest";
        DeleteUserRequest request = new DeleteUserRequest();
        request.setId(id);
        return (DeleteUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(soapAction));
    }
}
