package com.wma.adult.user;

import com.wma.adult.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        Log.d("register","id = " + user.getId() + " account = " + user.getAccount() + " password = " + user.getAccount() + " userPhone = " + user.getUserPhone() + " userEmail = " + user.getUserEmail());
        userMapper.register(user.getId(), user.getAccount(), user.getPassword(), user.getUserPhone(), user.getUserEmail());
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user.getId(), user.getAccount(), user.getPassword(), user.getToken()
                , user.getUserName(), user.getUserPhone(), user.getUserEmail(), user.getBgWall(), user.getHeadImage(), user.getSex()
                , user.getBirthday(), user.getProvince(), user.getCity(), user.getArea()
                , user.getLoginTime());
    }


    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void resetPassword(String id, String password) {
        userMapper.resetPassword(id, password);
    }

}
