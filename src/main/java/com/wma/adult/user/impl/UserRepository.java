package com.wma.adult.user.impl;

import com.wma.adult.user.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
