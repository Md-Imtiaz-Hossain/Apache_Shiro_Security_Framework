package com.imtiaz.apache_shiro.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imtiaz.apache_shiro.entity.User;
import com.imtiaz.apache_shiro.mapper.UserMapper;
import com.imtiaz.apache_shiro.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
