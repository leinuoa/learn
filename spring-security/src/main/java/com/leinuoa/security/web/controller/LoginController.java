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

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil,
                           JwtUserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @PostMapping(path = "/sys/login")
    @ResponseBody
    public String login(String username,String password){
        String token = null;
        try {
            authenticate(username, password);
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
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

    @RequestMapping("/index")
    @ResponseBody
    public String homePage(){
        return "登录成功！！！";
    }
}
