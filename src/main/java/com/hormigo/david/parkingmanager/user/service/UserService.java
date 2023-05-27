package com.hormigo.david.parkingmanager.user.service;

import java.util.List;
import java.util.Optional;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;

public interface UserService {
    public List<User> getAll();
    public User register(UserDao userDao) throws UserExistsException;
    public boolean userExists(String email);
    public Optional<User> getUser(long id);
    public void deleteUserById(long id) throws UserDoesNotExistsException;
    public User updateUser(long id,UserDao user) throws UserDoesNotExistsException;
}
