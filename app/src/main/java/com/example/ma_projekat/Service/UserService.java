package com.example.ma_projekat.Service;

import com.example.ma_projekat.Model.User;
import com.example.ma_projekat.Repository.UserRepository;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public void getUser(String email, String password, UserRepository.FireStoreCallback fireStoreCallback){
        userRepository.getUser(email, password, fireStoreCallback);
    }
}