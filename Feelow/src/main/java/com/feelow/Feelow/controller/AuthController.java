package com.feelow.Feelow.controller;

import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.dto.SignInDto;
import com.feelow.Feelow.dto.SignUpDto;
import com.feelow.Feelow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody){
        System.out.println(requestBody.toString());
        return null;
        //esponseDto<?> result = authService.signUp(requestBody);
        //        return  result;
    }

    // @PostMapping("/sign-in")
    //public ResponseDto<SingInResponseDro> signIn(@RequestBody SignInDto requestBody){
    //    return null;
    //}
}
