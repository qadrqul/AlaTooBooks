package com.example.academics.config;

import com.example.academics.model.Role;
import com.example.academics.model.Users;
import com.example.academics.services.UserService;
import com.example.academics.model.repo.RoleRepository;
import com.example.academics.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String username = token.getPrincipal().getAttributes().get("given_name").toString().toLowerCase();
        if (userRepository.findUsersByUsername(username).isPresent()) {


        } else {
            Users user = new Users();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setUsername(username);
            user.setPassword(username);

            Role role_user = new Role("ROLE_USER");
            user.setRoles(Set.of(role_user));
            userService.saveUser(user);
            userRepository.save(user);
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }

}
