package com.sandeep.ContactManger.config;

import com.sandeep.ContactManger.Entities.Providers;
import com.sandeep.ContactManger.Entities.UserEntity;
import com.sandeep.ContactManger.repositories.IUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenicationSuccessHandler");

        // identify the provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

        String oauth2Provider = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

        logger.info(oauth2Provider);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID().toString());
        //user.setRoleList(List.of(ConstantUtils.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("Sandeep"));

        if (oauth2Provider.equalsIgnoreCase("google")) {

            // google
            // google attributes

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePicLink(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google.");

        } else if (oauth2Provider.equalsIgnoreCase("github")) {

            // github
            // github attributes
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePicLink(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);

            user.setAbout("This account is created using github");
        }

        else if (oauth2Provider.equalsIgnoreCase("linkedin")) {

        }

        else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        // save the user
        // facebook
        // facebook attributes
        // linkedin



        UserEntity user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved:" + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

    }
}
