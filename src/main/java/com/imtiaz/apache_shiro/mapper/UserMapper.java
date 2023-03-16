package com.imtiaz.apache_shiro.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imtiaz.apache_shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}