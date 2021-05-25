package com.my.crm.settings.dao;

import com.my.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {

    User selectUserLogin(Map<String, String> map);
}
