package com.imtiaz.apache_shiro.config;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
// Write from bottom to top to configure core components in config
public class ShiroConfig {
    // filter
    // inject security manager
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // set security manager
        bean.setSecurityManager(defaultWebSecurityManager);

        // add shiro's built-in filter
        /*
         * anon: access without authentication
         * authc: must be authenticated to allow
         * user: must have remember me function to use
         * perms: Only those who have permission to a resource can access it
         * role: Only with a certain role permission can access
         * */
//        Map<String ,String > filterMap = new LinkedHashMap<>();

//        filterMap.put("/user/add", "authc");
//        filterMap.put("/user/update", "authc");
//
//        filterMap.put("/user/*", "authc");
//
//        bean.setFilterChainDefinitionMap(filterMap);

        // Login path, this demo is configured in `LoginController`
//        bean.setLoginUrl("/toLogin");

        return bean;
    }

    // DefaultWebSecurityManager security manager
    // @Qualifier annotation directly injects `MyRealm` object, you can use Baidu by yourself
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // Associate MyRealm
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    // To create a realm object, a custom class is required
    @Bean(name = "myRealm")
    public MyRealm myRealm() {
        return new MyRealm();
    }
}