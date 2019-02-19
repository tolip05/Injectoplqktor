package panda.service;

import panda.domein.models.service.UserServiceModel;

import java.util.List;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel userLogin(UserServiceModel userServiceModel);
    UserServiceModel findUserByUsername(String username);

    List<UserServiceModel> findAllUsers();
}
