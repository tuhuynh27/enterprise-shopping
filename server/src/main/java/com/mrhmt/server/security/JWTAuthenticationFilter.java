package com.mrhmt.server.security;

import com.auth0.jwt.JWT;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import com.mrhmt.server.entities.User;
import org.cloudinary.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.mrhmt.server.constants.SecurityConstant.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getName(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = JWT.create()
                .withSubject(((UserDetails) auth.getPrincipal()).getUsername())
                .withExpiresAt(expireDate)
                .sign(HMAC512(SECRET.getBytes()));

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader("Access-Control-Expose-Headers","Access-Token, Uid");

        res.setContentType("application/json");

        JSONObject tokenObj = new JSONObject();
        tokenObj.put("token", token);
        tokenObj.put("name", ((UserDetails) auth.getPrincipal()).getUsername());
        tokenObj.put("expire", expireDate.getTime());
        tokenObj.put("authorize", auth.getAuthorities());

        res.getWriter().write(tokenObj.toString());

        res.getWriter().flush();
        res.getWriter().close();
    }
}
