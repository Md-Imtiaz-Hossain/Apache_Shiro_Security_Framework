package com.imtiaz.apache_shiro.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.imtiaz.apache_shiro.entity.User;
import com.imtiaz.apache_shiro.entity.UserRole;
import com.imtiaz.apache_shiro.service.UserRoleService;
import com.imtiaz.apache_shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    // authentication
    // This method can determine whether the user can successfully authenticate, and can throw various exceptions to the controller to determine the user's login status
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(" --------------------> Execute Authentication<------------------- -- ");
        // This token is the token encapsulated by the Controller layer ( UsernamePasswordToken token = new UsernamePasswordToken(email, password); )
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // get username
        String username = token.getUsername();
        // print to see if it is the username
        System.out.println("username = " + username);

        // query user by username
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, username);
        User user = userService.getOne(queryWrapper);
        System.out.println("user =------------> " + user);
        if (user == null) { // If no user is found, return null, and the Controller layer catches UnknownAccountException
            return null;
        } else {
            // Find the user according to the user name, check whether the password is correct
            // Three parameters: username (queried user's username), password (queried user's password), realmName (customized Realm class name)
            // This method is to judge at the bottom, that is, to find the user through the username, and compare whether the password is correct (user.getPassword() ?= token.getPassword())
            return new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(), this.getName());
            // return new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(), MyRealm.class.getName());
        }
    }

    // authorization
    // This method is used to determine whether there is permission to perform the operation
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println(" -------------------- > Execute Authorization< ------------------- -- ");

        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("identity information = " + primaryPrincipal);
        // Get the permission information of the current user according to the username
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // Query the user by username
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, primaryPrincipal));
        // Get the permission id according to the user's id
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        // Assign the query role information in the database to the permission object
        if (userRole.getRoleId() == 1) {
            simpleAuthorizationInfo.addRole("admin");
        } else if (userRole.getRoleId() == 2) {
            simpleAuthorizationInfo.addRole("user");
        } else {
            simpleAuthorizationInfo.addRole("test");
        }
        // Return permission information object
        return simpleAuthorizationInfo;
    }
}