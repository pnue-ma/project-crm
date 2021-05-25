package com.my.crm.settings.service;

import com.my.crm.exceptions.UserLoginException;
import com.my.crm.settings.domain.User;

public interface UserService {
    User userLogin(String uname, String upass, String uip) throws UserLoginException;
}
