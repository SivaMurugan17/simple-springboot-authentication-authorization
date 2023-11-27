package com.siva.Spring.service;

import com.siva.Spring.model.User;
import com.siva.Spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        Iterator<User> iterator = userRepository.findAll().iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
