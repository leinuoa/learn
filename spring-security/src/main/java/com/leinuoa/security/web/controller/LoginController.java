package com.leinuoa.security.web.controller;

import com.leinuoa.security.model.JwtRequest;
import com.leinuoa.security.model.JwtResponse;
import com.leinuoa.security.service.JwtUserDetailsService;
import com.leinuoa.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/sys/login")
    @ResponseBody
    public String login(@RequestBody JwtRequest authenticationRequest){
        String token = null;
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            token = jwtTokenUtil.generateToken(userDetails);
        }catch (Exception e){
            System.out.println(e.getMessage());
            if("USER_DISABLED".equals(e.getMessage())){
                System.out.println("用户名或密码错误！");
            }else if("INVALID_CREDENTIALS".equals(e.getMessage())){
                System.out.println("token失效！");
            }
        }
        return token;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
