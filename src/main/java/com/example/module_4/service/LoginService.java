package com.example.module_4.service;

import com.example.module_4.model.User;
import com.example.module_4.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private IUserRepository userRepository;

    public Integer login(User user) {
        List<User> userList = userRepository.findAll();
        for (User us : userList) {
            if (us.getUsername().equals(user.getUsername()) && us.getPassword().equals(user.getPassword())) {
                if (user.getRole().getName().equals("ROLE_GUEST")) {
                    return 0;
                }
                return 1;
            }

        }
        return 2;
    }

    public boolean signUp(User user) {
        if (!checkUserExist(user)){
            userRepository.save(user);
            return true;
        }return false;
    }

    public boolean checkUserExist(User user) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (user.getUsername().equals(u.getUsername())) {
                return false;
            }
        }
        return true;
    }

}