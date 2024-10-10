package com.example.crewup.config.oauth2;

import com.example.crewup.config.security.PrincipalDetails;
import com.example.crewup.config.security.jwt.JwtProvider;
import com.example.crewup.dto.CustomApiResponse;
import com.example.crewup.dto.response.auth.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        JwtResponse jwtResponse = jwtProvider.createToken(principalDetails.member());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        CustomApiResponse<JwtResponse> customApiResponse = CustomApiResponse.success(jwtResponse);
        response.getWriter().write(objectMapper.writeValueAsString(customApiResponse));
    }
}
