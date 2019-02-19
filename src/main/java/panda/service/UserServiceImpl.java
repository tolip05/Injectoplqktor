package panda.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import panda.domein.entities.User;
import panda.domein.models.service.UserServiceModel;
import panda.repository.UserRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        this.setUserRoLE(user);
        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel userLogin(UserServiceModel userServiceModel) {
        User user = this.userRepository.findByUserName(userServiceModel.getUsername());
        if (user == null || !DigestUtils.sha256Hex(userServiceModel.getPassword()).equals(user.getPassword())){
             return null;
        }
        return this.modelMapper.map(user,UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.modelMapper
                .map(this.userRepository.findByUserName(username),UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        List<User>users = this.userRepository.findAll();
        List<UserServiceModel>usersModels = new ArrayList<>();
        for (User user : users) {
            usersModels.add(this.modelMapper.map(user,UserServiceModel.class));
        }
        return usersModels;
    }

    private void setUserRoLE(User user){
        user.setRole(this.userRepository.size() == 0 ? "Admin" : "User");
    }
}
