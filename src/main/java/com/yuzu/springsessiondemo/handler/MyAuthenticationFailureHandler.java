package com.yuzu.springsessiondemo.handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        log.error("@@@ Login failed: {}", name);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(name, password);
//        SpringContextUtil.publishEvent(
//                new AuthenticationFailureBadCredentialsEvent(auth, e));
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
