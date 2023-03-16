package com.imtiaz.apache_shiro.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imtiaz.apache_shiro.entity.UserRole;
import com.imtiaz.apache_shiro.mapper.UserRoleMapper;
import com.imtiaz.apache_shiro.service.UserRoleService;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
