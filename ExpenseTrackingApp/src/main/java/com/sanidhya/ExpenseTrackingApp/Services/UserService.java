package com.sanidhya.ExpenseTrackingApp.Services;

import com.sanidhya.ExpenseTrackingApp.Entity.User;
import com.sanidhya.ExpenseTrackingApp.Entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUser(Long id);

    User updateUser(UserModel user,Long id);

    void deleteUser(Long id);

    User getLoggedInUser();


}
