package com.imtiaz.apache_shiro.controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    // Home page (because the thymeleaf template is used, returning String is equal to returning src/main/resources/templates/xx.html by default)
    @RequestMapping("")
    public String index() {
        return "login";
    }

    // handle login logic
    @RequestMapping("/login")
    public String login(String email, String password, Model model) {
        /**
         * There are many core components of shiro, but there are `3` core components:
         * 1. Subject: The person who is interacting with the system, or a third-party service. All Subject instances are bound (and required) to a SecurityManager.
         * 2. SecurityManager: The heart of the Shiro architecture, used to coordinate internal security components, manage internal component instances, and provide various services for security management through it.
         * When Shiro interacts with a Subject, it is essentially the SecurityManager behind the scenes that handles all heavy Subject security operations.
         * 3. Realms: It is essentially a specific security DAO. When configuring Shiro, at least one Realm must be specified for authentication and/or authorization.
         * Shiro provides a variety of available Realms to obtain security-related data. Such as relational database (JDBC), INI and property files, etc. You can define your own Realm implementations to represent custom data sources.
         * Simple understanding:
         * -> Subject is the user currently requesting login
         * SecurityManager is Shiro's core security manager, and it can also help us handle some businesses such as logout, jumping to 404 pages, etc.
         * Realm is a security screening condition. Usually, due to different businesses, most of us use custom methods to define Realm
         */
        // get the current user
        Subject subject = SecurityUtils.getSubject();
        // Encapsulate the user's login data (the token is encapsulated here and can be obtained in our custom Realm -> UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;)
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        /**
         * The exception here is the exception we throw in Realm. If it is captured at the Controller layer, you can know why the user cannot log in.
         */
        try {
            subject . login ( token ); // Execute the login method, if there is no exception, it will be ok

            if ( subject . isAuthenticated ()) { // Authorization
                if ( subject . hasRole ( "admin" )) { // Have admin permission to jump to admin page
                    return "admin";
                }
            }
            // otherwise jump to the index page
            return "index";
        } catch ( UnknownAccountException  e ) { // username does not exist
            model .addAttribute ( "msg" , " Username error" );
            System.out.println ( " Username error " ) ;
            return "login";
        } catch ( IncorrectCredentialsException  e ) { // password does not exist
            // Jump to login if the password is incorrect, jump to index if the password is correct
            model .addAttribute ( "msg" , " password error" );
            System.out.println ( " password error " ) ;
            return "login";
            /**
             * ----------> The following list shiro common exceptions
             */
        } catch ( UnsupportedTokenException  e ) { // identity token exception, unsupported identity token
            return null;
        } catch ( LockedAccountException  e ) { // account locked
            return null;
        } catch ( DisabledAccountException  e ) { // user disabled
            return null;
        } catch ( ExcessiveAttemptsException  e ) { // The number of login retries exceeds the limit. Only allow a certain number of authentication attempts for a period of time
            return null;
        } catch ( ConcurrentAccessException  e ) { // A user has multiple login exceptions: multiple logins are not allowed, only one login is allowed. That is, multiple logins are not allowed
            return null;
        } catch ( AccountException  e ) { // account exception
            return null;
        } catch ( ExpiredCredentialsException  e ) { // expired credentials exception
            return null;
        } catch ( CredentialsException  e ) { // CredentialsException
            return null;
        } catch ( AuthenticationException  e ) { // Credential exception
            return null;
        } catch ( HostUnauthorizedException  e ) { // no access permission, access exception
            return null;
        } catch ( UnauthorizedException  e ) { // No access permission, access exception
            return null;
        } catch ( UnauthenticatedException  e ) { // authorization exception
            return null;
        } catch ( AuthorizationException  e ) { // authorization exception
            return null;
        } catch ( ShiroException  e ) { // shiro global exception
            return null;
        }

    }
}