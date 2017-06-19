package com.emr.services;

import com.emr.domain.User;

public interface UserService extends CRUDService<User> {

    User findByUsername(String username);

}
